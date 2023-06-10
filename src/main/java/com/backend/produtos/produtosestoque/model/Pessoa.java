package com.backend.produtos.produtosestoque.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;


@Entity(name="PESSOA")
public class Pessoa {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name ="id")
	private Long id;
	
	private String nome;
	private String email;
	private String telefone;
	private String password;
	
	@OneToMany(targetEntity=Produto.class, fetch=FetchType.EAGER)
    private List<Produto> listasProdutos;
	
	public Pessoa() {
		super();
	}
	
	public List<Produto> getListasProdutos() {
		return listasProdutos;
	}
	public void setListasProdutos(Produto produto) {
		this.listasProdutos.add(produto);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
