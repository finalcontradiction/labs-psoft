package com.ufcg.psoft.mercadofacil.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ufcg.psoft.mercadofacil.model.ids.ItemCarrinhoId;

@Entity
public class ItemCarrinho implements Serializable {
	
	@EmbeddedId
	@OnDelete(action = OnDeleteAction.CASCADE)
	private ItemCarrinhoId id;
	
	private int qtd;
	
	public ItemCarrinho() {
		super();
	}
	
	public ItemCarrinho(Produto produto, Carrinho carrinho, int qtd) {
		this.id = new ItemCarrinhoId(carrinho, produto);
		this.qtd = qtd;
	}
	
	public void setQtd(int qtd) {
		this.qtd = qtd;
	}
	
	public int getQtd() {
		return this.qtd;
	}
	
	public Produto getProduto() {
		return this.id.getProduto();
	}
	
	public void setProduto(Produto prod) {
		this.id.setProduto(prod);
	}
	
	public void setCarrinho(Carrinho carrinho) {
		this.id.setCarrinho(carrinho);
	}
	
	public double subtotal() {
		return this.qtd * this.getProduto().getPreco();
	}


}
