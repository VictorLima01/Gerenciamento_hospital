package com.backend.produtos.produtosestoque.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.backend.produtos.produtosestoque.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	
	  @Modifying
	    @Transactional
	    @Query(value = "DELETE FROM pessoa_listas_produtos where listas_produtos_id = ?1 ; ", nativeQuery = true)
	    void deleteCopyByTradeId(Long id);
	  

@Modifying
	    @Transactional
	    @Query(value = "DELETE FROM produto where id = ?1 ; ", nativeQuery = true)
	    void deleteById(Long id);
	  
	  
}
