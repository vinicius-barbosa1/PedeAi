package br.com.ajufood.pedeai.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CategoriaProdutoResponseDTO {

  private int id;
  private String nome;
  private String descricao;
  private List<ProdutoResponseDTO> produtos;

}
