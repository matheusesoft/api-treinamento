package br.com.livraria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.livraria.domain.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
	
	Categoria findByNome(String nome);
}
