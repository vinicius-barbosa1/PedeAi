package br.com.ajufood.pedeai.service;

import br.com.ajufood.pedeai.exception.DataIntegrityException;
import br.com.ajufood.pedeai.exception.ObjectNotFoundException;
import br.com.ajufood.pedeai.model.EnderecoModel;
import br.com.ajufood.pedeai.repositoty.EnderecoRepository;
import br.com.ajufood.pedeai.rest.dto.request.EnderecoRequestDTO;
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
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public EnderecoResponseDTO obterPorId(int id){
        EnderecoModel enderecoModel = enderecoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Endereço com ID " + id + " não encontrado."
                ));

        return modelMapper.map(enderecoModel, EnderecoResponseDTO.class);
    }


    @Transactional(readOnly = true)
    public List<EnderecoResponseDTO> obterTodos(){
        return enderecoRepository.findAll()
                .stream()
                .map(endereco -> modelMapper.map(endereco, EnderecoResponseDTO.class))
                .toList();
    }

    @Transactional
    public EnderecoResponseDTO salvar(EnderecoRequestDTO enderecoRequestDTO){

        try{
            EnderecoModel enderecoModel = modelMapper.map(enderecoRequestDTO, EnderecoModel.class);
            EnderecoModel enderecoSalvo = enderecoRepository.save(enderecoModel);

            return modelMapper.map(enderecoSalvo, EnderecoResponseDTO.class);

        }catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                    "Erro de integridade ao salvar o endereço " + enderecoRequestDTO.getEndereco() + ".", e
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
            obterPorId(id);
            enderecoRepository.deleteById(id);
        }catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                    "Não foi possível excluir o endereço, pois ele possui vínculos com outros registros.", e
            );
        }

    }


}
