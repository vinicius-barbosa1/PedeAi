package br.com.ajufood.pedeai.service;

import br.com.ajufood.pedeai.exception.DataIntegrityException;
import br.com.ajufood.pedeai.exception.ObjectNotFoundException;
import br.com.ajufood.pedeai.model.ClienteModel;
import br.com.ajufood.pedeai.model.EnderecoModel;
import br.com.ajufood.pedeai.repositoty.EnderecoRepository;
import br.com.ajufood.pedeai.rest.dto.request.EnderecoRequestDTO;
import br.com.ajufood.pedeai.rest.dto.response.ClienteResponseDTO;
import br.com.ajufood.pedeai.rest.dto.response.EnderecoResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EnderecoService {

    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public EnderecoResponseDTO obterPorId(int id){
        EnderecoModel enderecoModel = enderecoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Endereço com ID " + id + " não encontrado."
                ));

        EnderecoResponseDTO dto = modelMapper.map(enderecoModel, EnderecoResponseDTO.class);
        dto.setClienteId(enderecoModel.getCliente().getId()); // Adiciona o id do cliente diretamente.
        return dto;
    }


    @Transactional(readOnly = true)
    public List<EnderecoResponseDTO> obterTodos(){
        return enderecoRepository.findAll()
                .stream()
                .map(endereco -> {
                    EnderecoResponseDTO dto = modelMapper.map(endereco, EnderecoResponseDTO.class);
                    dto.setClienteId(endereco.getCliente().getId());
                    return dto;
                })
                .toList();
    }

    @Transactional
    public EnderecoResponseDTO salvar(EnderecoRequestDTO enderecoRequestDTO){

        try{
            ClienteResponseDTO clienteResponseDTO = clienteService.obterPorId(enderecoRequestDTO.getClienteId());
            ClienteModel clienteModel = modelMapper.map(clienteResponseDTO, ClienteModel.class);


            EnderecoModel enderecoModel = modelMapper.map(enderecoRequestDTO, EnderecoModel.class);
            enderecoModel.setCliente(clienteModel);

            EnderecoModel enderecoSalvo = enderecoRepository.save(enderecoModel);

           return modelMapper.map(enderecoSalvo, EnderecoResponseDTO.class);
//            response.setClienteId(enderecoSalvo.getCliente().getId());
//
//
//            return response;

        }catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                    "Erro de integridade ao salvar o endereço " + enderecoRequestDTO.getClienteId() + ".", e
            );
        }

    }

    @Transactional
    public EnderecoResponseDTO atualizar(int id, EnderecoRequestDTO enderecoRequestDTO){

        try{
            EnderecoModel enderecoExistente = enderecoRepository.findById(id)
                    .orElseThrow(() -> new ObjectNotFoundException(
                            "Endereço com o ID " + id + " não encontrado."
                    ));

            modelMapper.map(enderecoRequestDTO, enderecoExistente);
            EnderecoModel enderecoSalvo = enderecoRepository.save(enderecoExistente);
            return modelMapper.map(enderecoSalvo, EnderecoResponseDTO.class);
        }catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                    "Erro de integridade ao atualizar o endereço " + id + ".", e
            );
        }
    }



    @Transactional
    public void deletar(int id){
        try{

            EnderecoModel endereco = enderecoRepository.findById(id)
                    .orElseThrow(() -> new ObjectNotFoundException(
                            "Endereço com ID " + id + " não encontrado."
                    ));

            ClienteModel cliente = endereco.getCliente();

            if (cliente != null) {
                cliente.getEnderecos().remove(endereco);
            }

            enderecoRepository.delete(endereco);

        }catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                    "Não foi possível excluir o endereço, pois ele possui vínculos com outros registros.", e
            );
        }

    }


}
