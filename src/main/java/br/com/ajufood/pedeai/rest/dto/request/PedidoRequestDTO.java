package br.com.ajufood.pedeai.rest.dto.request;

import br.com.ajufood.pedeai.rest.enums.PedidoStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequestDTO {
  @NotNull(message = "A data e hora do pedido são obrigatórias.")
  @PastOrPresent(message = "A data do pedido não pode ser no futuro.")
  private LocalDateTime dataHora;

  @NotNull(message = "O status do pedido é obrigatório.")
  // @Length(max = 128, message = "O status deve ter no máximo 128 caracteres.")
  private PedidoStatus status;

  @NotNull(message = "O valor total é obrigatório.")
  @DecimalMin(value = "0.00", message = "O valor total não pode ser negativo.")
  private BigDecimal valorTotal;

  @NotNull(message = "O ID do cliente é obrigatório.")
  private int clienteId;

  @NotNull(message = "O ID do endereço de entrega é obrigatório.")
  private int enderecoEntregaId;
}
