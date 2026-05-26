package br.com.ajufood.pedeai.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "endereco")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "endereco", nullable = false, length = 256)
  @NotBlank(message = "O endereço é obrigatório.")
  @Length(min = 3, max = 256, message = "O endereço deverá ter no mínimo 3 caracteres e no máximo 256 caracteres")
  private String endereco;

  @Column(name = "numero", nullable = true)
  private int numero;

  @Column(name = "complemento", nullable = true, length = 265)
  @Length(min = 3, max = 256, message = "O complemento deverá ter no mínimo 3 caracteres e no máximo 256 caracteres")
  private String complemento;

  @Column(name = "bairro", nullable = false, length = 128)
  @NotBlank(message = "O bairro é obrigatório.")
  @Length(min = 2, max = 128, message = "O bairro deverá ter no mínimo 2 caracteres e no máximo 128 caracteres")
  private String bairro;

  @Column(name = "cidade", nullable = false, length = 128)
  @NotBlank(message = "A cidade é obrigatória.")
  @Length(min = 2, max = 128, message = "A cidade deverá ter no mínimo 2 caracteres e no máximo 128 caracteres")
  private String cidade;

  @Column(name = "estado", nullable = false, length = 2)
  @NotBlank(message = "O estado é obrigatório.")
  @Length(min = 2, max = 2, message = "O estado deverá ter obrigatoriamente 2 caracteres")
  private String estado;

  @Column(name = "cep", nullable = true, length = 8)
  @Length(min = 8, max = 8, message = "O cep deverá ter obrigatoriamente 8 caracteres")
  private String cep;

  @Column(name = "clienteID", nullable = false)
  @NotNull(message = "ClienteID é obrigatório.")
  private int clienteId;
}
