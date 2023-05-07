package com.ufcg.psoft.mercadofacil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.psoft.mercadofacil.model.Compra;

public interface CompraRepository extends JpaRepository<Compra, Long> {

	public List<Compra> findByClienteId(long clienteId);
	
}
