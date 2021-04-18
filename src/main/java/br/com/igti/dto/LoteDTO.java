package br.com.igti.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoteDTO {
	
	private Long id;
	private String descricao;
	private BigDecimal precoInicial;
	private Long leilaoId;
	private String leilaoTitulo;
	private String leilaoDescricao;
	private Date leilaoAbertura;
	private Date leilaoFechamento;
}
