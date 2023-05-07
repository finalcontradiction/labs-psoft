package com.ufcg.psoft.mercadofacil.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.psoft.mercadofacil.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
	
	public List<Pagamento> findByCompraClienteId(long clienteId);
	
	default List<Pagamento> findByClienteId(long clienteId){
		return findByCompraClienteId(clienteId);
	}
	
}
