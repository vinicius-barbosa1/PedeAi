package br.com.ajufood.pedeai.rest.dto.response;

import br.com.ajufood.pedeai.rest.enums.PedidoStatus;

import java.time.LocalDate;

public record PedidoFluxoStatus(
   int idPedido,
   PedidoStatus statusAnterior,
   PedidoStatus statusAtual,
   LocalDate dataHoraAtualizacao
) {}
