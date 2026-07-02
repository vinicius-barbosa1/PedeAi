package br.com.ajufood.pedeai.rest.dto.projection;

import java.math.BigDecimal;

public interface RelatorioVendasProjecao {
    String getCategoria();

    Long getTotalItens();

    Long getTotalPedidos();

    BigDecimal getTotalFaturado();
}
