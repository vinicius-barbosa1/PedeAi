package br.com.ajufood.pedeai.repositoty;

import br.com.ajufood.pedeai.model.EnderecoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoModel, Integer> {
    Optional<EnderecoModel> findByCep(String cep);
    void deleteByCep(String cep);
}
