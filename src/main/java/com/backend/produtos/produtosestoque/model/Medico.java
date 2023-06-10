package com.backend.produtos.produtosestoque.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="MEDICO")
public class Medico {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name ="id_medico")
	private Long id_medico;
	
	private String nome;
	private String email;
	private String telefone;
	private String endereco;
	private String especialidade;
	
	@OneToMany(targetEntity=Paciente.class, fetch=FetchType.EAGER)
    private List<Paciente> listasPacientes;
	
	public Medico() {  
		super();
	}
	
	
	public List<Paciente> getListasPacientes() {
		return listasPacientes;
	}
	public void setListasPacientes(Paciente paciente) {
		this.listasPacientes.add(paciente);
	}
    
	public Long getId_medico() {
		return id_medico;
	}

	public void setId_medico(Long id_medico) {
		this.id_medico = id_medico;
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

	public String getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}
	
	

}
