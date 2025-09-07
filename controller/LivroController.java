package br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.controller;


import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.DTO.LivroDTO;
import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.excecao.AnoPublicacaoInvalidoException;
import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.excecao.LivroDuplicadoException;
import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {

   
    private final LivroService livroService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("livros", livroService.listar());
        return "listar";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("livroDTO", new LivroDTO());
        return "form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("livroDTO") LivroDTO livroDTO, BindingResult result,
                         @RequestParam("capaFile") MultipartFile capaFile,
                         @RequestParam(value = "pdfFile", required = false) MultipartFile pdfFile, Model model) throws IOException {

        
        if (result.hasErrors()) {          
            return "form";
        }

        // ======== CAPA ========
        if (capaFile != null && !capaFile.isEmpty()) {
            String uploadDir = "uploads";
            String fileName = UUID.randomUUID() + "_" + sanitizeFilename(capaFile.getOriginalFilename());
            Path caminho = Paths.get(uploadDir, fileName);

            Files.createDirectories(caminho.getParent());
            Files.copy(capaFile.getInputStream(), caminho, StandardCopyOption.REPLACE_EXISTING);

            livroDTO.setCapaUrl("/uploads/" + fileName);
        }

        // ======== PDF ========
        if (pdfFile != null && !pdfFile.isEmpty()) {
            String contentType = pdfFile.getContentType();
            String original = pdfFile.getOriginalFilename() == null ? "arquivo.pdf" : pdfFile.getOriginalFilename();
            String safeOriginal = sanitizeFilename(original);

            boolean mimeOk = "application/pdf".equalsIgnoreCase(contentType);
            boolean extOk = safeOriginal.toLowerCase().endsWith(".pdf");

            if (!mimeOk || !extOk) {
                throw new IllegalArgumentException("Apenas arquivos PDF são permitidos.");
            }

            String uploadDir = "uploads/pdfs";
            String fileName = UUID.randomUUID() + "_" + safeOriginal;
            Path caminho = Paths.get(uploadDir, fileName);

            Files.createDirectories(caminho.getParent());
            Files.copy(pdfFile.getInputStream(), caminho, StandardCopyOption.REPLACE_EXISTING);

            livroDTO.setPdfUrl("/uploads/pdfs/" + fileName);
        }

        try {
            livroService.salvar(livroDTO);
            return "redirect:/livros";
        }catch (LivroDuplicadoException | AnoPublicacaoInvalidoException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("livroDTO", livroDTO);
            return "form"; // volta para o form com a mensagem
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable Long id) throws IOException {
        LivroDTO livro = livroService.buscarPorId(id);
        String pdfUrl = livro.getPdfUrl();

        if (pdfUrl == null || pdfUrl.isBlank()) {
            return ResponseEntity.notFound().build();
        }

        // pdfUrl vem como "/uploads/pdfs/arquivo.pdf" → resolvemos para o disco
        String relative = pdfUrl.replaceFirst("^/uploads/", ""); // "pdfs/arquivo.pdf"
        Path path = Paths.get("uploads").resolve(relative).normalize();

        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            return ResponseEntity.notFound().build();
        }

        UrlResource resource = new UrlResource(path.toUri());
        String filename = sanitizeFilename(
                (livro.getNome() != null ? livro.getNome() : "livro") + ".pdf"
        );

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
                .contentType(new MediaType("application", "pdf"))
                .body(resource);
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        LivroDTO livroDTO = livroService.buscarPorId(id);
        model.addAttribute("livroDTO", livroDTO);
        return "form";
    }

    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        livroService.deletar(id);
        return "redirect:/livros";
    }

    @GetMapping("/search")
    @ResponseBody
    public List<LivroDTO> search(@RequestParam("nome") String nome) {
        return livroService.buscarPorNome(nome); // deve retornar objetos com pelo menos nome e capaUrl
    }

    // ===== util simples para nomes de arquivo =====
    private String sanitizeFilename(String name) {
        if (name == null) return "file";
        return name.replaceAll("[^a-zA-Z0-9\\.\\-\\_]", "_");
    }
}
