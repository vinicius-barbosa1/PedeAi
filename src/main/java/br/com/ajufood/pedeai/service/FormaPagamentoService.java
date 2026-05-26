package br.com.ajufood.pedeai.service;

import java.util.List;

import br.com.ajufood.pedeai.exception.BusinessRuleException;
import br.com.ajufood.pedeai.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ajufood.pedeai.exception.DataIntegrityException;
import br.com.ajufood.pedeai.model.FormaPagamentoModel;
import br.com.ajufood.pedeai.repositoty.FormaPagamentoRepository;
import br.com.ajufood.pedeai.rest.dto.request.FormaPagamentoRequestDTO;
import br.com.ajufood.pedeai.rest.dto.response.FormaPagamentoResponseDTO;

@Service
public class FormaPagamentoService {
    
    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public FormaPagamentoResponseDTO obterPorId(int id) {
        FormaPagamentoModel formaPagamento = formaPagamentoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Forma de pagamento com ID " + id + " não encontrada."));

        return modelMapper.map(formaPagamento, FormaPagamentoResponseDTO.class);
    }

    @Transactional(readOnly = true)
    public FormaPagamentoResponseDTO obterPorNome(String nome) {
        FormaPagamentoModel formaPagamento = formaPagamentoRepository.findByNomeIgnoreCase(nome)
                .orElseThrow(() -> new ObjectNotFoundException("Forma de pagamento com nome " + nome + " não encontrada."));

        return modelMapper.map(formaPagamento, FormaPagamentoResponseDTO.class);
    }

    @Transactional(readOnly = true)
    public List<FormaPagamentoResponseDTO> obterTodos() {
        return formaPagamentoRepository.findAll()
                .stream()
                .map(formaPagamento -> modelMapper.map(formaPagamento, FormaPagamentoResponseDTO.class))
                .toList();
    }

    @Transactional(readOnly = true)
    public boolean existsByNome(String nome) {
        return formaPagamentoRepository.existsByNomeIgnoreCase(nome);
    }

    @Transactional
    public FormaPagamentoResponseDTO salvar(FormaPagamentoRequestDTO formaPagamentoRequestDTO) {
      
        try{
            FormaPagamentoModel formaPagamento = modelMapper.map(formaPagamentoRequestDTO, FormaPagamentoModel.class);

            if(existsByNome(formaPagamento.getNome())) {
                throw new BusinessRuleException("Já existe uma forma de pagamento com esse nome " + formaPagamentoRequestDTO.getNome());
            }

            FormaPagamentoModel formaPagamentoSalva = formaPagamentoRepository.save(formaPagamento);
            return modelMapper.map(formaPagamentoSalva, FormaPagamentoResponseDTO.class);

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                    "Erro de integridade ao salvar a forma de pagamento " + formaPagamentoRequestDTO.getNome() + ".", e
            );
        }
    }

    @Transactional
    public FormaPagamentoResponseDTO atualizar(int id, FormaPagamentoRequestDTO formaPagamentoRequestDTO){
        try {
            FormaPagamentoModel formaPagamentoExistente = formaPagamentoRepository.findById(id)
                    .orElseThrow(() -> new ObjectNotFoundException("Forma de pagamento com ID " + id + " não encontrada."));

            modelMapper.map(formaPagamentoRequestDTO, formaPagamentoExistente);
            FormaPagamentoModel formaPagamentoAtualizada = formaPagamentoRepository.save(formaPagamentoExistente);

            return modelMapper.map(formaPagamentoAtualizada, FormaPagamentoResponseDTO.class);

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                    "Erro de integridade ao atualizar a forma de pagamento com ID " + id + ".", e
            );
        }
    }

    @Transactional
    public void deletar(int id) {
        try{
            obterPorId(id);
            formaPagamentoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                    "Não foi possível excluir a forma de pagamento.", e
            );
        }
    }
}
