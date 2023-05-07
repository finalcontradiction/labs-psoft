package com.ufcg.psoft.mercadofacil.enums;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public enum FormaDePagamento {
	
	BOLETO(0),
	CARTAO(1),
	PIX(2);
	
	private int code;
	
	private FormaDePagamento(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public static FormaDePagamento valueOf(int code) throws IllegalArgumentException {
		
		return Stream.of(FormaDePagamento.values())
        .filter(c -> c.getCode()==code)
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
		
	}
}
