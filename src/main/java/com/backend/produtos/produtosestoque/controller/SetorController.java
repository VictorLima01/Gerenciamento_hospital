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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.produtos.produtosestoque.model.Pratileira;
import com.backend.produtos.produtosestoque.model.Setor;
import com.backend.produtos.produtosestoque.repository.EnfermeiroRepository;
import com.backend.produtos.produtosestoque.repository.SetorRepository;

@RestController
@RequestMapping("/api")  
public class SetorController {
	@Autowired
    private SetorRepository setorRepository;
	
	@Autowired
    private EnfermeiroRepository enfermeiroRepository;
	
	@GetMapping("/setor")
    public List<Setor> list() {
        return setorRepository.findAll();
    }
	
	@PostMapping("/setor/cadastrar")
	   public ResponseEntity createSetor(@Valid @RequestBody Setor setor) {
		   List<Setor> setores = setorRepository.findAll();
		   if(setores.size() == 0) {
			   setorRepository.save(setor);
		   }else {
			   for (Setor setorType : setores) {
				    if(setorType.getNome().equals(setor.getNome())) {
				    	System.out.println("Nome duplicado, digite um nome válido!");
				    	return new ResponseEntity<>("Nome duplicado, digite um nome válido!", HttpStatus.BAD_REQUEST);
				    }else {
				    	setorRepository.save(setor);
				    }
				}
		   }
		   return new ResponseEntity<>(setor, HttpStatus.CREATED);
	   }
	
	@GetMapping(value="/setor/alocar-setor/id_setor={id_setor}&id_enfermeiro={id_enfermeiro}")
	   public ResponseEntity alocarPacientesParaMedico(@PathVariable("id_setor") Long id_setor, @PathVariable("id_enfermeiro") Long id_enfermeiro){
		return setorRepository.findById(id_setor)
	              .map(record -> {
	            	  enfermeiroRepository.findById(id_enfermeiro).map(enfermeiro -> {
	            		  	 enfermeiro.setSetor(record.getNome());
	 	            		 record.setListaEnfermeiro(enfermeiro);
	 	            		 return ResponseEntity.ok().build();
	 	            	  });	 	            	  
	            	  setorRepository.save(record);
	 	            	  return new ResponseEntity<>(record, HttpStatus.OK);
	              }).orElse(ResponseEntity.notFound().build());
	   }

	   @DeleteMapping(path= {"/setores/{id}"})
	   public ResponseEntity delete(@PathVariable long id) throws SQLException, ClassNotFoundException {
		setorRepository.deleteCopyByTradeId(id);
		setorRepository.deleteById(id);
		return null;
	   }

}
