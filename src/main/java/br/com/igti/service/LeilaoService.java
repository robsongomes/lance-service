package br.com.igti.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.igti.dto.HabilitacaoDTO;
import br.com.igti.dto.LoteDTO;
import lombok.Data;

@Service
public class LeilaoService {

	@Value("${lotes.endpoint}")
	private String lotesEndpoint;
	
	@Value("${habilitacao.endpoint}")
	private String habilitacaoEndpoint;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private RestTemplate client;
	
	public LoteDTO findLoteById(Long id) {
		LoteResponse response = client.getForEntity(lotesEndpoint + "/{id}", 
				LoteResponse.class, id).getBody();
		return modelMapper.map(response, LoteDTO.class);
	}
	
	public HabilitacaoDTO findHabilitacao(Long idLeilao, String cpf) {
		HabilitacaoResponse response = client.getForEntity(habilitacaoEndpoint + "/{idLeilao}/habilitacao/{cpf}", 
				HabilitacaoResponse.class, idLeilao, cpf).getBody();
		return modelMapper.map(response, HabilitacaoDTO.class);
	}
	
	@Data
	static class LoteResponse {
		private String id;
		private String descricao;
		private String precoInicial;
		private String leilaoId;
		private String leilaoTitulo;
		private String leilaoDescricao;
		private String leilaoAbertura;
		private String leilaoFechamento;
	}
	
	@Data
	static class HabilitacaoResponse {
		private String id;
		private String leilaoId;
		private String leilaoTitulo;
		private String idParticipante;	
		private String dataHabilitacao;
	}
}
