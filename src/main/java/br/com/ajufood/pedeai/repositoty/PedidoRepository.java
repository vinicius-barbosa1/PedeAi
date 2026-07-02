package br.com.ajufood.pedeai.repositoty;

import br.com.ajufood.pedeai.model.PedidoModel;
import br.com.ajufood.pedeai.rest.dto.projection.RelatorioVendasProjecao;
import br.com.ajufood.pedeai.rest.dto.response.PedidoResumoProjecao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, Integer> {


    @Query(value = """
        SELECT
            c.nome,
            SUM(i.quantidade),
            COUNT(DISTINCT p.id),
            SUM(i.subtotal)
        FROM itens_pedido i
        JOIN produto pr
            ON pr.id = i.produto_id
        JOIN categoria_produto c
            ON c.id = pr.categoria_id
        JOIN pedido p
            ON p.id = i.pedido_id
        WHERE p.status = 'ENTREGUE'
        AND p.data_hora BETWEEN ? AND ?
        GROUP BY c.nome
        ORDER BY SUM(i.sub_total) DESC
""", nativeQuery = true)
    List<RelatorioVendasProjecao> buscarRelatorio(
            LocalDate dataInicio,
            LocalDate dataFim);
}
