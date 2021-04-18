package br.com.igti.messaging;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import br.com.igti.config.MessagingConfig;
import br.com.igti.dto.HabilitacaoDTO;
import br.com.igti.dto.LanceMessage;
import br.com.igti.dto.LoteDTO;
import br.com.igti.dto.ParticipanteDTO;
import br.com.igti.model.Lance;
import br.com.igti.repository.LanceRepository;
import br.com.igti.service.LeilaoService;
import br.com.igti.service.ParticipanteService;

@Component
public class LanceMessageReceiver {
	private CountDownLatch latch = new CountDownLatch(1);

	@Autowired
	private LanceRepository lanceRepository;

	@Autowired
	private LeilaoService leilaoService;

	@Autowired
	private ParticipanteService participanteService;

	@RabbitListener(queues = MessagingConfig.LANCES_QUEUE, ackMode = "MANUAL")
	public void receiveMessage(LanceMessage lanceMessage, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
		// Carregar o lote (com o leilão)
		LoteDTO lote = leilaoService.findLoteById(lanceMessage.getLoteId());

		// Carregar o Participante
		ParticipanteDTO participante = participanteService.findByCpf(lanceMessage.getCpf());
		// Carregar Habilitação
		HabilitacaoDTO habilitacao = leilaoService.findHabilitacao(lote.getLeilaoId(), lanceMessage.getCpf());
		
		//Validar Lance
		boolean lanceValido = isLanceValido(lanceMessage, lote, habilitacao);

		Lance lance = new Lance();
		lance.setLoteId(lote.getId());
		lance.setParticipanteId(participante.getId());
		lance.setTimestamp(lanceMessage.getTimestamp());
		lance.setValido(lanceValido);
		lance.setValor(lanceMessage.getValor());

		lanceRepository.save(lance);

		System.out.println("Processed <" + lanceMessage + ">");

		// Em caso de erro no processo, a mensagem não deve ser processada.
		ackMessage(channel, tag);

		latch.countDown();
	}

	private boolean isLanceValido(LanceMessage lanceMessage, LoteDTO lote, HabilitacaoDTO habilitacao) {
		// Validar se o lance foi dado no intervalo do leilão
		boolean lanceEmLeilaoAberto = (lanceMessage.getTimestamp().before(lote.getLeilaoFechamento())
				|| lanceMessage.getTimestamp().equals(lote.getLeilaoFechamento()))
				&& lanceMessage.getTimestamp().after(lote.getLeilaoAbertura())
				|| lanceMessage.getTimestamp().equals(lote.getLeilaoAbertura());

		// Validar o valor do lance (deve ser compatível com o lance mínimo do lote)
		boolean lanceMinimoRespeitado = lanceMessage.getValor().compareTo(lote.getPrecoInicial()) >= 0;

		// Validar se participante está habilitado para o leilao
		boolean lanceParticipanteHabilitado = habilitacao != null
				&& habilitacao.getDataHabilitacao().before(lote.getLeilaoAbertura());
		
		return lanceEmLeilaoAberto && lanceMinimoRespeitado && lanceParticipanteHabilitado;
	}

	private boolean ackMessage(Channel channel, long tag) {
		try {
			channel.basicAck(tag, false);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public CountDownLatch getLatch() {
		return latch;
	}
}
