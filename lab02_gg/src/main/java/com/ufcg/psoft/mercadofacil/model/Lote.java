package com.ufcg.psoft.mercadofacil.model;

import java.time.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Lote {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@JoinColumn(name = "produto_id")
	@OneToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
    private Produto produto;
	
    private int numeroDeItens;
    
    @Column(name = "data_validade")
    private LocalDate dataValidade;

    private Lote() { }
    
    public Lote(Produto produto, int numeroDeItens, LocalDate dataValidade) {
        this.produto = produto;
        this.numeroDeItens = numeroDeItens;
        this.dataValidade = dataValidade;
    }

    public Long getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getNumeroDeItens() {
        return numeroDeItens;
    }
    
    public LocalDate getDataValidade() {
    	return this.dataValidade;
    }
    
    public void setDataValidade(LocalDate dataValidade) {
    	this.dataValidade = dataValidade;
    }

    public void setNumeroDeItens(int numeroDeItens) {
        this.numeroDeItens = numeroDeItens;
    }

    @Override
    public String toString() {
        return "Lote{" +
                "id=" + id +
                ", produto=" + produto.getId() +
                ", numeroDeItens=" + numeroDeItens + '\'' +
                '}';
    }
}
