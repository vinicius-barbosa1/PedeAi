package br.com.ajufood.pedeai.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

/**
 * Entidade que representa um cliente no sistema.
 *
 * Esta classe será mapeada para a tabela {@code cliente} no banco de dados.
 *
 * Exemplos de dados:
 * nome = "Maria Silva"
 * cpf = "12345678901"
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cliente")
public class ClienteModel {

    /**
     * Identificador único do cliente.
     *
     * O valor é gerado automaticamente pelo banco de dados.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * Nome do cliente.
     *
     * Campo obrigatório, com no mínimo 2 e no máximo 128 caracteres.
     */
    @NotBlank(message = "O Nome é obrigatório.")
    @Column(name = "nome", nullable = false, length = 128)
    @Length(min = 2, max = 128, message = "O Nome deverá ter no mínimo 2 caracteres e no máximo 128 caracteres.")
    private String nome;

    /**
     * CPF do cliente.
     *
     * Campo obrigatório, deve conter 11 dígitos, ser válido e único no banco.
     */
    @NotBlank(message = "O CPF é obrigatório.")
    @CPF(message = "CPF inválido!")
    @Length(min = 11, max = 11, message = "O CPF deverá ter obrigatoriamente 11 dígitos.")
    @Column(name = "cpf", nullable = false, length = 11, unique = true)
    private String cpf;

    /**
     * E-mail do cliente.
     *
     * Campo obrigatório, deve possuir formato válido e ser único no banco.
     */
    @NotBlank(message = "O E-mail é obrigatório.")
    @Email(message = "E-mail inválido!")
    @Length(min = 3, max = 256, message = "O E-mail deverá ter no mínimo 3 caracteres e no máximo 256 caracteres.")
    @Column(name = "email", nullable = false, length = 256, unique = true)
    private String email;

    /**
     * Telefone do cliente.
     *
     * Campo obrigatório, deve conter exatamente 11 dígitos.
     */
    @NotBlank(message = "O Telefone é obrigatório.")
    @Column(name = "telefone", nullable = false, length = 11)
    @Length(min = 11, max = 11, message = "O telefone deverá ter obrigatoriamente 11 dígitos.")
    private String telefone;


    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true) //Cria uma lista de endereços para o cliente
    private List<EnderecoModel> enderecos;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoModel> pedidos;
}