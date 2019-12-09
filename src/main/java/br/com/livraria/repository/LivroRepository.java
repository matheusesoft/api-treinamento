package br.com.livraria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.livraria.domain.Livro;

public interface LivroRepository extends JpaRepository<Livro, Integer> {
	
	Livro findByNome(String nome);
	
}
