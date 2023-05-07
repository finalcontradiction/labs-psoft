package com.ufcg.psoft.mercadofacil.service;

import java.time.LocalDate;
import java.util.List;

import com.ufcg.psoft.mercadofacil.dto.LoteDTO;
import com.ufcg.psoft.mercadofacil.exception.DataInvalidaException;
import com.ufcg.psoft.mercadofacil.exception.LoteNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.ProdutoNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Lote;

public interface LoteService {
	
	public List<LoteDTO> listarLotes();

	public LoteDTO criaLote(int numItens, Long prodId, String dataValidade) throws ProdutoNotFoundException, DataInvalidaException;
	
	public LoteDTO getLoteById(Long id) throws LoteNotFoundException;
	
	public LoteDTO atualizaNumItens(Long id, int num) throws LoteNotFoundException;
	
	public void removerLote(Long id) throws LoteNotFoundException;
	
	public List<Lote> buscarLotePorIdProd(Long id);
	
	public void consumirLotes(int target, Long prodId);
	
}
