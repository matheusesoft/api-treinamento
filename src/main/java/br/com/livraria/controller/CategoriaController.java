package br.com.livraria.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.livraria.domain.Categoria;
import br.com.livraria.repository.CategoriaRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("categorias")
@Api(value = "Categorias Controller")
public class CategoriaController {

	private CategoriaRepository categoriaRepository;

	public CategoriaController(CategoriaRepository categoriaRepository) {
		this.categoriaRepository = categoriaRepository;
	}

	@ApiOperation(value = "Adicionar uma categoria.")
	@PostMapping
	public ResponseEntity<Categoria> adiciona(@Valid @RequestBody Categoria categoria) {
		categoriaRepository.save(categoria);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/{id}")
				.buildAndExpand(categoria.getIdCategoria()).toUri();

		return ResponseEntity.created(location).body(categoria);
	}

	@ApiOperation(value = "Listar todas as categorias")
	@GetMapping("/lista/todos")
	public ResponseEntity<List<Categoria>> listaTodos() {
		return ResponseEntity.ok(categoriaRepository.findAll());
	}

	@ApiOperation(value = "Pegar lista de categorias com paginação.")
	@GetMapping("/lista")
	public ResponseEntity<Page<Categoria>> listaComPaginacao(@PageableDefault(page = 0, size = 10) Pageable pageable) {
		return ResponseEntity.ok(categoriaRepository.findAll(pageable));
	}

	@ApiOperation(value = "Buscar categoria por id.")
	@GetMapping("/buscaporid/{id}")
	public ResponseEntity<Categoria> buscaPorId(@PathVariable("id") Integer id) {
		return categoriaRepository.findById(id).map(category -> ResponseEntity.ok().body(category))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@ApiOperation(value = "Atualizar categoria por id.")
	@PutMapping("/atualiza/{id}")
	public ResponseEntity<Categoria> atualiza(@RequestBody Categoria categoria, @PathVariable("id") Integer id) {
		return categoriaRepository.findById(id).map(c -> {

			c.setDescricao(categoria.getDescricao());
			c.setNome(categoria.getNome());

			return ResponseEntity.ok(c);
		}).orElse(ResponseEntity.notFound().build());
	}

	@ApiOperation(value = "Deletar categoria por id.")
	@DeleteMapping("/deleta/{id}")
	public ResponseEntity<?> remove(@PathVariable("id") Integer id) {
		return categoriaRepository.findById(id).map(categoria -> {
			categoriaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}).orElse(ResponseEntity.notFound().build());
	}
}
