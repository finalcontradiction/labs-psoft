package com.ufcg.psoft.mercadofacil.dto;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ufcg.psoft.mercadofacil.enums.FormaDePagamento;

public class PagamentoDTO {
	
	private Long id;
	
	private Long compraId;
	
	private double valor;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	FormaDePagamento formaDePagamento;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private ZonedDateTime timestamp;
	
	public PagamentoDTO() { super(); }
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCompraId() {
		return compraId;
	}

	public void setCompraId(long compraId) {
		this.compraId = compraId;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public FormaDePagamento getFormaDePagamento() {
		return formaDePagamento;
	}

	public void setFormaDePagamento(FormaDePagamento formaDePagamento) {
		this.formaDePagamento = formaDePagamento;
	}

	public ZonedDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(ZonedDateTime timestamp) {
		this.timestamp = timestamp;
	}


}
