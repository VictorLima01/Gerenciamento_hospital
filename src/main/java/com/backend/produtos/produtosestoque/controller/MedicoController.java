package com.backend.produtos.produtosestoque.controller;

import java.sql.SQLException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.produtos.produtosestoque.model.Medico;
import com.backend.produtos.produtosestoque.model.Pessoa;
import com.backend.produtos.produtosestoque.repository.MedicamentoRepository;
import com.backend.produtos.produtosestoque.repository.MedicoRepository;
import com.backend.produtos.produtosestoque.repository.PacienteRepository;

@RestController
@RequestMapping("/api")
public class MedicoController {
	@Autowired
    private MedicoRepository medicoRepository;
	
	@Autowired
    private PacienteRepository pacienteRepository;
	
	@Autowired
    private MedicamentoRepository medicamentoRepository;
	
	@GetMapping("/medicos")
    public List<Medico> list() {
        return medicoRepository.findAll();
    }
	
	@PostMapping("/medicos/cadastrar")
	   public ResponseEntity createPessoa(@Valid @RequestBody Medico medico) {
		   List<Medico> medicos = medicoRepository.findAll();
		   if(medicos.size() == 0) {
			   medicoRepository.save(medico);
		   }else {
			   for (Medico medicoType : medicos) {
				    if(medicoType.getEmail().equals(medico.getEmail())) {
				    	System.out.println("Email duplicado, digite um email válido!");
				    	return new ResponseEntity<>("Email duplicado, digite um email válido!", HttpStatus.BAD_REQUEST);
				    }else {
				    	medicoRepository.save(medico);
				    }
				}
		   }
		   return new ResponseEntity<>(medico, HttpStatus.CREATED);
	   }
	
	@GetMapping(value="/medicos/alocar-pacientes/id_medico={id_medico}&id_paciente={id_paciente}")
	   public ResponseEntity alocarPacientesParaMedico(@PathVariable("id_medico") Long id_medico, @PathVariable("id_paciente") Long id_paciente){
		return medicoRepository.findById(id_medico)
	              .map(record -> {
	            	  pacienteRepository.findById(id_paciente).map(paciente -> {
	 	            		 record.setListasPacientes(paciente);
	 	            		 return ResponseEntity.ok().build();
	 	            	  });	 	            	  
	            	  	  medicoRepository.save(record);
	 	            	  return new ResponseEntity<>(record, HttpStatus.OK);
	              }).orElse(ResponseEntity.notFound().build());
	   }

	   @DeleteMapping(path= {"/medicos/{id}"})
	   public ResponseEntity delete(@PathVariable long id) throws SQLException, ClassNotFoundException {
		medicoRepository.deleteCopyByTradeId(id);
		medicoRepository.deleteById(id);
		return null;
	   }
	
	@PutMapping(value="/medicos/{id}")
	   public ResponseEntity update(@PathVariable("id") long id, @RequestBody Medico medicos) {
		   return medicoRepository.findById(id)
				   .map(record -> {
					   record.setNome(medicos.getNome());
					   record.setEmail(medicos.getEmail());
					   record.setTelefone(medicos.getTelefone());
					   record.setEndereco(medicos.getEndereco());
					   record.setEspecialidade(medicos.getEspecialidade());
						 medicoRepository.save(record);
						 return new ResponseEntity<>(medicos, HttpStatus.OK);
					   }).orElse(ResponseEntity.notFound().build());	   	
	   }
}
