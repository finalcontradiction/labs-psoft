package com.ufcg.psoft.mercadofacil.dto;

public class ItemCompraDTO {
	
	public ItemCompraDTO() { super(); }

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getProdCodBarras() {
		return prodCodBarras;
	}

	public void setProdCodBarras(String prodCodBarras) {
		this.prodCodBarras = prodCodBarras;
	}

	public double getPrecoUnit() {
		return precoUnit;
	}

	public void setPrecoUnit(double precoUnit) {
		this.precoUnit = precoUnit;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}
	
	public double getSubtotal() {
		return this.subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	

	
	private String prodName;
	
	private String prodCodBarras;
	
	private double precoUnit;
	
	private int qtd;
	
	private double subtotal;
	
}
