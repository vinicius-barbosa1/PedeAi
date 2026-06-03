package br.com.ajufood.pedeai.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "itensPedido")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItensPedidoModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private long id;

  @NotNull(message = "A quantidade é obrigatória.")
  @Min(value = 1, message = "A quantidade mínima deve ser 1.")
  @Column(name = "quantidade", nullable = false)
  private int quantidade;

  @NotNull(message = "O preço unitário é obrigatório.")
  @DecimalMin(value = "0.00", message = "O preço unitário não pode ser negativo.")
  @Column(name = "precoUnitario", nullable = false, precision = 11, scale = 2)
  private BigDecimal precoUnitario;

  @NotNull(message = "O subtotal é obrigatório.")
  @DecimalMin(value = "0.00", message = "O subtotal não pode ser negativo.")
  @Column(name = "subTotal", nullable = false, precision = 11, scale = 2)
  private BigDecimal subTotal;

  @NotNull(message = "O ID do pedido é obrigatório.")
  @JoinColumn(name = "pedido_id", nullable = false)
  private PedidoModel pedidoId;

  @NotNull(message = "O ID do produto é obrigatório.")
  @JoinColumn(name = "produto_id", nullable = false)
  private ProdutoModel produtoId;
}