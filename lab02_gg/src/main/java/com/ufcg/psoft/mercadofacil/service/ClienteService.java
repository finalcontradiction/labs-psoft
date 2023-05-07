package com.ufcg.psoft.mercadofacil.service;

import java.util.List;

import com.ufcg.psoft.mercadofacil.dto.ClienteDTO;
import com.ufcg.psoft.mercadofacil.dto.CompraDTO;
import com.ufcg.psoft.mercadofacil.dto.PagamentoDTO;
import com.ufcg.psoft.mercadofacil.exception.ClienteAlreadyCreatedException;
import com.ufcg.psoft.mercadofacil.exception.ClienteNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Cliente;

public interface ClienteService {

	public ClienteDTO getClienteById(Long id) throws ClienteNotFoundException;
	
	public Cliente getClienteId(Long id) throws ClienteNotFoundException;
	
	public ClienteDTO getClienteByCpf(Long cpf) throws ClienteNotFoundException;
	
	public void removerClienteCadastrado(Long id) throws ClienteNotFoundException;

	public List<ClienteDTO> listarClientes();
	
	public ClienteDTO criaCliente(ClienteDTO clienteDTO) throws ClienteAlreadyCreatedException;
	
	public ClienteDTO atualizaCliente(Long id, ClienteDTO clienteDTO) throws ClienteNotFoundException;
	
	public boolean isCliente(long id);
	
	public List<CompraDTO> listarCompras(long id) throws ClienteNotFoundException;
	
	public List<PagamentoDTO> listarPagamentos(long id) throws ClienteNotFoundException;
	
	public void salvarClienteCadastrado(Cliente cliente);

}
