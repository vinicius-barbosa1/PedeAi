package br.com.ajufood.pedeai.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class FormaPagamentoRequestDTO {

  @Length(min = 3, max = 128, message = "O nome deverá ter no mínimo 3 caracteres e no máximo 128 caracteres")
  @NotBlank(message = "O nome é obrigatório.")
  private String nome;

  @Length(min = 10, max = 256, message = "A descrição deverá ter no mínimo 10 caracteres e no máximo 256 caracteres")
  private String descricao;

}
