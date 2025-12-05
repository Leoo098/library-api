package com.modulo4.springjpa.libraryapi.security;

import com.modulo4.springjpa.libraryapi.model.Usuario;
import com.modulo4.springjpa.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UsuarioService usuarioService;

    public Usuario obterUsuarioLogado(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof CustomAuthentication customAuth){
            return customAuth.getUsuario();
        }

        return null;

//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        String login = userDetails.getUsername();
//        return usuarioService.obterPorLogin(login);
    }
}
