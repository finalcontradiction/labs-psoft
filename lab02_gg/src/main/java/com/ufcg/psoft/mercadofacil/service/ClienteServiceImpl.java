package com.ufcg.psoft.mercadofacil.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.mercadofacil.dto.ClienteDTO;
import com.ufcg.psoft.mercadofacil.dto.CompraDTO;
import com.ufcg.psoft.mercadofacil.dto.PagamentoDTO;
import com.ufcg.psoft.mercadofacil.exception.ClienteAlreadyCreatedException;
import com.ufcg.psoft.mercadofacil.exception.ClienteNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.repository.CarrinhoRepository;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;
import com.ufcg.psoft.mercadofacil.repository.CompraRepository;
import com.ufcg.psoft.mercadofacil.repository.PagamentoRepository;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CarrinhoRepository carrinhoRepository;
	
	@Autowired
	private CompraRepository compraRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	public ModelMapper modelMapper;
	
	public ClienteDTO getClienteById(Long id) throws ClienteNotFoundException {
		Cliente cliente = getClienteId(id);
		return modelMapper.map(cliente, ClienteDTO.class);
	}
	
	public Cliente getClienteId(Long id) throws ClienteNotFoundException {
		return clienteRepository.findById(id)
				.orElseThrow(() -> new ClienteNotFoundException());
	}
	
	public ClienteDTO getClienteByCpf(Long cpf) throws ClienteNotFoundException {
		Cliente cliente = clienteRepository.findByCpf(cpf)
				.orElseThrow(() -> new ClienteNotFoundException());
		return modelMapper.map(cliente, ClienteDTO.class);
	}
		
	public void removerClienteCadastrado(Long id) throws ClienteNotFoundException {
		Cliente cliente = getClienteId(id);
		clienteRepository.delete(cliente);
	}

	public void salvarClienteCadastrado(Cliente cliente) {
		clienteRepository.save(cliente);		
	}

	public List<ClienteDTO> listarClientes() {
		List<ClienteDTO> clientes = clienteRepository.findAll()
				.stream()
				.map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
				.collect(Collectors.toList());
		return clientes;
	}

	public ClienteDTO criaCliente(ClienteDTO clienteDTO) throws ClienteAlreadyCreatedException {
		
		if(isClienteCadastrado(clienteDTO.getCpf())) {
			throw new ClienteAlreadyCreatedException();
		}
			
		Cliente cliente = new Cliente(clienteDTO.getCpf(), clienteDTO.getNome(), 
				clienteDTO.getIdade(), clienteDTO.getEndereco());
		
		clienteRepository.save(cliente);
		
		return modelMapper.map(cliente, ClienteDTO.class);
	}

	public ClienteDTO atualizaCliente(Long id, ClienteDTO clienteDTO) throws ClienteNotFoundException {
		
		Cliente cliente = getClienteId(id);
		
		cliente.setIdade(clienteDTO.getIdade());
		cliente.setEndereco(clienteDTO.getEndereco());

		salvarClienteCadastrado(cliente);
		
		return modelMapper.map(cliente, ClienteDTO.class);
	}
	
	private boolean isClienteCadastrado(Long cpf) {
		try {
			getClienteByCpf(cpf);
			return true;
		} catch (ClienteNotFoundException e) {
			return false;
		}
	}
	
	public boolean isCliente(long id) {
		try {
			this.clienteRepository.findById(id)
			.orElseThrow(() -> new ClienteNotFoundException());
			
			return true;
		}catch(ClienteNotFoundException e) {
			return false;
		}
	}
	
	public List<CompraDTO> listarCompras(long id) throws ClienteNotFoundException {
		if(!isCliente(id)) throw new ClienteNotFoundException();
		
		return this.compraRepository.findByClienteId(id).stream().
				map(comp -> modelMapper.map(comp, CompraDTO.class)).
				collect(Collectors.toList());
	}
	
	public List<PagamentoDTO> listarPagamentos(long id) throws ClienteNotFoundException {
		if(!isCliente(id)) throw new ClienteNotFoundException();
		
		return this.pagamentoRepository.findByClienteId(id).stream().
				map(pag -> modelMapper.map(pag, PagamentoDTO.class)).
				collect(Collectors.toList());
	}
	
}
