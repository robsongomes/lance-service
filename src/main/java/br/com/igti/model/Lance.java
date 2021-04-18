package br.com.igti.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Lance {

	@Id
	@GeneratedValue
	private Long id;
	private Long loteId;
	private Long participanteId;
	private BigDecimal valor;
	private boolean valido;
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
}

