package main.facades;

import main.repositories.LoteRepository;
import main.repositories.ProdutoRepository;
import main.services.LoteService;
import main.services.ProdutoService;
import main.exceptions.LoteInvalidoException;
import main.exceptions.LoteNotFoundException;
import main.exceptions.ProdutoInvalidoException;
import main.exceptions.ProdutoNotFoundException;
import main.models.Lote;
import main.models.Produto;

import java.text.ParseException;
import java.util.Collection;

public class Facade {
	
	// Repositorios
	private ProdutoRepository produtoRep;
	private LoteRepository loteRep;
	
	// Servicos
	private ProdutoService produtoService; 
	private LoteService loteService;
	
	
	public Facade() {
		this.produtoRep = new ProdutoRepository();
		this.loteRep = new LoteRepository();
		this.loteService = new LoteService(loteRep, produtoRep);
		this.produtoService = new ProdutoService(this.loteService);
	}
	
	public Collection<Produto> listaProdutos() {
		return this.produtoService.listaProdutos();
	}
	
	public Collection<Lote> listaLotes() {
		return this.loteService.listaLotes();
	}
		
	public String criaProduto(String data) throws ProdutoInvalidoException {
		return this.produtoService.addProduto(data);
	}
	
	public String criaProduto(String nome, String fabr, double preco) throws ProdutoInvalidoException {
		return this.produtoService.addProduto(nome, fabr, preco);
	}
	
	public Produto delProduto(String prodId) throws ProdutoNotFoundException {
		return this.produtoService.delProd(prodId);
	}

	public String criaLote(String data) throws ParseException, LoteInvalidoException, ProdutoInvalidoException, ProdutoNotFoundException {
		
		return this.loteService.addLote(data);
	}
	
	public String criaLote(String prodId, int qtd, String dataValidade) 
			throws ParseException, LoteInvalidoException, ProdutoInvalidoException, ProdutoNotFoundException {
		
		return this.loteService.addLote(prodId, qtd, dataValidade);
	}
	
	public Lote delLote(String id) throws LoteNotFoundException {
		return this.loteService.delLote(id);
	}
	
	public Collection<Produto> buscaProdutos(String nome){
		return this.produtoService.buscaProdutos(nome);
	}
	
	public Collection<Produto> buscaProdutosLoteadosPorNome(String nome){
		return this.produtoService.buscaProdutosLoteadosPorNome(nome);
	}
}