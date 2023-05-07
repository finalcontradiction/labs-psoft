package com.ufcg.psoft.mercadofacil.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
public class Compra {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private LocalDate data;
	
	@OneToMany(mappedBy="compra", cascade=CascadeType.ALL)
	private List<ItemCompra> itens;
	
	@ManyToOne()
	@OnDelete(action = OnDeleteAction.CASCADE)
    private Cliente cliente;
	
	@OneToOne(cascade=CascadeType.ALL)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Pagamento pagamento;

	public Compra() { super(); }
	
	public Compra(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public Pagamento getPagamento() {
		return this.pagamento;
	}
	
	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}
	
	public void setData(LocalDate data) {
		this.data = data;
	}
	
	public List<ItemCompra> getItens() {
		return this.itens;
	}
	
	public void setItens(List<ItemCompra> itens) {
		this.itens = itens;
	}
	
	public double total() {
		double total = 0;
		
		for(ItemCompra item : itens) {
			total += item.subtotal();
		}
		
		return total;
	}
	
	public int getNumItens() {
		return this.itens.size();
	}

}
