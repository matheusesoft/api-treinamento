package br.com.livraria.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "Livro")
@Entity
public class Livro {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="livro_sequence")
	@SequenceGenerator(name="livro_sequence", sequenceName="livro_seq")
	private Integer idLivro;

	private String nome;

	private int quantidadeDePaginas;

	private String dataDeLancamento;

	@ManyToOne
	private Autor autor;

	@ManyToOne
	private Categoria categoria;

	private String ISBN;

	private int quantidadeEmEstoque;

	private double preco;

	public boolean existeEmEstoque(int quandidadeDoPedido) {
		if (quandidadeDoPedido > quantidadeEmEstoque) {
			return false;
		}

		return true;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getQuantidadeDePaginas() {
		return quantidadeDePaginas;
	}

	public void setQuantidadeDePaginas(int quantidadeDePaginas) {
		this.quantidadeDePaginas = quantidadeDePaginas;
	}

	public String getDataDeLancamento() {
		return dataDeLancamento;
	}

	public void setDataDeLancamento(String dataDeLancamento) {
		this.dataDeLancamento = dataDeLancamento;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public int getQuantidadeEmEstoque() {
		return quantidadeEmEstoque;
	}

	public void setQuantidadeEmEstoque(int quantidadeEmEstoque) {
		this.quantidadeEmEstoque = quantidadeEmEstoque;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public Integer getIdLivro() {
		return idLivro;
	}

	public void setIdLivro(Integer idLivro) {
		this.idLivro = idLivro;
	}

}
