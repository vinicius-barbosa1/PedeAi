package br.com.ajufood.pedeai.repositoty;

import br.com.ajufood.pedeai.model.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoModel, Integer> {
    Optional<ProdutoModel> findByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);

    // UC 01 - Semana 01 - Fácil
    @Query(value = """
        SELECT * 
        FROM produtos p 
        WHERE p.disponivel = true 
          AND (:categoriaProdutoID IS NULL OR p.categoria_id = :categoriaProdutoID)
    """, nativeQuery = true)
    List<ProdutoModel> ListarProdutosPorDisponibilidade(@Param("categoriaProdutoID") Integer categoriaProdutoID);

}
