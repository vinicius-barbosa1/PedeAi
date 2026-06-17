package br.com.ajufood.pedeai.rest.dto.response;

import java.math.BigDecimal;

public record RelatorioVendasResponse(
        String categoria,
        Long totalItens,
        Long totalPedidos,
        BigDecimal totalFaturado,
        BigDecimal ticketMedio
) {
}
