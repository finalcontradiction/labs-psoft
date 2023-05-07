package com.ufcg.psoft.mercadofacil.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.mercadofacil.dto.LoteDTO;
import com.ufcg.psoft.mercadofacil.dto.ProdutoDTO;
import com.ufcg.psoft.mercadofacil.exception.DataInvalidaException;
import com.ufcg.psoft.mercadofacil.exception.LoteNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.ProdutoNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.LoteRepository;

@Service
public class LoteServiceImpl implements LoteService {

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private LoteRepository loteRepository;
	
	@Autowired
	public ModelMapper modelMapper;
	
	private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public List<LoteDTO> listarLotes() {
		
		List<LoteDTO> lotes = loteRepository.findAll()
				.stream()
				.map(lote -> modelMapper.map(lote, LoteDTO.class))
				.collect(Collectors.toList());

		return lotes;
	}

	private void salvarLote(Lote lote) {
		loteRepository.save(lote);		
	}

	public LoteDTO criaLote(int numItens, Long id, String dataValidade) throws ProdutoNotFoundException, DateTimeParseException, DataInvalidaException {
		
		Produto produto = produtoService.getProduto(id);

		LocalDate data = LocalDate.parse(dataValidade, FORMATO_DATA);
		
		Lote lote = new Lote(produto, numItens, data);
		
		salvarLote(lote);
	
		return modelMapper.map(lote, LoteDTO.class);
	}
	
	public Lote getLote(Long id) throws LoteNotFoundException {
		Lote lote = loteRepository.findById(id)
				.orElseThrow(() -> new LoteNotFoundException());
		return lote;
	}
	
	public LoteDTO getLoteById(Long id) throws LoteNotFoundException {
		Lote lote = this.getLote(id);
		return modelMapper.map(lote, LoteDTO.class);
	}
	
	public LoteDTO atualizaNumItens(Long id, int num) throws LoteNotFoundException {
		Lote lote = this.getLote(id);
		
		lote.setNumeroDeItens(num);
		loteRepository.save(lote);
		return modelMapper.map(lote, LoteDTO.class);
		
	}
	
	public void removerLote(Long id) throws LoteNotFoundException {
		Lote lote = getLote(id);
		loteRepository.delete(lote);
	}
	
	public List<Lote> buscarLotePorIdProd(Long id) {
		return this.loteRepository.findLotesByProdId(id);
	}
	
	public void consumirLotes(int target, Long prodId) {
		if(target <= 0) return;
		
		List<Lote> lotes = this.buscarLotePorIdProd(prodId); //Lista ja esta ordenada por data de validade
		int aux = target;
		
		for(Lote l : lotes) {
			if(aux > l.getNumeroDeItens()) {
				aux = aux - l.getNumeroDeItens();
				l.setNumeroDeItens(0);
				this.loteRepository.save(l);
			}else {
				l.setNumeroDeItens(l.getNumeroDeItens() - aux);
				this.loteRepository.save(l);
				break;
			}
		}
		
		//Remove lotes vazios
		this.loteRepository.deleteInBatch(
				lotes.stream()
				.filter(l -> (l.getNumeroDeItens() <= 0))
				.collect(Collectors.toList())
				);
		
	}

}
