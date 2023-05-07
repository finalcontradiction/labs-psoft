package com.ufcg.psoft.mercadofacil.dto;

import java.util.List;

public class CarrinhoDTO {
	
	private List<ItemCarrinhoDTO> itens;
	
	private double total;
	
	private int numItens;
	
	public int getNumItens() {
		return numItens;
	}

	public void setNumItens(int numItens) {
		this.numItens = numItens;
	}

	public CarrinhoDTO() { super(); }

	public List<ItemCarrinhoDTO> getItens() {
		return itens;
	}

	public void setItens(List<ItemCarrinhoDTO> itens) {
		this.itens = itens;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	

}
