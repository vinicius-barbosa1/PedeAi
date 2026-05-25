package br.com.ajufood.pedeai.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * DTO utilizado para transportar os dados de cliente entre a API e o cliente da aplicação.
 *
 * O DTO evita expor diretamente a entidade do banco de dados nas respostas da API.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponseDTO {
    private int id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
}