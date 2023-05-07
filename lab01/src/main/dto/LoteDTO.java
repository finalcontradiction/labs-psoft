package main.dto;

public class LoteDTO {
	
	private String idProduto;
	
	private int quantidade; 
	
	private String dataValidade;
	
	public LoteDTO(String idProduto, int quantidade, String dataValidade) {
		
		this.idProduto = idProduto;
		this.quantidade = quantidade;
		this.dataValidade = dataValidade;
	}
	
	public String getIdProduto() {
		return idProduto;
	}

	public int getQuantidade() {
		return quantidade;
	}
	
	public String getDataValidade() {
		return this.dataValidade;
	}
}
