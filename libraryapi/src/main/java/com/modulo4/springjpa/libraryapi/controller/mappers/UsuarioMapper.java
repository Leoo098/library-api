package com.modulo4.springjpa.libraryapi.controller.mappers;

import com.modulo4.springjpa.libraryapi.controller.dto.UsuarioDTO;
import com.modulo4.springjpa.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);
}
