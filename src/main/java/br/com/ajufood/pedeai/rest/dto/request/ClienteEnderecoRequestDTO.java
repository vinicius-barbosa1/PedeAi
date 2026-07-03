package br.com.ajufood.pedeai.rest.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteEnderecoRequestDTO {

    @NotBlank(message = "O Nome é obrigatório.")
    @Length(min = 2, max = 128, message = "O Nome deverá ter no mínimo 2 caracteres e no máximo 128 caracteres.")
    private String nome;

    @NotBlank(message = "O CPF é obrigatório.")
    @CPF(message = "CPF inválido!")
    @Length(min = 11, max = 11, message = "O CPF deverá ter obrigatoriamente 11 dígitos.")
    private String cpf;

    @NotBlank(message = "O E-mail é obrigatório.")
    @Email(message = "E-mail inválido!")
    @Length(min = 3, max = 256, message = "O E-mail deverá ter no mínimo 3 caracteres e no máximo 256 caracteres.")
    private String email;

    @NotBlank(message = "O Telefone é obrigatório.")
    @Length(min = 11, max = 11, message = "O telefone deverá ter obrigatoriamente 11 dígitos.")
    private String telefone;

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

    @NotNull(message = "ClienteID é obrigatório.")
    private int clienteId;
}
