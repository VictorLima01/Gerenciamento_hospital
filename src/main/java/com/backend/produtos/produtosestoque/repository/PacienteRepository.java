package com.backend.produtos.produtosestoque.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.backend.produtos.produtosestoque.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long>{
    @Modifying
	    @Transactional
	    @Query(value = "DELETE FROM paciente_listas_medicamentos where paciente_id_paciente = ?1 ; ", nativeQuery = true)
	    void deleteCopyByTradeId(Long id);
	  
}
