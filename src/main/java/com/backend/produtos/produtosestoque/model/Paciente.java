package com.backend.produtos.produtosestoque.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name="PACIENTE")
public class Paciente {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name ="id_paciente")
	private Long id_paciente;
	
	private String nome;
	private int idade;
	private String email;
	private String convenio;
	
	@OneToMany(targetEntity=Medicamento.class, fetch=FetchType.EAGER)
    private List<Medicamento> listasMedicamentos;
	
	public Paciente() {
		super();
	}
	
	public List<Medicamento> getListasMedicamentos() {
		return listasMedicamentos;
	}
	public void setListasMedicamentos(Medicamento medicamento) {
		this.listasMedicamentos.add(medicamento);
	}
	public Long getId_paciente() {
		return id_paciente;
	}

	public void setId_paciente(Long id_paciente) {
		this.id_paciente = id_paciente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConvenio() {
		return convenio;
	}

	public void setConvenio(String convenio) {
		this.convenio = convenio;
	}
	
	
}
