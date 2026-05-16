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
public class EnderecoRequestDTO {
  @NotBlank(message = "O endereço é obrigatório.")
  @Length(min = 3, max = 256, message = "O endereço deverá ter no mínimo 3 caracteres e no máximo 256 caracteres")
  private String endereco;

  private int numero;

  @Length(min = 3, max = 256, message = "O complemento deverá ter no mínimo 3 caracteres e no máximo 256 caracteres")
  private String complemento;

  @NotBlank(message = "O bairro é obrigatório.")
  @Length(min = 2, max = 128, message = "O bairro deverá ter no mínimo 2 caracteres e no máximo 128 caracteres")
  private String bairro;

  @NotBlank(message = "A cidade é obrigatória.")
  @Length(min = 2, max = 128, message = "A cidade deverá ter no mínimo 2 caracteres e no máximo 128 caracteres")
  private String cidade;

  @NotBlank(message = "O estado é obrigatório.")
  @Length(min = 2, max = 2, message = "O estado deverá ter obrigatoriamente 2 caracteres")
  private String estado;

  @Length(min = 8, max = 8, message = "O cep deverá ter obrigatoriamente 8 caracteres")
  private String cep;

  @NotBlank(message = "ClienteID é obrigatório.")
  private int clienteId;
}
