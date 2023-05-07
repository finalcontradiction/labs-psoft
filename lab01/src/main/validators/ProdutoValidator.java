package main.validators;

import main.models.Produto;
import main.exceptions.*;

public class ProdutoValidator {

	public ProdutoValidator() {
	}
	
	public void validate(Produto prod) throws ProdutoInvalidoException {
		if(prod.getNome().isBlank() || prod.getFabricante().isBlank() || prod.getPreco() <= 0) throw new ProdutoInvalidoException("Produto invalido!");
	}

}
