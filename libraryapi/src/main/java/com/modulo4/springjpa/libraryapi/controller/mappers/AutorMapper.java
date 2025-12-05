package com.modulo4.springjpa.libraryapi.controller.mappers;

import com.modulo4.springjpa.libraryapi.controller.dto.AutorDTO;
import com.modulo4.springjpa.libraryapi.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    //@Mapping(source = "nome", target = "nomeAutor")  usado quando o nome da coluna é diferente da propriedade
    @Mapping(source = "dataNascimento", target = "dataNascimento")
    @Mapping(source = "nacionalidade", target = "nacionalidade")
    Autor toEntity(AutorDTO dto);

    AutorDTO toDTO(Autor autor);
}
