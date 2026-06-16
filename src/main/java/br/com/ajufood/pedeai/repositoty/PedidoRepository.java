package br.com.ajufood.pedeai.repositoty;

import br.com.ajufood.pedeai.model.PedidoModel;
import br.com.ajufood.pedeai.rest.dto.response.PedidoResumoProjecao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, Integer> {
}
