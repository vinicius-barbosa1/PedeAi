package br.com.ajufood.pedeai.model;

import jakarta.persistence.*;
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

@Entity
@Table(name = "pedido")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @NotNull(message = "A data e hora do pedido são obrigatórias.")
  @PastOrPresent(message = "A data do pedido não pode ser no futuro.")
  @Column(name = "dataHora", nullable = false)
  private LocalDateTime dataHora;

  @NotBlank(message = "O status do pedido é obrigatório.")
  @Length(max = 128, message = "O status deve ter no máximo 128 caracteres.")
  @Column(name = "status", nullable = false, length = 128)
  private String status;

  @NotNull(message = "O valor total é obrigatório.")
  @DecimalMin(value = "0.00", message = "O valor total não pode ser negativo.")
  @Column(name = "valorTotal", nullable = false, precision = 11, scale = 2)
  private BigDecimal valorTotal;

  @ManyToOne(fetch = FetchType.LAZY)
  @NotNull(message = "O cliente é obrigatório.")
  @JoinColumn(name = "cliente", nullable = false)
  private ClienteModel cliente;


  @ManyToOne(fetch = FetchType.LAZY)
  @NotNull(message = "O endereço de entrega é obrigatório.")
  @JoinColumn(name = "enderecoEntrega", nullable = false)
  private EnderecoModel endereco;
}