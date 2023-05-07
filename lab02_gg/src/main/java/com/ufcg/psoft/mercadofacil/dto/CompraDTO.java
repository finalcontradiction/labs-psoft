package com.ufcg.psoft.mercadofacil.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ufcg.psoft.mercadofacil.model.ItemCompra;

public class CompraDTO {
	
	@JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
	private LocalDate data;
	
	private List<ItemCompraDTO> itens;
	
	private double total;
	
	private double numItens;
	
	public double getNumItens() {
		return numItens;
	}

	public void setNumItens(double numItens) {
		this.numItens = numItens;
	}

	private PagamentoDTO pagamento;
	
	public PagamentoDTO getPagamento() {
		return pagamento;
	}

	public void setPagamento(PagamentoDTO pagamento) {
		this.pagamento = pagamento;
	}

	public CompraDTO() { super(); }

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public List<ItemCompraDTO> getItens() {
		return itens;
	}

	public void setItens(List<ItemCompraDTO> itens) {
		this.itens = itens;
	}
	
	public void setTotal(double total) {
		this.total = total;
	}
	
	public double getTotal() {
		return this.total;
	}
	

}
