package com.ufcg.psoft.mercadofacil.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.mercadofacil.dto.LoteDTO;
import com.ufcg.psoft.mercadofacil.exception.DataInvalidaException;
import com.ufcg.psoft.mercadofacil.exception.LoteNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.ProdutoNotFoundException;
import com.ufcg.psoft.mercadofacil.service.LoteService;
import com.ufcg.psoft.mercadofacil.service.ProdutoService;
import com.ufcg.psoft.mercadofacil.util.ErroLote;
import com.ufcg.psoft.mercadofacil.util.ErroProduto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class LoteApiController {

	@Autowired
	LoteService loteService;
	
	@Autowired
	ProdutoService produtoService;
	
	@Autowired
	public ModelMapper modelMapper;
	
	@GetMapping(value = "/lotes")
	public ResponseEntity<?> listarLotes() {
		
		List<LoteDTO> lotes = loteService.listarLotes();

		if (lotes.isEmpty()) {
			return ErroLote.erroSemLotesCadastrados();
		}
		
		return new ResponseEntity<List<LoteDTO>>(lotes, HttpStatus.OK);
	}
	
	@PostMapping(value = "/produto/{idProduto}/lote/")
	public ResponseEntity<?> criarLote(@PathVariable("idProduto") long id, @RequestBody LoteCreationDataWrapper data) {
			
		try {
			if(data.num > 0) {
				LoteDTO lote = loteService.criaLote(data.num, id, data.str);
				return new ResponseEntity<LoteDTO>(lote, HttpStatus.CREATED);
			}else {
				return ErroLote.erroNumItensInvalido();
			}
		} catch (ProdutoNotFoundException e) {
			return ErroProduto.erroProdutoNaoEnconrtrado(id);
		} catch (DateTimeParseException e) {
			return ErroLote.erroDataValidadeInvalida();
		} catch(DataInvalidaException e) {
			return ErroLote.erroDataValidadeInvalida();
		}
	}
	
	@GetMapping(value = "/lote/{id}")
	public ResponseEntity<?> consultarLote(@PathVariable("id") Long id){
		try {
			LoteDTO lote = loteService.getLoteById(id);
			return new ResponseEntity<LoteDTO>(lote, HttpStatus.OK);
		}catch(LoteNotFoundException e) {
			return ErroLote.erroLoteNaoCadastrado(id);
		}
	}
	
	@PutMapping(value = "/lote/{id}")
	public ResponseEntity<?> atualizaNumItens(@PathVariable("id") Long id, @RequestBody int numItens){
		try {
			if(numItens > 0) {
				LoteDTO lote = loteService.atualizaNumItens(id, numItens);
				return new ResponseEntity<LoteDTO>(lote, HttpStatus.OK);
			}else {
				return ErroLote.erroNumItensInvalido();
			}
		}catch(LoteNotFoundException e) {
			return ErroLote.erroLoteNaoCadastrado(id);
		}
	}
	
	@DeleteMapping(value = "/lote/{id}")
	public ResponseEntity<?> removerLote(@PathVariable("id") Long id){
		try {
			loteService.removerLote(id);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(LoteNotFoundException e) {
			return ErroLote.erroLoteNaoCadastrado(id);
		}
	}
	
	@GetMapping(value = "/lotes/")
	public ResponseEntity<?> buscarLotesPorProdId(@RequestParam Long prodId) {
		
		List<LoteDTO> lotes = this.loteService.buscarLotePorIdProd(prodId).stream()
				.map(l -> modelMapper.map(l, LoteDTO.class))
				.collect(Collectors.toList());
		
		return new ResponseEntity<List<LoteDTO>>(lotes, HttpStatus.OK);
		
	}
	
	
	private static class LoteCreationDataWrapper {
		
		@JsonProperty("numItens")
		@ApiModelProperty(value = "numItens", example = "5", required = true)
		private int num;
		
		@JsonProperty("dataValidade")
		@ApiModelProperty(value = "dataValidade", example = "dd/MM/yyyy", required = true)
		private String str;
		
		LoteCreationDataWrapper(int num, String str){
			this.num = num;
			this.str = str;
		}
		
	}

}