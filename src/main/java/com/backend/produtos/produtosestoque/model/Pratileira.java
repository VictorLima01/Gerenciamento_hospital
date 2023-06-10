package com.backend.produtos.produtosestoque.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="PRATILEIRA")
public class Pratileira {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name ="id_pratileira")
	private Long id_pratileira;
	
	private String nome_pratileira;
	private String setor;
	
	public Pratileira() {
		super();
	}

	public Long getId_pratileira() {
		return id_pratileira;
	}

	public void setId_pratileira(Long id_pratileira) {
		this.id_pratileira = id_pratileira;
	}

	public String getNome_pratileira() {
		return nome_pratileira;
	}

	public void setNome_pratileira(String nome_pratileira) {
		this.nome_pratileira = nome_pratileira;
	}

	public String getSetor() {
		return setor;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	}
	
}
