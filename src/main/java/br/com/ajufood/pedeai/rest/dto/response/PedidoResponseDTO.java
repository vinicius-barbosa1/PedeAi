package br.com.ajufood.pedeai.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponseDTO {
  private int id;
  private LocalDateTime dataHora;
  private String status;
  private BigDecimal valorTotal;
  private int clienteId;
  private int enderecoEntregaId;
}
