package br.com.igti.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HabilitacaoDTO {

	private String id;
	private String leilaoId;
	private String leilaoTitulo;
	private String idParticipante;	
	private Date dataHabilitacao;
}
