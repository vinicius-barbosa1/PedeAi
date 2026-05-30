package br.com.ajufood.pedeai.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Table(name = "formaPagamento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormaPagamentoModel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "nome", nullable = false, length = 128, unique = true)
  @Length(min = 3, max = 128, message = "O nome deverá ter no mínimo 3 caracteres e no máximo 128 caracteres")
  @NotBlank(message = "O nome é obrigatório.")
  private String nome;

  @Column(name = "descricao", length = 256)
  @Length(min = 10, max = 256, message = "A descrição deverá ter no mínimo 10 caracteres e no máximo 256 caracteres")
  private String descricao;


  @OneToMany(mappedBy = "formaPagamentoId")
  private List<PagamentoModel> pagamentoModel;
}
