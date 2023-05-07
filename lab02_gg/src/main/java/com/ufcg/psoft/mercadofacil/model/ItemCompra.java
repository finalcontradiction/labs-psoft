package com.ufcg.psoft.mercadofacil.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class ItemCompra {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Compra compra;
	
	private String prodName;
	
	private String prodCodBarras;
	
	private double precoUnit;
	
	private int qtd;
	
	public ItemCompra() { super(); }
	
	public ItemCompra(String nome, String cod, int qtd, double preco) {
		this.prodName = nome;
		this.prodCodBarras = cod;
		this.qtd = qtd;
		this.precoUnit = preco;
	}
	
	public double subtotal() {
		return this.qtd * this.precoUnit;
	}
	
	public String toString() {
		return "";
	}
	
	public void setCompra(Compra compra) {
		this.compra = compra;
	}
	
}
