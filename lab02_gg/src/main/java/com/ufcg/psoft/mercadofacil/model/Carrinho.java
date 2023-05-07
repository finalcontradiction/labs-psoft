package com.ufcg.psoft.mercadofacil.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
public class Carrinho {
	
	@Id
	private Long id;
	
	@OneToOne
	@MapsId
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Cliente cliente;
	
	@OneToMany(mappedBy="id.carrinho", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<ItemCarrinho> itens = new ArrayList<ItemCarrinho>();

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public List<ItemCarrinho> getItens(){
		return this.itens;
	}
	
	public void setItens(List<ItemCarrinho> itens) {
		this.itens = itens;
	}
	
	public void clearItens() {
		this.itens.clear();
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void add(ItemCarrinho item) {
		if(item != null) {
			this.itens.add(item);
		}
	}
	
	public double total() {
		double total = 0;
		
		for(ItemCarrinho item : this.itens) {
			total += item.subtotal();
		}
		
		return total;
	}
	
	public int getNumItens() {
		return this.itens.size();
	}
	
	public boolean remove(ItemCarrinho item) {
		return this.itens.remove(item);
	}
}
