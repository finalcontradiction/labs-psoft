package com.ufcg.psoft.mercadofacil.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.mercadofacil.dto.CarrinhoDTO;
import com.ufcg.psoft.mercadofacil.dto.ClienteDTO;
import com.ufcg.psoft.mercadofacil.dto.CompraDTO;
import com.ufcg.psoft.mercadofacil.dto.ItemCarrinhoDTO;
import com.ufcg.psoft.mercadofacil.enums.FormaDePagamento;
import com.ufcg.psoft.mercadofacil.exception.CarrinhoVazioException;
import com.ufcg.psoft.mercadofacil.exception.ClienteNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.CompraInvalidaException;
import com.ufcg.psoft.mercadofacil.exception.ProdutoNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.QuantidadeIndisponivelException;
import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.service.CarrinhoService;
import com.ufcg.psoft.mercadofacil.service.ItemNotFoundException;
import com.ufcg.psoft.mercadofacil.util.CustomErrorType;
import com.ufcg.psoft.mercadofacil.util.ErroCarrinho;
import com.ufcg.psoft.mercadofacil.util.ErroCliente;
import com.ufcg.psoft.mercadofacil.util.ErroProduto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CarrinhoApiController {
	
	@Autowired
	private CarrinhoService carrinhoService;
	
	@PostMapping(value = "/cliente/{id}/carrinho/item")
	public ResponseEntity<?> adicionarItem(@PathVariable("id") long id, @RequestBody long idProd) {
		try {
			
			this.carrinhoService.inicializaCarrinho(id);
			
			ItemCarrinhoDTO item = this.carrinhoService.adicionarItem(id, idProd);
			
			return new ResponseEntity<ItemCarrinhoDTO>(item, HttpStatus.CREATED);
			
		}catch(ClienteNotFoundException e) {
			return ErroCliente.erroClienteNaoEnconrtrado(id);
		}catch(ProdutoNotFoundException e) {
			return ErroProduto.erroProdutoNaoEnconrtrado(idProd);
		}catch(QuantidadeIndisponivelException e) {
			return ErroCarrinho.erroQtdIndisp();
		}
	}
	
	@PutMapping(value = "/cliente/{id}/carrinho/item/{idProd}")
	public ResponseEntity<?> atualizarItem(@PathVariable("id") long id, @PathVariable("idProd") long idProd, @RequestBody int qtd) {
		if(qtd <= 0) return ErroCarrinho.erroQtdInv();
		
		try {
			ItemCarrinhoDTO item = this.carrinhoService.atualizaItem(id, idProd, qtd);
			return new ResponseEntity<ItemCarrinhoDTO>(item, HttpStatus.OK);
			
		}catch(CarrinhoVazioException e) {
			return ErroCarrinho.erroCarrVazio(id);
		}catch(ClienteNotFoundException e) {
			return ErroCliente.erroClienteNaoEnconrtrado(id);
		}catch(ItemNotFoundException e){
			return ErroCarrinho.erroItemInex();
		}catch(QuantidadeIndisponivelException e) {
			return ErroCarrinho.erroQtdIndisp();
		}
	}
	
	@DeleteMapping(value = "/cliente/{id}/carrinho/item/{idProd}")
	public ResponseEntity<?> removerItem(@PathVariable("id") long id, @PathVariable("idProd") long idProd) {
		try {
			ItemCarrinhoDTO item = this.carrinhoService.removerItem(id, idProd);
			return new ResponseEntity<ItemCarrinhoDTO>(item, HttpStatus.OK);
		}catch(ClienteNotFoundException e) {
			return ErroCliente.erroClienteNaoEnconrtrado(id);
		}catch(CarrinhoVazioException e) {
			return ErroCarrinho.erroCarrVazio(id);
		}catch(ItemNotFoundException e) {
			return ErroCarrinho.erroItemInex();
		}
	}
	
	@GetMapping(value = "/cliente/{id}/carrinho/itens")
	public ResponseEntity<?> listarItens(@PathVariable("id") long id) {
		try {
			CarrinhoDTO lista = this.carrinhoService.listarItens(id);
			return new ResponseEntity<CarrinhoDTO>(lista, HttpStatus.OK);
		}catch(ClienteNotFoundException e) {
			return ErroCliente.erroClienteNaoEnconrtrado(id);
		}catch(CarrinhoVazioException e) {
			return ErroCarrinho.erroCarrVazio(id);
		}
	}
	
	@PostMapping(value = "/cliente/{id}/carrinho/checkout")
	public ResponseEntity<?> checkout(@PathVariable("id") long id, @RequestBody FormaDePagamento forma) {
		try {
			CompraDTO compra = this.carrinhoService.checkout(id, forma);
			return new ResponseEntity<CompraDTO>(compra, HttpStatus.CREATED);
		}catch(ProdutoNotFoundException | CompraInvalidaException e) {
			return ErroCarrinho.erroCompraInv();
		}catch(ClienteNotFoundException e) {
			return ErroCliente.erroClienteNaoEnconrtrado(id);
		}catch(CarrinhoVazioException e) {
			return ErroCarrinho.erroCarrVazio(id);
		}
	}
	
	@DeleteMapping(value = "/cliente/{id}/carrinho")
	public ResponseEntity<?> descartar(@PathVariable("id") long id){
		try {
			CarrinhoDTO cart = this.carrinhoService.descartarCarrinho(id);
			return new ResponseEntity<CarrinhoDTO>(cart, HttpStatus.OK);
		}catch(ClienteNotFoundException e) {
			return ErroCliente.erroClienteNaoEnconrtrado(id);
		}catch(CarrinhoVazioException e) {
			return ErroCarrinho.erroCarrVazio(id);
		}
	}

}
