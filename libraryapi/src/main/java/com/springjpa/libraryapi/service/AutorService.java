package com.springjpa.libraryapi.service;

import com.springjpa.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.springjpa.libraryapi.model.Autor;
import com.springjpa.libraryapi.model.Usuario;
import com.springjpa.libraryapi.repository.AutorRepository;
import com.springjpa.libraryapi.repository.LivroRepository;
import com.springjpa.libraryapi.security.SecurityService;
import com.springjpa.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository repository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;
    private final SecurityService securityService;

    // Sem a anotation do Lombook que gera o construtor é necessário:

//    public AutorService(AutorRepository repository,
//                        AutorValidator validator,
//                        LivroRepository livroRepository) {
//
//        this.repository = repository;
//        this.validator = validator;
//        this.livroRepository = livroRepository;
//    }

    public Autor salvar(Autor autor) {
        validator.validar(autor);
        Usuario usuario = securityService.obterUsuarioLogado();
        autor.setUsuario(usuario);
        return repository.save(autor);
    }

    public void atualizar(Autor autor) {
        if (autor.getId() == null){
            throw new IllegalArgumentException("Para atualizar, é necessário que o autor já esteja salvo na base.");
        }
        validator.validar(autor);
        repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletar(Autor autor){
        if (possuiLivro(autor)){
            throw new OperacaoNaoPermitidaException(
                    "Não é permitido excluir um autor que possui livros cadastrados!");
        }
        repository.delete(autor);
    }

    public List<Autor> pesquisa(String nome, String nacionalidade){
        if (nome != null && nacionalidade != null){
            return repository.findByNomeAndNacionalidade(nome, nacionalidade);
        }

        if (nome != null){
            return repository.findByNome(nome);
        }

        if (nacionalidade != null){
            return repository.findByNacionalidade(nacionalidade);
        }

        return repository.findAll();
    }

    public List<Autor> pesquisaByExample(String nome, String nacionalidade){
        var autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnorePaths("id", "dataNascimento", "dataCadastro")
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample = Example.of(autor, matcher);
        return repository.findAll(autorExample);
    }

    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }

}
