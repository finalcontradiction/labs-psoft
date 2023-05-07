package com.ufcg.psoft.mercadofacil.model;

import java.time.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.ufcg.psoft.mercadofacil.enums.FormaDePagamento;

@Entity
public class Pagamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Compra compra;
	
	private ZonedDateTime timestamp;
	
	private double valor;
	
	private int formaDePagamento;
	
	public Pagamento() { super(); }
	
	public Pagamento(FormaDePagamento formaPag) {
		this.formaDePagamento = formaPag.getCode();
	}
	
	public Long compraId() {
		return this.compra.getId();
	}
	
	public ZonedDateTime getTimestamp() {
		return timestamp;
	}

	public FormaDePagamento getFormaDePagamento() {
		return FormaDePagamento.valueOf(formaDePagamento);
	}

	public void setFormaDePagamento(FormaDePagamento formaDePagamento) {
		if(formaDePagamento != null) this.formaDePagamento = formaDePagamento.getCode();
	}

	public void setTimestamp(ZonedDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	public ZonedDateTime now() {
		return LocalDateTime.now().atZone(ZoneId.systemDefault());
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
	
	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

}
