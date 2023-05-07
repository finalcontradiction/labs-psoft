package com.ufcg.psoft.mercadofacil.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.config.Configuration.AccessLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.mercadofacil.dto.CarrinhoDTO;
import com.ufcg.psoft.mercadofacil.dto.CompraDTO;
import com.ufcg.psoft.mercadofacil.dto.ItemCarrinhoDTO;
import com.ufcg.psoft.mercadofacil.dto.ProdutoDTO;
import com.ufcg.psoft.mercadofacil.enums.FormaDePagamento;
import com.ufcg.psoft.mercadofacil.exception.CarrinhoVazioException;
import com.ufcg.psoft.mercadofacil.exception.ClienteNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.CompraInvalidaException;
import com.ufcg.psoft.mercadofacil.exception.ProdutoNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.QuantidadeIndisponivelException;
import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.model.Cliente;
import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.model.ItemCarrinho;
import com.ufcg.psoft.mercadofacil.model.ItemCompra;
import com.ufcg.psoft.mercadofacil.model.Pagamento;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.CarrinhoRepository;
import com.ufcg.psoft.mercadofacil.repository.ClienteRepository;
import com.ufcg.psoft.mercadofacil.repository.CompraRepository;
import com.ufcg.psoft.mercadofacil.repository.ItemCarrinhoRepository;
import com.ufcg.psoft.mercadofacil.repository.ItemCompraRepository;
import com.ufcg.psoft.mercadofacil.repository.PagamentoRepository;
import com.ufcg.psoft.mercadofacil.util.MyModelMapper;

@Service
public class CarrinhoServiceImpl implements CarrinhoService {
	
	@Autowired
	private CarrinhoRepository carrinhoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ItemCarrinhoRepository itemRepository;
	
	@Autowired
	private ProdutoService prodService;
	
	@Autowired
	private LoteService loteService;
	
	@Autowired
	private ItemCompraRepository itemCompraRepository;
	
	@Autowired
	private CompraRepository compraRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	public void inicializaCarrinho(long clienteId) throws ClienteNotFoundException {
		
		Cliente cliente = this.clienteService.getClienteId(clienteId);
		Optional<Carrinho> carrinhoOpt = Optional.ofNullable(cliente.getCarrinho());
		
		if(carrinhoOpt.isEmpty()) {
			
			Carrinho cart = new Carrinho();
			cart.setCliente(cliente);
			cliente.setCarrinho(cart);
			
			this.carrinhoRepository.save(cart);
		}
		
	}
	
	public ItemCarrinhoDTO adicionarItem(long clienteId, long prodId) throws ClienteNotFoundException, ProdutoNotFoundException, QuantidadeIndisponivelException {
		
		Produto prod = this.prodService.getProduto(prodId);
		
		boolean flag = this.getQtd(clienteId, prodId) >= this.prodService.quantidadeDisponivel(prodId);
		
		if(flag) throw new QuantidadeIndisponivelException();
		
		ItemCarrinho item;
		
		Carrinho carrinho = this.carrinhoRepository.findById(clienteId).get(); //Suponho que o carrinho ja tenha sido inicializado.
		
		if(this.getQtd(clienteId, prodId) == 0) {
			
			item = new ItemCarrinho(prod, carrinho, 1);
			
			carrinho.add(item);
			
		}else {
			
			item = this.itemRepository.findById(clienteId, prodId).get();
			
			item.setQtd(item.getQtd() + 1);
			
		}
		
		this.carrinhoRepository.save(carrinho);
		
		return modelMapper.map(item, ItemCarrinhoDTO.class);
		
	}
	
	private int getQtd(long clienteId, long prodId) throws ClienteNotFoundException, ProdutoNotFoundException {
		
		if(!this.clienteService.isCliente(clienteId)) throw new ClienteNotFoundException();
		if(!this.prodService.isProdIdCadastrado(prodId)) throw new ProdutoNotFoundException();
		
		Optional<ItemCarrinho> optItem = this.itemRepository.findById(clienteId, prodId);
		
		if(optItem.isEmpty()) {
			return 0;
			
		}else {
			return optItem.get().getQtd();
		}
	}
	
	public ItemCarrinhoDTO atualizaItem(long clienteId, long prodId, int novaQtd) throws CarrinhoVazioException, ClienteNotFoundException, ItemNotFoundException, QuantidadeIndisponivelException { 
		
		if(!this.clienteService.isCliente(clienteId)) throw new ClienteNotFoundException();
		if(this.estaVazio(clienteId)) throw new CarrinhoVazioException();
		
		Optional<ItemCarrinho> optItem = this.itemRepository.findById(clienteId, prodId);
		if(optItem.isEmpty()) throw new ItemNotFoundException();
		try {
		if(novaQtd > this.prodService.quantidadeDisponivel(prodId)) throw new QuantidadeIndisponivelException();
		}catch(ProdutoNotFoundException e) {}
		
		ItemCarrinho item = optItem.get();
		
		if(item.getQtd() != novaQtd) {
			item.setQtd(novaQtd);
			this.itemRepository.save(item);
		}
		
		return modelMapper.map(item, ItemCarrinhoDTO.class);
	}
	
