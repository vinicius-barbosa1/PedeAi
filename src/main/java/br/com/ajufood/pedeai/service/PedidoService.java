package br.com.ajufood.pedeai.service;

import br.com.ajufood.pedeai.exception.ConstraintException;
import br.com.ajufood.pedeai.exception.DataIntegrityException;
import br.com.ajufood.pedeai.exception.ObjectNotFoundException;
import br.com.ajufood.pedeai.model.PedidoModel;
import br.com.ajufood.pedeai.repositoty.PedidoRepository;
import br.com.ajufood.pedeai.rest.dto.request.PedidoRequestDTO;
import br.com.ajufood.pedeai.rest.dto.response.PedidoResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PedidoResponseDTO obterPorId(int id){
        PedidoModel pedidoModel =  pedidoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Pedido com o ID " + id + " não encontrado."
                ));

        return modelMapper.map(pedidoModel, PedidoResponseDTO.class);
    }

    public List<PedidoResponseDTO> obterTodos(){
        return pedidoRepository.findAll()
                .stream()
                .map(pedido -> modelMapper.map(pedido, PedidoResponseDTO.class))
                .toList();
    }

    public PedidoResponseDTO salvar(PedidoRequestDTO pedidoRequestDTO){

        try{
            PedidoModel pedidoModel = modelMapper.map(pedidoRequestDTO, PedidoModel.class);
            PedidoModel pedidoSalvo =  pedidoRepository.save(pedidoModel);
            return modelMapper.map(pedidoSalvo, PedidoResponseDTO.class);


        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                    "Erro de integridade ao salvar o pedido do cliente " + pedidoRequestDTO.getClienteId() + ".", e
            );
        }
    }

    public PedidoResponseDTO atualizar(int id, PedidoRequestDTO pedidoRequestDTO){
        try {
            PedidoModel pedidoExistente = pedidoRepository.findById(id)
                    .orElseThrow(() -> new ObjectNotFoundException(
                            "Pedido com ID " + id + " não encontrado."
                    ));

            modelMapper.map(pedidoRequestDTO, pedidoExistente);

            pedidoExistente.setId(id);

            PedidoModel pedidoSalvo = pedidoRepository.save(pedidoExistente);
            return modelMapper.map(pedidoSalvo, PedidoResponseDTO.class);

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                    "Erro de integridade ao atualizar o pedido " + id + ".", e
            );
        }
    }

    public void excluir(int id){
        try {
            PedidoModel pedidoExistente = pedidoRepository.findById(id)
                    .orElseThrow(() -> new ObjectNotFoundException(
                            "Pedido com ID " + id + " não encontrado."
                    ));

            // Yoda condition para evitar NullPointerException
            if ("FINALIZADO".equals(pedidoExistente.getStatus())) {
                throw new ConstraintException("Impossível cancelar/excluir um pedido já finalizado.");
            }

            pedidoRepository.deleteById(id);

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                    "Não foi possível excluir o pedido, pois ele possui vínculos com outros registros.", e
            );
        }
    }

}
