package br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.service;

import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.DTO.LivroDTO;
import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.entities.LivroEntity;
import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.excecao.AnoPublicacaoInvalidoException;
import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.excecao.LivroDuplicadoException;
import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.mapper.LivroMapper;
import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final LivroMapper livroMapper;


    public void salvar(LivroDTO dto) {
        
        if(dto.getId() == null){
            //salvando um livro novo
            if(livroRepository.existsByNome(dto.getNome())){
                throw new LivroDuplicadoException("Já existe um livro com esse nome");
            }           
        }else{
            //editando um livro   
            if(livroRepository.existsByNomeAndIdNot(dto.getNome(), dto.getId())){
                throw new LivroDuplicadoException("Já existe um livro com esse nome");
                }
        }

        int anoAtual = LocalDate.now().getYear();
        if(dto.getAnoPublicacao() < 1500 || dto.getAnoPublicacao() > anoAtual ){
            throw new AnoPublicacaoInvalidoException("Ano de publicação inválido!");
        }

        LivroEntity entity = livroMapper.toEntity(dto);
        livroRepository.save(entity);
    }

    public List<LivroDTO> listar() {
        return livroRepository.findAll().stream()
                .map(livroMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deletar(Long id) {
        livroRepository.deleteById(id);
    }

    // Buscar livros pelo nome (usado no autocomplete)
    public List<LivroDTO> buscarPorNome(String nome) {
        return livroRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(livroMapper::toDTO)
                .collect(Collectors.toList());
    }

    public LivroDTO buscarPorId(Long id) {
        return livroRepository.findById(id)
            .map(livroMapper::toDTO)
            .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
    }

}


