package com.backend.produtos.produtosestoque.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.backend.produtos.produtosestoque.model.Setor;

public interface SetorRepository extends JpaRepository<Setor, Long>{
     @Modifying
	    @Transactional
	    @Query(value = "DELETE FROM setor_lista_enfermeiro where setor_id_setor = ?1 ; ", nativeQuery = true)
	    void deleteCopyByTradeId(Long id);
	  
	  	
}
