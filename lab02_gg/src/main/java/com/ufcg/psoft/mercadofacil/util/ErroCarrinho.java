package com.ufcg.psoft.mercadofacil.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroCarrinho {
	
	static final String QUANTIDADE_INDISPONIVEL = "Quantidade solicitada esta indisponivel";
	
	static final String CARRINHO_VAZIO = "O carrinho esta vazio";
	
	static final String ITEM_INEXISTENTE = "Item inexistente";
	
	static final String COMPRA_INVALIDA = "Impossivel realizar compra. Talvez alguns dos itens em seu carrinho tenham sofrido mudanca de disponibilidade.";
	
	static final String QTD_INVALIDA = "Quantidade invalida";
	
	public static ResponseEntity<CustomErrorType> erroQtdIndisp() {		
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroCarrinho.QUANTIDADE_INDISPONIVEL),
				HttpStatus.BAD_REQUEST);
	}
	
	public static ResponseEntity<CustomErrorType> erroCarrVazio(long id) {		
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroCarrinho.CARRINHO_VAZIO),
				HttpStatus.NO_CONTENT);
	}
	
	public static ResponseEntity<CustomErrorType> erroItemInex() {		
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroCarrinho.ITEM_INEXISTENTE),
				HttpStatus.NOT_FOUND);
	}
	
	public static ResponseEntity<CustomErrorType> erroCompraInv() {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroCarrinho.COMPRA_INVALIDA),
				HttpStatus.BAD_REQUEST);
	}
	
	public static ResponseEntity<CustomErrorType> erroQtdInv() {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroCarrinho.QTD_INVALIDA),
				HttpStatus.BAD_REQUEST);
	}

}
