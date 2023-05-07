package com.ufcg.psoft.mercadofacil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ufcg.psoft.mercadofacil.model.Lote;

public interface LoteRepository extends JpaRepository<Lote, Long>{
	
	@Query(value="SELECT l FROM Lote l WHERE l.produto.id = :idProd ORDER BY l.dataValidade")
	public List<Lote> findLotesByProdId(Long idProd);
	
}