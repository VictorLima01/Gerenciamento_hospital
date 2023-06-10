package com.backend.produtos.produtosestoque.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="SETOR")
public class Setor {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name ="id_setor")
	private Long id_setor;
	
	private String nome;
	@OneToMany(targetEntity=Enfermeiro.class, fetch=FetchType.EAGER)
    private List<Enfermeiro> listaEnfermeiro;
	
	public Setor() {  
		super();
	}
	
	public Long getId_setor() {
		return id_setor;
	}

	public void setId_setor(Long id_setor) {
		this.id_setor = id_setor;
	}

	public List<Enfermeiro> getListaEnfermeiro() {
		return listaEnfermeiro;
	}
	public void setListaEnfermeiro(Enfermeiro enfermeiro) {
		this.listaEnfermeiro.add(enfermeiro);
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
