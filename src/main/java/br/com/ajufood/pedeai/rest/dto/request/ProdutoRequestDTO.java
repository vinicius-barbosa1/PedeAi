package br.com.ajufood.pedeai.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoRequestDTO {

  @Length(min = 1, max = 128, message = "O nome deverá ter no mínimo 1 caracteres e no máximo 128 caracteres")
  @NotBlank(message = "O nome é obrigatório.")
  private String nome;

  @Length(max = 256, message = "A descrição deve ter no máximo 256 caracteres")
  private String descricao;

  @NotNull(message = "O preço é obrigatório.")
  private BigDecimal preco;

  private boolean disponivel;

  @NotNull(message = "A categoriaID é obrigatória.")
  private int categoriaProdutoId;
}
