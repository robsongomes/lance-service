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
public class LanceMessage {

	private Long loteId;
	private String cpf;
	private BigDecimal valor;
	private Date timestamp;
}
