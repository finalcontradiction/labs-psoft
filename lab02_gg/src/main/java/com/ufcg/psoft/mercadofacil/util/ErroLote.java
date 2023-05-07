package com.ufcg.psoft.mercadofacil.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErroLote {

	static final String SEM_LOTES_CADASTRADOS = "Não há lotes cadastrados";
	
	static final String LOTE_NAO_CADASTRADO = "O lote com id %s nao esta cadastrado";
	
	static final String DATA_LOTE_INVALIDA = "Data de validade invalida";
	
	static final String NUM_ITENS_INVALIDO = "Numero invalido de itens";
	
	public static ResponseEntity<CustomErrorType> erroSemLotesCadastrados() {		
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroLote.SEM_LOTES_CADASTRADOS),
				HttpStatus.NO_CONTENT);
	}
	
	public static ResponseEntity<CustomErrorType> erroLoteNaoCadastrado(long id){
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(String.format(ErroLote.LOTE_NAO_CADASTRADO, id)), HttpStatus.NOT_FOUND);
	}
	
	public static ResponseEntity<CustomErrorType> erroDataValidadeInvalida() {
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroLote.DATA_LOTE_INVALIDA),
				HttpStatus.BAD_REQUEST);
	}
	
	public static ResponseEntity<CustomErrorType> erroNumItensInvalido(){
		return new ResponseEntity<CustomErrorType>(new CustomErrorType(ErroLote.NUM_ITENS_INVALIDO),
				HttpStatus.BAD_REQUEST);
	}
}

