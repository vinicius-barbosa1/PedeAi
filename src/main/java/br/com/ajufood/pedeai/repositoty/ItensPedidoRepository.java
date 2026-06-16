package br.com.ajufood.pedeai.repositoty;

import br.com.ajufood.pedeai.model.ItensPedidoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItensPedidoRepository extends JpaRepository<ItensPedidoModel, Long> {
}
