package com.modulo4.springjpa.libraryapi.service;

import com.modulo4.springjpa.libraryapi.model.Autor;
import com.modulo4.springjpa.libraryapi.model.GeneroLivro;
import com.modulo4.springjpa.libraryapi.model.Livro;
import com.modulo4.springjpa.libraryapi.repository.AutorRepository;
import com.modulo4.springjpa.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public void atualizacaoSemAtualizar(){
        var livro = livroRepository
                .findById(UUID.fromString("4329b677-148a-4aaa-9595-6cb1a49f4939"))
                .orElse(null);

        livro.setDataPublicacao(LocalDate.of(2024, 6, 1));
    }

    @Transactional
    public void executar(){
        // salva o autor
        Autor autor = new Autor();
        autor.setNome("Teste");
        autor.setDataNascimento(LocalDate.of(1951, 1, 31));
        autor.setNacionalidade("Brasileira");

        autorRepository.save(autor);

        //salva o livro
        Livro livro = new Livro();
        livro.setIsbn("90887-94632");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("Teste Livro");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        livro.setAutor(autor);

        livroRepository.save(livro);

        if (autor.getNome().equals("Teste Francisco")){
            throw new RuntimeException("Rollback!");
        }
    }

}
