package br.com.ajufood.pedeai.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Entity
@Table(name = "produto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "nome", nullable = false, length = 128)
  @Length(min = 1, max = 128, message = "O nome deverá ter no mínimo 1 caracteres e no máximo 128 caracteres")
  @NotBlank(message = "O nome é obrigatório.")
  private String nome;

  @Column(name = "descricao", length = 256)
  @Length(max = 256, message = "A descrição deve ter no máximo 256 caracteres")
  private String descricao;

  @Column(name = "preco", nullable = false, precision = 11, scale = 2)
  @NotNull(message = "O preço é obrigatório.")
  private BigDecimal preco;

  @Column(name = "disponivel", nullable = false)
  private boolean disponivel;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "categoriaProduto_id", nullable = false)
  @NotNull(message = "A categoria é obrigatória.")
  private CategoriaProdutoModel categoriaProduto;
}
