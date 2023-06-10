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

import com.backend.produtos.produtosestoque.model.Enfermeiro;
import com.backend.produtos.produtosestoque.model.Medicamento;
import com.backend.produtos.produtosestoque.repository.EnfermeiroRepository;
import com.backend.produtos.produtosestoque.repository.PratileiraRepository;
import org.springframework.util.StringUtils;

@RestController
@RequestMapping("/api")
public class EnfermeiroController {
	
	@Autowired
    private EnfermeiroRepository enfermeiroRepository;
	
	@Autowired
    private PratileiraRepository pratileiraRepository;

	private String mensagem_retorno_api;
	
	@GetMapping("/enfermeiros")
    public List<Enfermeiro> list() {
        return enfermeiroRepository.findAll();
    }
	
	@PostMapping("/enfermeiros/cadastrar")
	   public ResponseEntity createEnfermeiros(@Valid @RequestBody Enfermeiro enfermeiro) {
		   List<Enfermeiro> enfermeiros = enfermeiroRepository.findAll();
		   if(enfermeiros.size() == 0) {
			   enfermeiroRepository.save(enfermeiro);
		   }else {
			   for (Enfermeiro enfermeiroType : enfermeiros) {
				    if(enfermeiroType.getEmail().equals(enfermeiro.getEmail())) {
				    	System.out.println("Email duplicado, digite um Email válido!");
				    	return new ResponseEntity<>("Email duplicado, digite um Email válido!", HttpStatus.BAD_REQUEST);
				    }else {
				    	enfermeiroRepository.save(enfermeiro);
				    }
				}
		   }
		   return new ResponseEntity<>(enfermeiro, HttpStatus.CREATED);
	   }
	
	@GetMapping(value="/enfermeiros/alocar-pratileira/id_enfermeiro={id_enfermeiro}&id_pratileira={id_pratileira}")
	   public ResponseEntity alocarPratileiraParaEnfermeiro(@PathVariable("id_enfermeiro") Long id_enfermeiro, @PathVariable("id_pratileira") Long id_pratileira){
		return enfermeiroRepository.findById(id_enfermeiro)
	              .map(record -> {
	            	  pratileiraRepository.findById(id_pratileira).map(pratileira -> {
	            		  if(record.getSetor() != null && pratileira.getSetor().equals(record.getSetor())) {
	            			  record.setPratileiraSobResponsabilidade(pratileira);
	            			  enfermeiroRepository.save(record);
	            			  mensagem_retorno_api = "Cadastro de pratileira para um enfermeiro feito com sucesso!: " +
	            			  "/n {" +
	            			  "" +
	            			  "/n }";
	            			  return new ResponseEntity<>(record, HttpStatus.OK);
	            		  }else {
	            			  mensagem_retorno_api = "Coloque esse enfermeiro em um setor! é necessário atribuir esse enfermeiro á um setor na api: /setor/alocar-setor/id_setor={id_setor}&id_enfermeiro={id_enfermeiro}";
	            			  System.out.println("Coloque esse enfermeiro em um setor");
	            			  return new ResponseEntity<>("Coloque esse enfermeiro em um setor", HttpStatus.BAD_GATEWAY);
	            		  }
	 	            		
	 	            	  });	 	            	  
	            	  		
	 	            	  return new ResponseEntity<>(mensagem_retorno_api, HttpStatus.ALREADY_REPORTED);
	              }).orElse(ResponseEntity.notFound().build());
	   }

	   @DeleteMapping(path= {"/enfermeiro/{id}"})
	   public ResponseEntity delete(@PathVariable long id) throws SQLException, ClassNotFoundException {
		enfermeiroRepository.deleteCopyByTradeId(id);
		enfermeiroRepository.deleteById(id);
		return null;
	   }
	
	@PutMapping(value="/enfermeiro/{id}")
	   public ResponseEntity update(@PathVariable("id") long id, @RequestBody Enfermeiro enfermeiros) {
		   return enfermeiroRepository.findById(id)
				   .map(record -> {
					   record.setNome(enfermeiros.getNome());
					   record.setEmail(enfermeiros.getEmail());
					   record.setTelefone(enfermeiros.getTelefone());
					   record.setEndereco(enfermeiros.getEndereco());
						 enfermeiroRepository.save(record);
						 return new ResponseEntity<>(enfermeiros, HttpStatus.OK);
					   }).orElse(ResponseEntity.notFound().build());	   	
	   }

}
