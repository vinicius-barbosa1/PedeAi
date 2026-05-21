package br.com.ajufood.pedeai.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ajufood.pedeai.exception.DataIntegrityException;
import br.com.ajufood.pedeai.exception.ObjectNotFoundException;
import br.com.ajufood.pedeai.model.PagamentoModel;
import br.com.ajufood.pedeai.repositoty.PagamentoRepository;
import br.com.ajufood.pedeai.rest.dto.request.PagamentoRequestDTO;
import br.com.ajufood.pedeai.rest.dto.response.PagamentoResponseDTO;

@Service
public class PagamentoService {
    
    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public PagamentoResponseDTO obterPorId(int id) {
        PagamentoModel pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Pedido com o ID " + id + " não encontrado."
                ));
        return modelMapper.map(pagamento, PagamentoResponseDTO.class);
    }

    @Transactional(readOnly = true)
    public List<PagamentoResponseDTO> obterTodos(){
        return pagamentoRepository.findAll()
                .stream()
                .map(pagamento -> modelMapper.map(pagamento, PagamentoResponseDTO.class))
                .toList();
    }

    @Transactional
    public PagamentoResponseDTO salvar(PagamentoRequestDTO pagamentoRequestDTO) {
        try{
            PagamentoModel pagamentoModel = modelMapper.map(pagamentoRequestDTO, PagamentoModel.class);
            PagamentoModel pagamentoSalvo = pagamentoRepository.save(pagamentoModel);
            return modelMapper.map(pagamentoSalvo, PagamentoResponseDTO.class);
        }catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                    "Erro de integridade ao salvar o pagamento do pedido " + pagamentoRequestDTO.getPedidoId() + ".", e
            );
        }
    }

    @Transactional
    public PagamentoResponseDTO atualizar(int id, PagamentoRequestDTO pagamentoRequestDTO) {

        try {
            PagamentoModel pagamentoExistente = pagamentoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Pagamento com o ID " + id + " não encontrado."
                ));

            modelMapper.map(pagamentoRequestDTO, pagamentoExistente);
            PagamentoModel pagamentoAtualizado = pagamentoRepository.save(pagamentoExistente);

            return modelMapper.map(pagamentoAtualizado, PagamentoResponseDTO.class);

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                    "Erro de integridade ao atualizar o pagamento do pedido " + pagamentoRequestDTO.getPedidoId() + ".", e
            );
        }
    }

    //Realmente necessário excluir um pagamento ou seria melhor apenas marcar como cancelado ou pago?
    @Transactional
    public void excluir(int id) {
        try{
            obterPorId(id);
            pagamentoRepository.deleteById(id);
        }catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                    "Erro de integridade ao excluir o pagamento com ID " + id + ".", e
            );
        }
    }
}
