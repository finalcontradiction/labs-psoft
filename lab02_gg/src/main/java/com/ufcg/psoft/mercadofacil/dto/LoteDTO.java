package com.ufcg.psoft.mercadofacil.dto;

import java.time.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ufcg.psoft.mercadofacil.model.Produto;

public class LoteDTO {

    private Long id;
    private Produto produto;
    private int numeroDeItens;
    
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate dataValidade;

    public Long getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getNumeroDeItens() {
        return numeroDeItens;
    }
    
    public LocalDate getDataValidade() {
    	return dataValidade;
    }
}
