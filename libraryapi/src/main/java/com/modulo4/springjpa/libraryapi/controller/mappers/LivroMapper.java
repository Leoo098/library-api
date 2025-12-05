package com.modulo4.springjpa.libraryapi.controller.mappers;

import com.modulo4.springjpa.libraryapi.controller.dto.CadastroLivroDTO;
import com.modulo4.springjpa.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.modulo4.springjpa.libraryapi.model.Livro;
import com.modulo4.springjpa.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDTO dto);

    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
}
