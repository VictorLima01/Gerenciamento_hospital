package com.backend.produtos.produtosestoque.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity(name="ENFERMEIRO")
public class Enfermeiro {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name ="id_enfermeiro")
	private Long id_enfermeiro;
	
	private String nome;
	private String email;
	private String telefone;
	private String endereco;
	private String setor;
	
	public String getSetor() {
		return setor;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	}

	@OneToMany(targetEntity=Pratileira.class, fetch=FetchType.EAGER)
    private List<Pratileira> pratileiraSobResponsabilidade;
	
	public Enfermeiro() {  
		super();
	}
	
	public List<Pratileira> getPratileiraSobResponsabilidade() {
		return pratileiraSobResponsabilidade;
	}
	public void setPratileiraSobResponsabilidade(Pratileira pratileira) {
		this.pratileiraSobResponsabilidade.add(pratileira);
	}

	public Long getId_enfermeiro() {
		return id_enfermeiro;
	}

	public void setId_enfermeiro(Long id_enfermeiro) {
		this.id_enfermeiro = id_enfermeiro;
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

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	
}
