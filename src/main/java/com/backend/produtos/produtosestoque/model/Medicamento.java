package com.backend.produtos.produtosestoque.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="MEDICAMENTO")
public class Medicamento {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name ="id_medicamento")
	private Long id_medicamento;
	
	private String nome;
	private int qtd;
	private String efeitos;
	
	@OneToMany(targetEntity=Pratileira.class, fetch=FetchType.EAGER)
    private List<Pratileira> listasPratileiras;
	
	public Medicamento() {
		super();
	}
	
	public List<Pratileira> getListasPratileiras() {
		return listasPratileiras;
	}
	public void setListasPratileiras(Pratileira pratileira) {
		this.listasPratileiras.add(pratileira);
	}

	public Long getId_medicamento() {
		return id_medicamento;
	}

	public void setId_medicamento(Long id_medicamento) {
		this.id_medicamento = id_medicamento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}
	
	public String getEfeitos() {
		return efeitos;
	}

	public void setEfeitos(String efeitos) {
		this.efeitos = efeitos;
	}

	public void aleterarQtd(int qtd2) {
		this.qtd = this.qtd - qtd;
		
	}
	
	
		
}
