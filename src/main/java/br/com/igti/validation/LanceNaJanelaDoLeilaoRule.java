package br.com.igti.validation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.com.igti.dto.LanceMessage;
import br.com.igti.dto.LoteDTO;

public class LanceNaJanelaDoLeilaoRule implements ValidationRule<LanceMessage, LoteDTO>{

	@Override
	public List<String> validate(LanceMessage lanceMessage, LoteDTO lote) {
		boolean valid = (lanceMessage.getTimestamp().before(lote.getLeilaoFechamento())
				|| lanceMessage.getTimestamp().equals(lote.getLeilaoFechamento()))
				&& lanceMessage.getTimestamp().after(lote.getLeilaoAbertura())
				|| lanceMessage.getTimestamp().equals(lote.getLeilaoAbertura());
		
		return valid ? Collections.emptyList() : Arrays.asList("Lance foi dado fora do intervalo de atividade do Leilão");  
	}

}
