package br.com.ajufood.pedeai.rest.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ItensPedidoRequestDTO {

  @NotNull(message = "A quantidade é obrigatória.")
  @Min(value = 1, message = "A quantidade mínima deve ser 1.")
  private int quantidade;

  @NotNull(message = "O preço unitário é obrigatório.")
  @DecimalMin(value = "0.00", message = "O preço unitário não pode ser negativo.")
  private BigDecimal precoUnitario;

  @NotNull(message = "O subtotal é obrigatório.")
  @DecimalMin(value = "0.00", message = "O subtotal não pode ser negativo.")
  private BigDecimal subTotal;

  @NotNull(message = "O ID do pedido é obrigatório.")
  private int pedidoId;

  @NotNull(message = "O ID do produto é obrigatório.")
  private int produtoId;

}
