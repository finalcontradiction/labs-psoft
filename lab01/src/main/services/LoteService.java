package main.services;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import main.dto.LoteDTO;
import main.models.Lote;
import main.models.Produto;
import main.repositories.LoteRepository;
import main.repositories.ProdutoRepository;
import main.exceptions.*;
import main.validators.*;

public class LoteService {

	private LoteRepository loteRep;
	private ProdutoRepository produtoRep;
	private LoteValidator loteVal;
	private Gson gson = new Gson();
	
	public LoteService(LoteRepository loteRep, ProdutoRepository prodRep) {
		this.loteRep = loteRep;
		this.produtoRep = prodRep;
		this.loteVal = new LoteValidator();
	}
	
	public Collection<Lote> listaLotes() {
		return this.loteRep.getAll();
	}
	
	public String addLote(String jsonData) throws ParseException, LoteInvalidoException, ProdutoInvalidoException, ProdutoNotFoundException {
		
		LoteDTO loteDTO = gson.fromJson(jsonData, LoteDTO.class);
		Produto prod = this.produtoRep.getProd(loteDTO.getIdProduto());
		
		if(prod == null) {
			throw new ProdutoNotFoundException("Produto inexistente. Impossivel criar lote");
			
		}else {

			Date data = new SimpleDateFormat("dd/MM/yyyy").parse(loteDTO.getDataValidade());
			
			Lote lote = new Lote(prod, loteDTO.getQuantidade(), data);
			
			this.loteVal.validate(lote);
			
			this.loteRep.addLote(lote);

			return lote.getId();
		}
	}
	
	public String addLote(String prodId, int qtd, String dataValidade)
			throws ParseException, LoteInvalidoException, ProdutoInvalidoException, ProdutoNotFoundException {
		
		String jsonL = "{\"idProduto\":\"" + prodId + "\", \"quantidade\":"+qtd+", \"dataValidade\":\""+dataValidade+"\"}";
		
		return this.addLote(jsonL);
	}
	
	public Collection<Lote> getLotesByProdId(String prodId) {
		return this.loteRep.getAll().stream()
				.filter(l -> l.getProduto().getId().equals(prodId))
				.collect(Collectors.<Lote>toList());
	}
	
	public Lote delLote(String id) throws LoteNotFoundException {
		
		Lote toDel = this.loteRep.getLote(id);
		
		if(toDel == null) throw new LoteNotFoundException("Lote inexistente");
		
		this.loteRep.delLot(id);
		
		return toDel;
	}
	
	public void delLotesEmBatch(Collection<Lote> lotes) {
		for(Lote lote : lotes) {
			this.loteRep.delLot(lote.getId());
		}
	}
	
	public LoteRepository getLoteRep() {
		return this.loteRep;
	}
	
	public ProdutoRepository getProdutoRep() {
		return this.produtoRep;
	}
}