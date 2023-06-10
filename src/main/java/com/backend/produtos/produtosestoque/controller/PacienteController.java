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
import com.backend.produtos.produtosestoque.model.Paciente;
import com.backend.produtos.produtosestoque.repository.MedicamentoRepository;
import com.backend.produtos.produtosestoque.repository.PacienteRepository;

@RestController
@RequestMapping("/api")
public class PacienteController {
	
	@Autowired
    private PacienteRepository pacienteRepository;
	
	@Autowired
    private MedicamentoRepository medicamentoRepository;
	
	@GetMapping("/pacientes")
    public List<Paciente> list() {
        return pacienteRepository.findAll();
    }
	
	@PostMapping("/pacientes/cadastrar")
	   public ResponseEntity createPaciente(@Valid @RequestBody Paciente paciente) {
		   List<Paciente> pacientes = pacienteRepository.findAll();
		   if(pacientes.size() == 0) {
			   pacienteRepository.save(paciente);
		   }else {
			   for (Paciente pacienteType : pacientes) {
				    if(pacienteType.getEmail().equals(paciente.getEmail())) {
				    	System.out.println("Email duplicado, digite um email válido!");
				    	return new ResponseEntity<>("Email duplicado, digite um email válido!", HttpStatus.BAD_REQUEST);
				    }else {
				    	pacienteRepository.save(paciente);
				    }
				}
		   }
		   return new ResponseEntity<>(paciente, HttpStatus.CREATED);
	   }
	
	@GetMapping(value="/pacientes/alocar-medicamentos/id_paciente={id_paciente}&id_medicamento={id_medicamento}")
	   public ResponseEntity alocarMedicamentosParaPacientes(@PathVariable("id_paciente") Long id_paciente, @PathVariable("id_medicamento") Long id_medicamento){
		return pacienteRepository.findById(id_paciente)
	              .map(record -> {
	            	  medicamentoRepository.findById(id_medicamento).map(medicamento -> {
	            		  if(medicamento.getListasPratileiras().size() == 0) {
	            			  return new ResponseEntity<>("Adicione esse remédio em uma pratileira", HttpStatus.BAD_REQUEST);
	            		  }else {
	            			   medicamento.aleterarQtd(1);
		 	            	   record.setListasMedicamentos(medicamento);
		 	            	   return ResponseEntity.ok().build();
	            		  }
	 	            	  });	 	            	  
	            	  	  pacienteRepository.save(record);
	 	            	  return new ResponseEntity<>(record, HttpStatus.OK);
	              }).orElse(ResponseEntity.notFound().build());
	   }

	   @DeleteMapping(path= {"/pacientes/{id}"})
	   public ResponseEntity delete(@PathVariable long id) throws SQLException, ClassNotFoundException {
		pacienteRepository.deleteCopyByTradeId(id);
		pacienteRepository.deleteById(id);
		return null;
	   }
	
	@PutMapping(value="/pacientes/{id}")
	   public ResponseEntity update(@PathVariable("id") long id, @RequestBody Paciente pacientes) {
		   return pacienteRepository.findById(id)
				   .map(record -> {
					   record.setNome(pacientes.getNome());
					   record.setIdade(pacientes.getIdade());
					   record.setEmail(pacientes.getEmail());
					   record.setConvenio(pacientes.getConvenio());
						 pacienteRepository.save(record);
						 return new ResponseEntity<>(pacientes, HttpStatus.OK);
					   }).orElse(ResponseEntity.notFound().build());	   	
	   }

}
