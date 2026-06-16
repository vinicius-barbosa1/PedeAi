package br.com.ajufood.pedeai.rest.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PedidoResumoProjecao {
    // Dados do Pedido
    Integer getId();
    LocalDateTime getDataHora();
    BigDecimal getValorTotal();
    String getStatus();

    // Dados do Endereço
    String getEndereco();
    Integer getNumero();
    String getBairro();
    String getCidade();

    // Dados do Item
    String getNome();
    Integer getQuantidade();
    BigDecimal getPrecoUnitario();
    BigDecimal getSubTotal();
}