package main.services;

import java.util.Collection;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import main.models.Lote;
import main.models.Produto;
import main.repositories.LoteRepository;
import main.repositories.ProdutoRepository;
import main.dto.ProdutoDTO;
import main.exceptions.*;
import main.validators.*;

public class ProdutoService {
	
	private ProdutoRepository prodRep;
	private LoteRepository loteRep;
	private LoteService loteServ;
	private ProdutoValidator prodVal;
	private Gson gson = new Gson();
	
	public ProdutoService(LoteService loteServ) {
		this.loteServ = loteServ;
		this.prodRep = loteServ.getProdutoRep();
		this.loteRep = loteServ.getLoteRep();
		this.prodVal = new ProdutoValidator();
	}
	
	public Collection<Produto> listaProdutos() {
		return this.prodRep.getAll();
	}
	
	public String addProduto(String jsonData) throws ProdutoInvalidoException {
		ProdutoDTO prodDTO= gson.fromJson(jsonData, ProdutoDTO.class);
		Produto produto = new Produto(prodDTO.getNome(), prodDTO.getFabricante(), prodDTO.getPreco());
		
		this.prodVal.validate(produto);
		
		this.prodRep.addProduto(produto);
		
		return produto.getId();
	}
	
	public String addProduto(String nome, String fabr, double preco) throws ProdutoInvalidoException {
		String prodData = "{\"nome\":\""+nome+"\", \"fabricante\":\""+fabr+"\", \"preco\":"+preco+"}";
		return this.addProduto(prodData);
	}
	
	public Produto delProd(String prodId) throws ProdutoNotFoundException {
		
		Produto prodToDel = this.prodRep.getProd(prodId);
		
		if(prodToDel == null) {
			throw new ProdutoNotFoundException("Produto inexistente");
		}else {
			
			this.prodRep.delProd(prodId);
			Collection<Lote> lotesToDel = this.loteServ.getLotesByProdId(prodId);
			this.loteServ.delLotesEmBatch(lotesToDel);
			
			return prodToDel;
		}
		
	}
	
	public Collection<Produto> buscaProdutos(String nome){
		return this.prodRep.getAll().stream()
				.filter(p -> p.getNome().toLowerCase().contains(nome.toLowerCase()))
				.collect(Collectors.<Produto>toList());
	}
	
	public Collection<Produto> buscaProdutosLoteadosPorNome(String nome){
		return this.loteRep.getAll().stream()
				.map(l -> l.getProduto())
				.filter(l -> l.getNome().toLowerCase().contains(nome.toLowerCase()))
				.collect(Collectors.<Produto>toSet());
	}
}