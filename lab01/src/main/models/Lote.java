package main.models;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.UUID;

public class Lote {
	
	private String id;
	
	private Produto produto;
	
	private int quantidade; 
	
	private Date dataValidade; 
	
	public Lote(Produto produto, int quantidade, Date dataValidade) {
		
		this.id = UUID.randomUUID().toString();
		this.produto = produto;
		this.quantidade = quantidade;
		this.dataValidade = dataValidade;
	}
	
	public String getId() {
		return id;
	}

	public Produto getProduto() {
		return produto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public Date getDataValidade() {
		return dataValidade;
	}

	public String toString() {
		DecimalFormat mFormat= new DecimalFormat("00");
		Date d = this.dataValidade;
		return "Lote ID: " + getId() + " - Produto: " + getProduto().getNome() + " - " + getQuantidade() + " itens" + " - Validade: "+mFormat.format(d.getDate())+"/"+mFormat.format(d.getMonth()+1)+"/"+(1900+d.getYear());
	}
}
