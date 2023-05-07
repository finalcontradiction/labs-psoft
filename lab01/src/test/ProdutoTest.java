package test;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.exceptions.LoteInvalidoException;
import main.exceptions.ProdutoInvalidoException;
import main.exceptions.ProdutoNotFoundException;
import main.facades.Facade;
import main.models.Produto;

class ProdutoTest {

	private Facade mercadoFacade = new Facade(); 
	
	private String jsonP1 = "{\"nome\":\"Leite integral\", \"fabricante\":\"Parmalat\", \"preco\":10.5}";

	/*@BeforeEach*/
	public void createProduto() throws ProdutoInvalidoException {
		mercadoFacade.criaProduto(jsonP1);
	}
	
	/*@Test*/
	public void verifyProdutosIgualUm() {
		assertEquals(1, mercadoFacade.listaProdutos().size());
	}

	@Test
	public void verifyAddProduto() throws ProdutoInvalidoException {
		this.mercadoFacade.criaProduto("Leite desnatado", "Parmalat", 10.99);
		
		System.out.println(this.mercadoFacade.listaProdutos());
	}
	
	@Test
	public void produtoInexistente() throws ProdutoNotFoundException {
		try {
			this.mercadoFacade.delProduto("esteIDprovavelmenteNaoEstaCadastrado");
			fail("");
		}catch(ProdutoNotFoundException e) {
			
		}
	}
	
	public void testSearch() throws ProdutoInvalidoException {
		this.mercadoFacade.criaProduto("Leite desnatado", "Parmalat", 10.99);
		this.mercadoFacade.criaProduto("Feijao Fradinho", "UmaMarca", 7.50);
		
		System.out.println(this.mercadoFacade.buscaProdutos("lEiTe"));
		System.out.println(this.mercadoFacade.buscaProdutos("lEiTe d"));
		System.out.println(this.mercadoFacade.buscaProdutos("desnaTadO"));
		System.out.println(this.mercadoFacade.buscaProdutos("fradinho"));
		System.out.println("----------");
	}
	
	/*@Test*/
	public void testDelProd() throws ProdutoInvalidoException {
		
		String prod = this.mercadoFacade.criaProduto("Leite desnatado", "Parmalat", 10.99);
		String newProd = this.mercadoFacade.criaProduto("Leite integral", "Parmalat", 9.99);
		
		try {
			this.mercadoFacade.criaLote(
					prod,
					10,
					"12/12/2022");
			
			this.mercadoFacade.criaLote(
					newProd,
					10,
					"05/12/2022");
			
			this.mercadoFacade.criaLote(newProd, 40, "10/01/2023");
		}catch(ParseException | LoteInvalidoException | ProdutoNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println(this.mercadoFacade.listaProdutos());
		System.out.println(this.mercadoFacade.listaLotes());
		
		try {
			this.mercadoFacade.delProduto(prod);
		} catch (ProdutoNotFoundException e) {

			e.printStackTrace();
		}
		
		System.out.println(this.mercadoFacade.listaProdutos());
		System.out.println(this.mercadoFacade.listaLotes());
		
	}
	
	@Test
	public void testBuscaLoteados() throws ProdutoInvalidoException {
		
		String prod = this.mercadoFacade.criaProduto("Leite desnatado", "Parmalat", 10.99);
		String newProd = this.mercadoFacade.criaProduto("Leite integral", "Parmalat", 9.99);
		String newerProd = this.mercadoFacade.criaProduto("Acucar Mascavo", "Engenho Engenhoso", 8.99);
		
		
		try {
			this.mercadoFacade.criaLote(
					prod,
					10,
					"12/12/2022");
			
			this.mercadoFacade.criaLote(
					newProd,
					10,
					"05/12/2022");
			
			this.mercadoFacade.criaLote(newProd, 40, "10/01/2023");
		}catch(ParseException | LoteInvalidoException | ProdutoNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println(this.mercadoFacade.buscaProdutosLoteadosPorNome("acucar"));
		System.out.println(this.mercadoFacade.buscaProdutosLoteadosPorNome("Integ"));
		
		
	}

}