package com.ufcg.psoft.mercadofacil.model.ids;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.ufcg.psoft.mercadofacil.model.Carrinho;
import com.ufcg.psoft.mercadofacil.model.Produto;

@Embeddable
public class ItemCarrinhoId implements Serializable {
	
	@ManyToOne
	@JoinColumn(name = "carrinho_cliente_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Carrinho carrinho;
	
	@JoinColumn(name = "produto_id")
	@OneToOne(orphanRemoval = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Produto produto;
		
	public ItemCarrinhoId() { super(); }
		
	public ItemCarrinhoId(Carrinho carrinho, Produto produto) {
		this.carrinho = carrinho;
		this.produto = produto;
	}
		
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemCarrinhoId other = (ItemCarrinhoId) obj;
		return this.carrinho.getId().equals(other.carrinho.getId()) && this.produto.getId().equals(other.produto.getId());
	}
		
	@Override
	public int hashCode() {
		return Objects.hash(carrinho.getId(), produto.getId());
	}

	public Carrinho getCarrinho() {
		return carrinho;
	}

	public void setCarrinho(Carrinho carrinho) {
		this.carrinho = carrinho;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
		
}