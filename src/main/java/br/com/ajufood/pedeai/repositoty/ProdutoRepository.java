package br.com.ajufood.pedeai.repositoty;

import br.com.ajufood.pedeai.model.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Integer> {
    Optional<ProdutoModel> findByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);

}
