package br.com.igti.validation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.com.igti.dto.HabilitacaoDTO;
import br.com.igti.dto.LoteDTO;

public class ParticipanteHabilitadoRule implements ValidationRule<LoteDTO, HabilitacaoDTO>{

	@Override
	public List<String> validate(LoteDTO lote, HabilitacaoDTO habilitacao) {
		boolean valid = habilitacao != null
				&& habilitacao.getDataHabilitacao().before(lote.getLeilaoAbertura());
		return valid ? Collections.emptyList() : Arrays.asList("Participante não foi habilitado antes do início do Leilão");
	}

}
