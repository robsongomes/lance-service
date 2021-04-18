package br.com.igti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.igti.dto.ParticipanteDTO;

@Service
public class ParticipanteService {
	
	@Value("${participantes.endpoint}")
	private String lotesEndpoint;
	
	@Autowired
	private RestTemplate client;
	
	public ParticipanteDTO findByCpf(String cpf) {
		return client.getForEntity(lotesEndpoint + "/{cpf}", ParticipanteDTO.class, cpf).getBody();
	}
}
