package com.backend.produtos.produtosestoque.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

import com.backend.produtos.produtosestoque.model.Pessoa;
import com.backend.produtos.produtosestoque.model.Produto;
import com.backend.produtos.produtosestoque.repository.ProdutoRepository;
import java.sql.*;
@RestController
@RequestMapping("/api")
public class ProdutoController {
	private PreparedStatement statement;
    private Connection con;
	@Autowired
    private ProdutoRepository produtoRepository;
	
	@GetMapping("/produtos")
    public List<Produto> list() {
        return produtoRepository.findAll();
    }
	
	@PostMapping("/produtos/cadastrar")
	   public ResponseEntity createProdutos(@Valid @RequestBody Produto produto) {
		   List<Produto> produtos = produtoRepository.findAll();
		   if(produtos.size() == 0) {
			   produtoRepository.save(produto);
		   }else {
			   for (Produto produtoType : produtos) {
				    if(produtoType.getNumeroLote() == produto.getNumeroLote()) {
				    	return new ResponseEntity<>("Número de lote inválido!", HttpStatus.BAD_REQUEST);
				    }else {
				    	produtoRepository.save(produto);
				    }
				}
		   }
		   return new ResponseEntity<>(produto, HttpStatus.CREATED);
	   }
	
	@DeleteMapping(path= {"/produtos/{id}"})
	   public ResponseEntity delete(@PathVariable long id) throws SQLException, ClassNotFoundException {
		produtoRepository.deleteCopyByTradeId(id);
		produtoRepository.deleteById(id);
		return null;
	   }
	
	@PutMapping(value="/produtos/{id}")
	   public ResponseEntity update(@PathVariable("id") long id, @RequestBody Produto produtos) {
		   return produtoRepository.findById(id)
				   .map(record -> {
					   record.setNumeroLote(produtos.getNumeroLote());
					   record.setNome(produtos.getNome());
					   record.setFuncao(produtos.getFuncao());
						 produtoRepository.save(record);
						 return new ResponseEntity<>(produtos, HttpStatus.OK);
					   }).orElse(ResponseEntity.notFound().build());	   	
	   }

}
