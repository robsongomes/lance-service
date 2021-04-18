package br.com.igti.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LanceRespostaDTO {

	private String id;
	private String loteId;
	private String participanteId;
	private String valor;
	private boolean valido;
	private String timestamp;
}
