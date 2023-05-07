package com.ufcg.psoft.mercadofacil.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;

import com.ufcg.psoft.mercadofacil.dto.CarrinhoDTO;
import com.ufcg.psoft.mercadofacil.dto.CompraDTO;
import com.ufcg.psoft.mercadofacil.dto.ItemCarrinhoDTO;
import com.ufcg.psoft.mercadofacil.dto.ItemCompraDTO;
import com.ufcg.psoft.mercadofacil.dto.PagamentoDTO;
import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.model.ItemCarrinho;
import com.ufcg.psoft.mercadofacil.model.ItemCompra;
import com.ufcg.psoft.mercadofacil.model.Pagamento;

public class MyModelMapper {
	
	private ModelMapper modelMapper = new ModelMapper();
	
	public MyModelMapper() {super();}
	
	public ModelMapper getModelMapper() {
		
	modelMapper.getConfiguration()
		.setFieldMatchingEnabled(true)
		.setFieldAccessLevel(AccessLevel.PRIVATE);
	
	modelMapper.typeMap(ItemCarrinho.class, ItemCarrinhoDTO.class).addMapping(ItemCarrinho::subtotal, ItemCarrinhoDTO::setSubtotal);
	modelMapper.typeMap(Carrinho.class, CarrinhoDTO.class).addMapping(Carrinho::total, CarrinhoDTO::setTotal);
	
	modelMapper.typeMap(Carrinho.class, CarrinhoDTO.class).addMapping(Carrinho::getNumItens, CarrinhoDTO::setNumItens);
	
	modelMapper.typeMap(Compra.class, CompraDTO.class).addMapping(Compra::total, CompraDTO::setTotal);
	modelMapper.typeMap(Compra.class, CompraDTO.class).addMapping(Compra::getNumItens, CompraDTO::setNumItens);
	modelMapper.typeMap(ItemCompra.class, ItemCompraDTO.class).addMapping(ItemCompra::subtotal, ItemCompraDTO::setSubtotal);
	
	modelMapper.typeMap(Pagamento.class, PagamentoDTO.class).addMapping(Pagamento::compraId, PagamentoDTO::setCompraId);
	modelMapper.typeMap(Pagamento.class, PagamentoDTO.class).addMapping(Pagamento::getValor, PagamentoDTO::setValor);
	
	return modelMapper;
	
	}

}
