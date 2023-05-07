package com.ufcg.psoft.mercadofacil.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long cpf;
	
	private String nome;

	private Integer idade;

	private String endereco;
	
	@OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    private Carrinho carrinho;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Compra> compras = new ArrayList<Compra>();
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Pagamento> pagamentos = new ArrayList<Pagamento>();

	private Cliente() {}

	public Cliente(Long cpf, String nome, Integer idade, String endereco) {
		this.cpf = cpf;
		this.nome = nome;
		this.idade = idade;
		this.endereco = endereco;
	}

	public Long getId() {
		return id;
	}

	public Long getCpf() {
		return cpf;
	}

	public String getNome() {
		return nome;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public void setCarrinho(Carrinho carrinho) {
		this.carrinho = carrinho;
	}
	
	public Carrinho getCarrinho() {
		return this.carrinho;
	}
	
	public Compra addCompra(Compra compra) {
		if(compra != null) this.compras.add(compra);
		return compra;
	}
	
	public Pagamento addPagamento(Pagamento pag) {
		if(pag != null) this.pagamentos.add(pag);
		return pag;
	}
	
	public void clearPagamentos() {
		this.pagamentos.clear();
	}
	
	public void clearCompras() {
		this.compras.clear();
	}
	
}
