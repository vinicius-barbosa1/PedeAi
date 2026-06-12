package br.com.ajufood.pedeai.service;

import java.util.List;

import br.com.ajufood.pedeai.model.FormaPagamentoModel;
import br.com.ajufood.pedeai.model.PedidoModel;
import br.com.ajufood.pedeai.rest.dto.response.FormaPagamentoResponseDTO;
import br.com.ajufood.pedeai.rest.dto.response.PedidoResponseDTO;
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
    private PedidoService pedidoService;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

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
        PagamentoResponseDTO dto = modelMapper.map(pagamento, PagamentoResponseDTO.class);
        dto.setFormaPagamentoId(pagamento.getFormaPagamento().getId());
        dto.setPedidoId(pagamento.getPedido().getId());

        return dto;

    }

    @Transactional(readOnly = true)
    public List<PagamentoResponseDTO> obterTodos(){
        return pagamentoRepository.findAll()
                .stream()
                .map(pagamento -> {
                    PagamentoResponseDTO dto = modelMapper.map(pagamento, PagamentoResponseDTO.class);
                    dto.setFormaPagamentoId(pagamento.getFormaPagamento().getId());
                    dto.setPedidoId(pagamento.getPedido().getId());
                    return dto;
                })
                .toList();
    }

    @Transactional
    public PagamentoResponseDTO salvar(PagamentoRequestDTO pagamentoRequestDTO) {
        try{
            PedidoResponseDTO pedidoResponseDTO = pedidoService.obterPorId(pagamentoRequestDTO.getPedidoId());
            PedidoModel pedidoModel = modelMapper.map(pedidoResponseDTO, PedidoModel.class);

            FormaPagamentoResponseDTO formaPagamentoResponseDTO = formaPagamentoService.obterPorId(pagamentoRequestDTO.getFormaPagamentoId());
            FormaPagamentoModel formaPagamentoModel = modelMapper.map(formaPagamentoResponseDTO, FormaPagamentoModel.class);

            PagamentoModel pagamentoModel = modelMapper.map(pagamentoRequestDTO, PagamentoModel.class);

            pagamentoModel.setPedido(pedidoModel); // adiciona o id manualmente após o mapeamento dos outros atributos.
            pagamentoModel.setFormaPagamento(formaPagamentoModel); // adiciona a forma de pagamento manualmente após o mapeamento dos outros atributos.

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

            PedidoResponseDTO pedidoResponseDTO = pedidoService.obterPorId(pagamentoRequestDTO.getPedidoId());
            PedidoModel pedidoModel = modelMapper.map(pedidoResponseDTO, PedidoModel.class);

            FormaPagamentoResponseDTO formaPagamentoResponseDTO = formaPagamentoService.obterPorId(pagamentoRequestDTO.getFormaPagamentoId());
            FormaPagamentoModel formaPagamentoModel = modelMapper.map(formaPagamentoResponseDTO, FormaPagamentoModel.class);

            modelMapper.map(pagamentoRequestDTO, pagamentoExistente);

            pagamentoExistente.setFormaPagamento(formaPagamentoModel);
            pagamentoExistente.setPedido(pedidoModel);

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
            PagamentoModel pagamentoExistente = pagamentoRepository.findById(id)
                    .orElseThrow(() -> new ObjectNotFoundException(
                            "Pagamento com o ID " + id + " não encontrado."
                    ));

            FormaPagamentoModel formaPagamentoModel = pagamentoExistente.getFormaPagamento(); //pega a forma de pagamento que está no pagamento existente

//            if(formaPagamentoModel != null){ // verifica se é diferente de nulo
//                formaPagamentoModel.getPagamentoModel().remove(pagamentoExistente); // deleta da lista em FormaPagamento
//            }

            pagamentoRepository.deleteById(id); // deleta no banco de dados

        }catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                    "Erro de integridade ao excluir o pagamento com ID " + id + ".", e
            );
        }
    }
}
