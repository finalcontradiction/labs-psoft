package com.ufcg.psoft.mercadofacil.service;

import java.util.List;

import com.ufcg.psoft.mercadofacil.dto.CarrinhoDTO;
import com.ufcg.psoft.mercadofacil.dto.CompraDTO;
import com.ufcg.psoft.mercadofacil.dto.ItemCarrinhoDTO;
import com.ufcg.psoft.mercadofacil.enums.FormaDePagamento;
import com.ufcg.psoft.mercadofacil.exception.CarrinhoVazioException;
import com.ufcg.psoft.mercadofacil.exception.ClienteNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.CompraInvalidaException;
import com.ufcg.psoft.mercadofacil.exception.ProdutoNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.QuantidadeIndisponivelException;

public interface CarrinhoService {
	
	public void inicializaCarrinho(long clienteId) throws ClienteNotFoundException;
	
	public ItemCarrinhoDTO adicionarItem(long clienteId, long prodId) throws ClienteNotFoundException, ProdutoNotFoundException, QuantidadeIndisponivelException;
	
	public ItemCarrinhoDTO atualizaItem(long clienteId, long prodId, int novaQtd) throws CarrinhoVazioException, ClienteNotFoundException, ItemNotFoundException, QuantidadeIndisponivelException;
	
	public CarrinhoDTO listarItens(long clienteId) throws ClienteNotFoundException, CarrinhoVazioException;
	
	public boolean estaVazio(long clienteId) throws ClienteNotFoundException;
	
	public CompraDTO checkout(long clienteId, FormaDePagamento formaPag) throws ClienteNotFoundException, CarrinhoVazioException, CompraInvalidaException, ProdutoNotFoundException;
	
	public CarrinhoDTO descartarCarrinho(long clienteId) throws ClienteNotFoundException, CarrinhoVazioException;
	
	public ItemCarrinhoDTO removerItem(long clienteId, long prodId) throws ClienteNotFoundException, CarrinhoVazioException, ItemNotFoundException;
	
}
