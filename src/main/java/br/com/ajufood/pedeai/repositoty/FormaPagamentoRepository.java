package br.com.ajufood.pedeai.repositoty;

import br.com.ajufood.pedeai.model.FormaPagamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamentoModel, Integer> {
}
