package main;

import java.text.ParseException;

import main.facades.Facade;

public class Sistema {
	
	public static void main(String[] args) {
		
		String jsonP1 = "{\"nome\":\"Leite integral\", \"fabricante\":\"Parmalat\", \"preco\":10.5}";
		
		Facade mercadoFacade = new Facade();
		
		// Adicionando produto ao catalogo		
		try {
			String idP1 = mercadoFacade.criaProduto(jsonP1);
			
			String jsonL1 = "{\"idProduto\":\"" + idP1 + "\", \"quantidade\":10, \"dataValidade\":\"28/12/2022\"}";
			
			mercadoFacade.criaLote(jsonL1);
			mercadoFacade.criaLote(idP1, 10, "01/01/2023");
			mercadoFacade.delProduto("00000000");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		// Lista produtos no catalogo
		System.out.println(mercadoFacade.listaProdutos());
		
		// Lista lotes no catalogo
		System.out.println(mercadoFacade.listaLotes());
	}
}
