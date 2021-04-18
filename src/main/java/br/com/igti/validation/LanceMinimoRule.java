package br.com.igti.validation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.com.igti.dto.LanceMessage;
import br.com.igti.dto.LoteDTO;

public class LanceMinimoRule implements ValidationRule<LanceMessage, LoteDTO>{

	@Override
	public List<String> validate(LanceMessage lanceMessage, LoteDTO lote) {
		boolean valid = lanceMessage.getValor().compareTo(lote.getPrecoInicial()) >= 0;
		return valid ? Collections.emptyList() : Arrays.asList("Valor do Lance é menor que o lance mínimo permitido para o lote");
	}

}
