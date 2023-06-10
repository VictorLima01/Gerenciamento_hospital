package com.backend.produtos.produtosestoque.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.backend.produtos.produtosestoque.model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long>{
     @Modifying
	    @Transactional
	    @Query(value = " DELETE FROM medico_listas_pacientes where medico_id_medico = ?1 ; ", nativeQuery = true)
	    void deleteCopyByTradeId(Long id);
     
     
}
