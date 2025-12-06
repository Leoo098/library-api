package com.springjpa.libraryapi.controller.mappers;

import com.springjpa.libraryapi.controller.dto.UsuarioDTO;
import com.springjpa.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);
}
