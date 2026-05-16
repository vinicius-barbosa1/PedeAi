package br.com.ajufood.pedeai.repositoty;

import br.com.ajufood.pedeai.model.PedidoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, Integer> {
}
