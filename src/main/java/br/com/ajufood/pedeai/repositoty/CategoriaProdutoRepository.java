package br.com.ajufood.pedeai.repositoty;

import br.com.ajufood.pedeai.model.CategoriaProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProdutoModel, Integer> {

  boolean existsByNome(String nome);

}
