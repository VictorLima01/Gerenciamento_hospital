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

import com.backend.produtos.produtosestoque.model.Medicamento;
import com.backend.produtos.produtosestoque.model.Paciente;
import com.backend.produtos.produtosestoque.model.Pratileira;
import com.backend.produtos.produtosestoque.repository.MedicamentoRepository;
import com.backend.produtos.produtosestoque.repository.PratileiraRepository;

@RestController
@RequestMapping("/api")
public class MedicamentosController {
	@Autowired
    private MedicamentoRepository medicamentoRepository;
	
	@Autowired
    private PratileiraRepository pratileiraRepository;
	
	@GetMapping("/medicamentos")
    public List<Medicamento> list() {
        return medicamentoRepository.findAll();
    }
	
	
	@PostMapping("/medicamentos/cadastrar")
	   public ResponseEntity createMedicamento(@Valid @RequestBody Medicamento medicamento) {
		   List<Medicamento> medicamentos = medicamentoRepository.findAll();
		   List<Pratileira> pratileiras = pratileiraRepository.findAll();
		   if(medicamentos.size() == 0) {
			   medicamentoRepository.save(medicamento);
		   }else {
			   for (Medicamento medicamentoType : medicamentos) {
				    if(medicamentoType.getNome().equals(medicamento.getNome())) {
				    	System.out.println("Nome duplicado, digite um nome válido!");
				    	return new ResponseEntity<>("Nome duplicado, digite um nome válido!", HttpStatus.BAD_REQUEST);
				    }else if(pratileiras.size() == 0){
				    	System.out.println("Você precisa guardar esse remédio em uma pratileira, por favor cadastre uma!");
				    	return new ResponseEntity<>("Você precisa guardar esse remédio em uma pratileira, por favor cadastre uma!", HttpStatus.BAD_REQUEST);
				    }else {
				    	medicamentoRepository.save(medicamento);
				    }
				}
		   }
		   return new ResponseEntity<>(medicamento, HttpStatus.CREATED);
	   }
	
	@GetMapping(value="/medicamentos/alocar-pratileira/id_medicamento={id_medicamento}&id_pratileira={id_pratileira}")
	   public ResponseEntity alocarMedicamentosParaPacientes(@PathVariable("id_medicamento") Long id_medicamento, @PathVariable("id_pratileira") Long id_pratileira){
		return medicamentoRepository.findById(id_medicamento)
	              .map(record -> {
	            	  pratileiraRepository.findById(id_pratileira).map(pratileira -> {
	 	            		 record.setListasPratileiras(pratileira);
	 	            		 return ResponseEntity.ok().build();
	 	            	  });	 	            	  
	            	  	medicamentoRepository.save(record);
	 	            	  return new ResponseEntity<>(record, HttpStatus.OK);
	              }).orElse(ResponseEntity.notFound().build());
	   }

	    @DeleteMapping(path= {"/medicamentos/{id}"})
	   public ResponseEntity delete(@PathVariable long id) throws SQLException, ClassNotFoundException {
		medicamentoRepository.deleteCopyByTradeId(id);
		medicamentoRepository.deleteById(id);
		return null;
	   }
	
	@PutMapping(value="/medicamentos/{id}")
	   public ResponseEntity update(@PathVariable("id") long id, @RequestBody Medicamento medicamentos) {
		   return medicamentoRepository.findById(id)
				   .map(record -> {
					   record.setNome(medicamentos.getNome());
					   record.setQtd(medicamentos.getQtd());
					   record.setEfeitos(medicamentos.getEfeitos());
						 medicamentoRepository.save(record);
						 return new ResponseEntity<>(medicamentos, HttpStatus.OK);
					   }).orElse(ResponseEntity.notFound().build());	   	
	   }
}
