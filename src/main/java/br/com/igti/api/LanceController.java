package br.com.igti.api;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.igti.dto.LanceDTO;
import br.com.igti.dto.LanceMessage;
import br.com.igti.dto.LanceRespostaDTO;
import br.com.igti.repository.LanceRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/lances")
@Api(value = "Lances API")
public class LanceController {
	
	@Autowired	
	private RabbitTemplate template;
	
	@Autowired	
	private Binding binding;
	
	@Autowired
	private LanceRepository repository;
	
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/lote/{loteId}")
	@ApiOperation(value = "API para envio de Lances para um Lote de Leilão")
	public ResponseEntity<?> darLance(@RequestBody LanceDTO lance, @PathVariable Long loteId) {
		LanceMessage message = new LanceMessage();
		message.setCpf(lance.getCpf());
		message.setLoteId(loteId);
		message.setValor(new BigDecimal(lance.getValor()));
		message.setTimestamp(new Date());
		template.convertAndSend(binding.getExchange(), binding.getRoutingKey(), message);
		return ResponseEntity.ok().body("Seu lance foi recebido e em breve será processado");
	}
	
	@GetMapping("/lote/{loteId}")
	public List<LanceRespostaDTO> lancesPorLote(@PathVariable Long loteId) {
		return repository.findByLoteId(loteId).stream()
				.map(l -> modelMapper.map(l, LanceRespostaDTO.class))
				.collect(Collectors.toList());
	}
}
