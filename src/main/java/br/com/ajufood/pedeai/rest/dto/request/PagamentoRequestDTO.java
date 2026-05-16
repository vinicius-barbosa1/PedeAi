package br.com.ajufood.pedeai.rest.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
public class PagamentoRequestDTO {
  @NotNull(message = "O valor pago é obrigatório.")
  @DecimalMin(value = "0.01", message = "O valor pago deve ser maior que zero.")
  private BigDecimal valorPago;

  @NotNull(message = "A data e hora do pagamento são obrigatórias.")
  @PastOrPresent(message = "A data do pagamento não pode ser no futuro.")
  private LocalDateTime dataHora;

  @NotNull(message = "O ID do pedido é obrigatório.")
  private int pedidoId;

  @NotNull(message = "O ID da forma de pagamento é obrigatório.")
  private int formaPagamentoId;
}
