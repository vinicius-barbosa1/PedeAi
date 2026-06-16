package br.com.ajufood.pedeai.rest.dto.response;

import java.math.BigDecimal;

public record ItemPedidoResumoDTO(
        String nomeProduto,
        Integer quantidade,
        BigDecimal precoUnitario,
        BigDecimal subtotal
) {
}