package br.com.ajufood.pedeai.service;

import br.com.ajufood.pedeai.exception.ConstraintException;
import br.com.ajufood.pedeai.exception.DataIntegrityException;
import br.com.ajufood.pedeai.exception.ObjectNotFoundException;
import br.com.ajufood.pedeai.model.ClienteModel;
import br.com.ajufood.pedeai.model.EnderecoModel;
import br.com.ajufood.pedeai.model.PedidoModel;
import br.com.ajufood.pedeai.repositoty.PedidoRepository;
import br.com.ajufood.pedeai.rest.dto.request.PedidoRequestDTO;
import br.com.ajufood.pedeai.rest.dto.response.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public PedidoResponseDTO obterPorId(int id){
        PedidoModel pedidoModel =  pedidoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Pedido com o ID " + id + " não encontrado."
                ));

        PedidoResponseDTO dto = modelMapper.map(pedidoModel, PedidoResponseDTO.class);
        dto.setClienteId(pedidoModel.getCliente().getId()); // Adiciona o id do cliente diretamente.
        dto.setEnderecoEntregaId(pedidoModel.getEndereco().getId()); // Adiciona o id do endereço diretamente.

        return dto;
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> obterTodos(){
        return pedidoRepository.findAll()
                .stream()
                .map(pedido -> {
                    PedidoResponseDTO dto = modelMapper.map(pedido, PedidoResponseDTO.class);
                    dto.setClienteId(pedido.getCliente().getId());
                    dto.setEnderecoEntregaId(pedido.getEndereco().getId());
                    return dto;
                })
                .toList();
    }

    @Transactional
    public PedidoResponseDTO salvar(PedidoRequestDTO pedidoRequestDTO){

        try{
            ClienteResponseDTO clienteResponseDTO = clienteService.obterPorId(pedidoRequestDTO.getClienteId());
            ClienteModel cliente = modelMapper.map(clienteResponseDTO, ClienteModel.class);

            EnderecoResponseDTO enderecoResponseDTO = enderecoService.obterPorId(pedidoRequestDTO.getEnderecoEntregaId());
            EnderecoModel endereco = modelMapper.map(enderecoResponseDTO, EnderecoModel.class);


            PedidoModel pedidoModel = modelMapper.map(pedidoRequestDTO, PedidoModel.class);
            pedidoModel.setCliente(cliente); //Adiciona o Cliente diretamente via setter para não dar erro.
            pedidoModel.setEndereco(endereco); //Adiciona o Endereço diretamente via setter para não dar erro.

            List<Integer> enderecosValidos = cliente.getEnderecos()
                                .stream()
                                .map(EnderecoModel::getId)
                                .toList();

            if(!enderecosValidos.contains(pedidoRequestDTO.getEnderecoEntregaId())){
                throw new ObjectNotFoundException("O endereço de entrega não pertence ao cliente.");
            }

            PedidoModel pedidoSalvo =  pedidoRepository.save(pedidoModel);

            PedidoResponseDTO response = modelMapper.map(pedidoSalvo, PedidoResponseDTO.class);
            response.setClienteId(pedidoSalvo.getCliente().getId()); // setta para retornar o id do cliente no response.
            response.setEnderecoEntregaId(pedidoSalvo.getEndereco().getId()); // setta para retornar o id do endereço no response.

            return response;

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                    "Erro de integridade ao salvar o pedido do cliente " + pedidoRequestDTO.getClienteId() + ".", e
            );
        }
    }

    @Transactional
    public PedidoResponseDTO atualizar(int id, PedidoRequestDTO pedidoRequestDTO){
        try {
            PedidoModel pedidoExistente = pedidoRepository.findById(id)
                    .orElseThrow(() -> new ObjectNotFoundException(
                            "Pedido com ID " + id + " não encontrado."
                    ));

            modelMapper.map(pedidoRequestDTO, pedidoExistente);

            pedidoExistente.setId(id);


            PedidoModel pedidoSalvo = pedidoRepository.save(pedidoExistente);

            PedidoResponseDTO response = modelMapper.map(pedidoSalvo, PedidoResponseDTO.class);
            response.setClienteId(pedidoSalvo.getCliente().getId()); // setta para retornar o id do cliente no response.
            response.setEnderecoEntregaId(pedidoSalvo.getEndereco().getId()); // setta para retornar o id do endereço no response.

            return response;

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                    "Erro de integridade ao atualizar o pedido " + id + ".", e
            );
        }
    }

    @Transactional
    public void excluir(int id) {
        try {
            PedidoModel pedidoExistente = pedidoRepository.findById(id)
                    .orElseThrow(() -> new ObjectNotFoundException(
                            "Pedido com ID " + id + " não encontrado."
                    ));

            ClienteModel cliente = pedidoExistente.getCliente();

            if (cliente != null) {
                cliente.getPedidos().remove(pedidoExistente);
                System.out.println("Pedido cancelado com sucesso.");
            }

            // Condição para evitar NullPointerException
            if ("FINALIZADO".equalsIgnoreCase(pedidoExistente.getStatus())) {
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