	public ItemCarrinhoDTO removerItem(long clienteId, long prodId) throws CarrinhoVazioException, ClienteNotFoundException, ItemNotFoundException {
		
		if(!this.clienteService.isCliente(clienteId)) throw new ClienteNotFoundException();
		if(this.estaVazio(clienteId)) throw new CarrinhoVazioException();
		
		ItemCarrinho item = this.itemRepository.findById(clienteId, prodId)
		.orElseThrow(() -> new ItemNotFoundException());
		
		Carrinho cart = this.carrinhoRepository.findById(clienteId).get();
		
		if(cart.getItens().size() == 1) {
			this.descartarCarrinho(clienteId);
		}else {
			cart.remove(item);
			this.carrinhoRepository.save(cart);
		}
		
		return modelMapper.map(item, ItemCarrinhoDTO.class);
		
	}
	
	public CarrinhoDTO listarItens(long clienteId) throws ClienteNotFoundException, CarrinhoVazioException {

		if(!this.clienteService.isCliente(clienteId)) throw new ClienteNotFoundException();
		
		Optional<Carrinho> optCarrinho = Optional.ofNullable(this.clienteService.getClienteId(clienteId).getCarrinho());
		
		if(this.estaVazio(clienteId)) throw new CarrinhoVazioException();
		
		return modelMapper.map(optCarrinho.get(), CarrinhoDTO.class);
		
	}
	

	public boolean estaVazio(long clienteId) throws ClienteNotFoundException {
		
		Optional<Carrinho> optCarrinho = Optional.ofNullable(this.clienteService.getClienteId(clienteId).getCarrinho());
		
		return optCarrinho.isEmpty() || optCarrinho.get().getItens().isEmpty();
	}
	
	public CompraDTO checkout(long clienteId, FormaDePagamento formaPag) throws ClienteNotFoundException, CarrinhoVazioException, CompraInvalidaException, ProdutoNotFoundException {
		
		Cliente cliente = this.clienteService.getClienteId(clienteId);
		if(this.estaVazio(clienteId)) throw new CarrinhoVazioException();
		
		Carrinho carrinho = cliente.getCarrinho();
		
		List<ItemCarrinho> itens = carrinho.getItens();
		
		List<ItemCompra> itensCompra = new ArrayList<ItemCompra>();
		
		Compra compra = new Compra(cliente);
		
		for(ItemCarrinho item : itens) {
			if(item.getQtd() > this.prodService.quantidadeDisponivel(item.getProduto().getId())) throw new CompraInvalidaException();
		}
		
		for(ItemCarrinho item : itens) {
			ItemCompra itemCompra = new ItemCompra(
					item.getProduto().getNome() + " " + item.getProduto().getFabricante(), 
					item.getProduto().getCodigoBarra(), 
					item.getQtd(), 
					item.getProduto().getPreco());
			
			itemCompra.setCompra(compra);
			itensCompra.add(itemCompra);
		}
		
		for(ItemCarrinho item : itens) {
			this.loteService.consumirLotes(item.getQtd(), item.getProduto().getId());
		}
		
		compra.setData(LocalDate.now());
		compra.setItens(itensCompra);
		
		cliente.addCompra(compra);
		cliente.setCarrinho(null);
		
		carrinho.clearItens();
		
		Pagamento pag = new Pagamento(formaPag);
		pag.setCompra(compra);
		pag.setTimestamp(pag.now());
		pag.setValor(compra.total());
		compra.setPagamento(pag);
		
		this.clienteService.salvarClienteCadastrado(cliente);
		this.carrinhoRepository.delete(carrinho);
		
		return modelMapper.map(compra, CompraDTO.class);
	}
	
	public CarrinhoDTO descartarCarrinho(long clienteId) throws ClienteNotFoundException, CarrinhoVazioException {
		
		if(!this.clienteService.isCliente(clienteId)) throw new ClienteNotFoundException();
		if(this.estaVazio(clienteId)) throw new CarrinhoVazioException();
		
		Cliente cliente = this.clienteService.getClienteId(clienteId);
		Carrinho cart = cliente.getCarrinho();
		
		CarrinhoDTO cartDTO = modelMapper.map(cart, CarrinhoDTO.class);
		
		cart.clearItens();
		cliente.setCarrinho(null);
		this.clienteService.salvarClienteCadastrado(cliente);
		this.carrinhoRepository.delete(cart);
		
		return cartDTO;
		
	}

}
