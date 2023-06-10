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
import com.backend.produtos.produtosestoque.model.Pratileira;
import com.backend.produtos.produtosestoque.repository.PratileiraRepository;

@RestController
@RequestMapping("/api")   
public class PratileiraController {
	
	@Autowired
    private PratileiraRepository pratileiraRepository;
	
	@GetMapping("/pratileira")
    public List<Pratileira> list() {
        return pratileiraRepository.findAll();
    }
	
	@PostMapping("/pratileira/cadastrar")
	   public ResponseEntity createMedicamento(@Valid @RequestBody Pratileira pratileira) {
		   List<Pratileira> pratileiras = pratileiraRepository.findAll();
		   if(pratileiras.size() == 0) {
			   pratileiraRepository.save(pratileira);
		   }else {
			   for (Pratileira pratileiraType : pratileiras) {
				    if(pratileiraType.getNome_pratileira().equals(pratileira.getNome_pratileira())) {
				    	System.out.println("Nome duplicado, digite um nome válido!");
				    	return new ResponseEntity<>("Nome duplicado, digite um nome válido!", HttpStatus.BAD_REQUEST);
				    }else {
				    	pratileiraRepository.save(pratileira);
				    }
				}
		   }
		   return new ResponseEntity<>(pratileira, HttpStatus.CREATED);
	   }
	
	@DeleteMapping(path= {"/prateleiras/{id}"})
	   public ResponseEntity delete(@PathVariable long id) throws SQLException, ClassNotFoundException {
		pratileiraRepository.deleteById(id);
		return null;
	   }
	
	@PutMapping(value="/prateleiras/{id}")
	   public ResponseEntity update(@PathVariable("id") long id, @RequestBody Pratileira pratileiras) {
		   return pratileiraRepository.findById(id)
				   .map(record -> {
					   record.setNome_pratileira(pratileiras.getNome_pratileira());
					   pratileiraRepository.save(record);
						 return new ResponseEntity<>(pratileiras, HttpStatus.OK);
					   }).orElse(ResponseEntity.notFound().build());	   	
	   }
}
