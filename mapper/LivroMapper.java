package br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.mapper;

import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.DTO.LivroDTO;
import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.entities.LivroEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface LivroMapper {

    // Converte LivroDTO para LivroEntity
    @Mapping(target = "id", source = "dto.id")
    LivroEntity toEntity(LivroDTO dto);

    // Converte LivroEntity para LivroDTO
    LivroDTO toDTO(LivroEntity entity);
}

