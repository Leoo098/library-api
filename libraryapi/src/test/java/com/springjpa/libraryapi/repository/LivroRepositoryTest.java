package com.springjpa.libraryapi.repository;

import com.springjpa.libraryapi.model.Autor;
import com.springjpa.libraryapi.model.GeneroLivro;
import com.springjpa.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest() {

        Livro livro = new Livro();
        livro.setIsbn("90887-94632");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("Ciências");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = autorRepository
                .findById(UUID.fromString("348064ef-7958-40d8-8e78-adabcfd88b3e"))
                .orElse(null);

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void salvarAutorELivroTest(){

        Livro livro = new Livro();
        livro.setIsbn("90887-94632");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Terceiro Livro");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = new Autor();
        autor.setNome("Claudio");
        autor.setDataNascimento(LocalDate.of(1951, 1, 31));
        autor.setNacionalidade("Brasileira");

        autorRepository.save(autor);

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void salvarCascadeTest(){

        Livro livro = new Livro();
        livro.setIsbn("90887-94632");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Outro Livro");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

//        Autor autor = autorRepository
//                .findById(UUID.fromString("86b289fa-7066-487e-a054-2247dbbf8de8"))
//                .orElse(null);

        Autor autor = new Autor();
        autor.setNome("João");
        autor.setDataNascimento(LocalDate.of(1951, 1, 31));
        autor.setNacionalidade("Brasileira");

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void atualizarAutorDoLivro(){

        UUID id = UUID.fromString("c6c4808a-5a06-4668-8f73-8cc1964e77bf");
        var livroParaAtualizar = repository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("86b289fa-7066-487e-a054-2247dbbf8de8");
        Autor maria = autorRepository.findById(idAutor).orElse(null);

        livroParaAtualizar.setAutor(maria);

        repository.save(livroParaAtualizar);
    }

    @Test
    void deletar(){
        UUID id = UUID.fromString("c6c4808a-5a06-4668-8f73-8cc1964e77bf");
        var livroParaAtualizar = repository.findById(id).orElse(null);
        repository.deleteById(id);
    }

    @Test
    @Transactional
    void buscarLivroTest(){
        UUID id = UUID.fromString("4329b677-148a-4aaa-9595-6cb1a49f4939");
        Livro livro = repository.findById(id).orElse(null);
        System.out.println("Livro:");
        System.out.println(livro.getTitulo());
        System.out.println("Autor:");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void pesquisaPorTituloTest(){

        List<Livro> lista = repository.findByTitulo("O roubo da casa assombrada");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorIsbnTest(){
        Optional<Livro> livro = repository.findByIsbn("20857-94122");
        livro.ifPresent(System.out::println);
    }

    @Test
    void pesquisaPorTituloEPreco(){
        var preco = BigDecimal.valueOf(204.00);
        var tituloPesquisa = "O roubo da casa assombrada";

        List<Livro> lista = repository.findByTituloAndPreco(tituloPesquisa, preco);
        lista.forEach(System.out::println);
    }

    @Test
    void listarLivrosComQueryJPQL(){
        var resultado = repository.listarTodosOrdenadoPorTituloAndPreco();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarAutoresDosLivros(){
        var resultado = repository.listarAutoresDosLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarTitulosNaoRepetidosDosLivros(){
        var resultado = repository.listarNomesDiferentesLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarGenerosDeLivrosAutoresBrasileiros(){
        var resultado = repository.listarGenerosAutoresBrasileiros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroQueryParamTest(){
        var resultado = repository.findByGenero(GeneroLivro.MISTERIO, "preco");
        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroPositionalParamTest(){
        var resultado = repository.findByGeneroPositionalParameters(GeneroLivro.MISTERIO, "preco");
        resultado.forEach(System.out::println);
    }

    @Test
    void deletePorGeneroTest(){
        repository.deleteByGenero(GeneroLivro.CIENCIA);
    }

    @Test
    void updateDataPublicacaoTeste(){
        repository.updateDataPublicacao(LocalDate.of(2000, 1, 1));
    }

}