package main.validators;

import main.models.Lote;
import main.exceptions.*;

public class LoteValidator {

	public LoteValidator() {
	}
	
	public void validate(Lote lote) throws ProdutoInvalidoException, LoteInvalidoException {
		(new ProdutoValidator()).validate(lote.getProduto());
		if(lote.getQuantidade() <= 0) throw new LoteInvalidoException("Lote invalido");
	}

}
