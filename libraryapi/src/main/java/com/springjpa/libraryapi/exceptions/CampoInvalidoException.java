package com.springjpa.libraryapi.exceptions;

import lombok.Getter;

@Getter
public class CampoInvalidoException extends RuntimeException{

    private String campo;

    public CampoInvalidoException(String campo, String mensagem){
        super(mensagem);
        this.campo = campo;
    }
}
