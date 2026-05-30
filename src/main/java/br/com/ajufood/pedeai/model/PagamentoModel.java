package br.com.ajufood.pedeai.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagamento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @NotNull(message = "O valor pago é obrigatório.")
  @DecimalMin(value = "0.01", message = "O valor pago deve ser maior que zero.")
  @Column(name = "valorPago", nullable = false, precision = 11, scale = 2)
  private BigDecimal valorPago;

  @NotNull(message = "A data e hora do pagamento são obrigatórias.")
  @PastOrPresent(message = "A data do pagamento não pode ser no futuro.")
  @Column(name = "dataHora", nullable = false)
  private LocalDateTime dataHora;

  @NotNull(message = "O ID do pedido é obrigatório.")
  @Column(name = "pedidoID", nullable = false)
  private int pedidoId;

  @ManyToOne(fetch = FetchType.LAZY)
  @NotNull(message = "A forma de pagamento é obrigatório.")
  @JoinColumn(name = "formaPagamento", nullable = false)
  private FormaPagamentoModel formaPagamento;
}