package br.com.livraria.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import br.com.livraria.domain.Autor;
import br.com.livraria.domain.Categoria;
import br.com.livraria.domain.Livro;
import br.com.livraria.repository.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("livros")
@Api(value = "Livros Controller")
public class LivroController {

	private LivroRepository livroRepository;
	private AutorRepository autorRepository;
	private CategoriaRepository categoriaRepository;

	public LivroController(LivroRepository livroRepository, AutorRepository autorRepository, CategoriaRepository categoriaRepository) {
		this.livroRepository = livroRepository;
		this.autorRepository = autorRepository;
		this.categoriaRepository = categoriaRepository;
	}

	@ApiOperation(value = "Adicionar livro.")
	@PostMapping("/adiciona")
	public ResponseEntity<Livro> adiciona(@Valid @RequestBody Livro livro) {
		Autor autor = autorRepository.findByNome(livro.getAutor().getNome());
		if(autor != null ) {
			livro.setAutor(autor);
		} else {
			livro.setAutor(autorRepository.save(livro.getAutor()));
		}
		
		Categoria categoria = categoriaRepository.findByNome(livro.getCategoria().getNome());
		if(categoria != null) {
			livro.setCategoria(categoria);
		} else {
			livro.setCategoria(categoriaRepository.save(livro.getCategoria()));
		}
		livroRepository.save(livro);
		
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}").buildAndExpand(livro.getIdLivro())
				.toUri();

		return ResponseEntity.created(location).body(livro);
	}
	
	@ApiOperation(value = "Adicionar vários livros.")
	@PostMapping("/adiciona/todos")
	public ResponseEntity<List<Livro>> adicionaTodos(@Valid @RequestBody List<Livro> livros) {
		List<Integer> livrosIds = new ArrayList<Integer>();
		for (Livro livro : livros) {
			Autor autor = autorRepository.findByNome(livro.getAutor().getNome());
			if(autor != null ) {
				livro.setAutor(autor);
			} else {
				livro.setAutor(autorRepository.save(livro.getAutor()));
			}
			
			Categoria categoria = categoriaRepository.findByNome(livro.getCategoria().getNome());
			if(categoria != null) {
				livro.setCategoria(categoria);
			} else {
				livro.setCategoria(categoriaRepository.save(livro.getCategoria()));
			}
		}
		
		List<Livro> livrosResponse = livroRepository.saveAll(livros);
		for (Livro livro : livrosResponse) {
			livrosIds.add(livro.getIdLivro());
		}
		return ResponseEntity.ok(livroRepository.findAllById(livrosIds));
	}

	@ApiOperation(value = "Listar todos os livros.")
	@GetMapping("/lista/todos")
	public ResponseEntity<List<Livro>> listaTodos() {
		return ResponseEntity.ok(livroRepository.findAll());
	}

	@ApiOperation(value = "Listar livros com paginação.")
	@GetMapping("/paginado")
	public ResponseEntity<?> listaComPaginacao(@PageableDefault(size = 10, page = 0) Pageable pageable) {
		return ResponseEntity.ok(livroRepository.findAll(pageable));
	}

	@ApiOperation(value = "Buscar livro por id.")
	@GetMapping("/buscaPorId/{id}")
	public ResponseEntity<Optional<Livro>> buscaPorId(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(livroRepository.findById(id));
	}
	
	@ApiOperation(value = "Buscar livro passando nome por parâmetro.")
	@GetMapping
	public ResponseEntity<Livro> buscaPorNome(@RequestParam("nome") String nome) {
		Livro livro = livroRepository.findByNome(nome);
		return ResponseEntity.ok(livro);
	}
	
	@ApiOperation(value = "Atualizar livro.")
	@PutMapping
	public ResponseEntity<Livro> atualiza(@RequestBody Livro livro) {
		Autor autor = autorRepository.findByNome(livro.getAutor().getNome());
		if(autor != null ) {
			livro.setAutor(autor);
		} else {
			livro.setAutor(autorRepository.save(livro.getAutor()));
		}
		
		Categoria categoria = categoriaRepository.findByNome(livro.getCategoria().getNome());
		if(categoria != null) {
			livro.setCategoria(categoria);
		} else {
			livro.setCategoria(categoriaRepository.save(livro.getCategoria()));
		}
		return ResponseEntity.ok(livroRepository.save(livro));
	}

	@ApiOperation(value = "Deletar livro por id.")
	@DeleteMapping("/{id}")
    public ResponseEntity<Livro> deletarLivroPorId(@PathVariable("id") Integer id) {

		Livro livro = livroRepository.findById(id).get();
        if (livro == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        livroRepository.delete(livro);

        return ResponseEntity.ok(livro);
    }
	
	@ApiOperation(value = "Deletar todos os livros, autores e categorias.")
	@DeleteMapping("/delete/all/cascade")
	public void deleteAll() {
		List<Livro> livros = livroRepository.findAll();
		List<Autor> autores = autorRepository.findAll();
		List<Categoria> categorias = categoriaRepository.findAll();
		
		for (Livro livro : livros) {
			livroRepository.delete(livro);
		}
		
		for (Categoria categoria : categorias) {
			categoriaRepository.delete(categoria);
		}
		
		for (Autor autor : autores) {
			autorRepository.delete(autor);
		}
		
	}
}
