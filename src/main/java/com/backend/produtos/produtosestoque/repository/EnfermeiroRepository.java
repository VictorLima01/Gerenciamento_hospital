package com.backend.produtos.produtosestoque.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.backend.produtos.produtosestoque.model.Enfermeiro;

public interface EnfermeiroRepository extends JpaRepository<Enfermeiro, Long>{
    @Modifying
	    @Transactional
	    @Query(value = "DELETE FROM enfermeiro_pratileira_sob_responsabilidade where enfermeiro_id_enfermeiro = ?1 ; ", nativeQuery = true)
	    void deleteCopyByTradeId(Long id);
	  

}
