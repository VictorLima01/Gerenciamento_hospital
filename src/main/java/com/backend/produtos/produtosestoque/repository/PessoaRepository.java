package com.backend.produtos.produtosestoque.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.produtos.produtosestoque.model.Pessoa;


public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
	
	@Query("select u from PESSOA u where u.email = ?1 and u.password = ?2")
	List<Pessoa> listByLoginCredential(String email,String password);

}
