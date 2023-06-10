package com.backend.produtos.produtosestoque.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

import com.backend.produtos.produtosestoque.model.Pessoa;
import com.backend.produtos.produtosestoque.model.Produto;
import com.backend.produtos.produtosestoque.repository.PessoaRepository;
import com.backend.produtos.produtosestoque.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping("/api")
public class PessoaController {
	
	@Autowired
    private PessoaRepository pessoaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@GetMapping("/pessoas")
    public List<Pessoa> list() {
        return pessoaRepository.findAll();
    }
	
	@PostMapping("/pessoas/cadastrar")
	   public ResponseEntity createPessoa(@Valid @RequestBody Pessoa pessoa) {
		   List<Pessoa> pessoas = pessoaRepository.findAll(); 
		   if(pessoas.size() == 0) {
			   pessoaRepository.save(pessoa);
		   }else {
			   for (Pessoa pessoaType : pessoas) {
				    if(pessoaType.getEmail().equals(pessoa.getEmail())) {
				    	System.out.println("Email duplicado, digite um email válido!");
				    	return new ResponseEntity<>("Email duplicado, digite um email válido!", HttpStatus.BAD_REQUEST);
				    }else if(pessoaType.getPassword().equals(pessoa.getPassword())){
				    	System.out.println("Senha duplicada, digite uma senha válida!");
				    	return new ResponseEntity<>("Senha duplicada, digite uma senha válida!", HttpStatus.BAD_REQUEST);
				    }else {
				    	pessoaRepository.save(pessoa);
				    }
				}
		   }
		   return new ResponseEntity<>(pessoa, HttpStatus.CREATED);
	   }
	
	 @DeleteMapping(path= {"/pessoas/{id}"})
	   public ResponseEntity delete(@PathVariable long id) {
		   return pessoaRepository.findById(id)
				   .map(record -> {
					   pessoaRepository.deleteById(id);
					  return ResponseEntity.ok().build();
				   }).orElse(ResponseEntity.notFound().build());
	   }
	   
	@GetMapping(value="/pessoas/login/email={email}&password={password}")
	   public ResponseEntity login(@PathVariable("email") String pessoaEmail, @PathVariable("password") String pessoaSenha){
		List<Pessoa> pessoa = pessoaRepository.listByLoginCredential(pessoaEmail, pessoaSenha);
		if(pessoa.size() == 0 ){
			return new ResponseEntity<>("Acesso não liberado!", HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<>(pessoa, HttpStatus.OK);
		}
	     
	   }
	
	@GetMapping(value="/pessoas/alocar-produtos/idProduto={idProduto}&idPessoa={idPessoa}")
	   public ResponseEntity login(@PathVariable("idProduto") Long idProduto, @PathVariable("idPessoa") Long idPessoa){
		return pessoaRepository.findById(idPessoa)
	              .map(record -> {
	            		  produtoRepository.findById(idProduto).map(produto -> {
	            			 produto.setAlocado(true);
	 	            		 record.setListasProdutos(produto);
	 	            		 return ResponseEntity.ok().build();
	 	            	  });	 	            	  
	            		  pessoaRepository.save(record);
	 	            	  return new ResponseEntity<>(record, HttpStatus.OK);
	              }).orElse(ResponseEntity.notFound().build());
	   }
	   
}
