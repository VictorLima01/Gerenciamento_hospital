package com.backend.produtos.produtosestoque.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.backend.produtos.produtosestoque.model.Medicamento;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long>{
    @Modifying
	    @Transactional
	    @Query(value = "DELETE FROM medicamento_listas_pratileiras where medicamento_id_medicamento = ?1 ; ", nativeQuery = true)
	    void deleteCopyByTradeId(Long id);
	  
}
