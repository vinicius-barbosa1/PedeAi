package br.com.ajufood.pedeai.service;

import br.com.ajufood.pedeai.exception.BusinessRuleException;
import br.com.ajufood.pedeai.exception.DataIntegrityException;
import br.com.ajufood.pedeai.exception.ObjectNotFoundException;
import br.com.ajufood.pedeai.model.ProdutoModel;
import br.com.ajufood.pedeai.repositoty.ProdutoRepository;
import br.com.ajufood.pedeai.rest.dto.request.ProdutoRequestDTO;
import br.com.ajufood.pedeai.rest.dto.response.ProdutoResponseDTO;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public ProdutoResponseDTO obterPorId(int id){
        ProdutoModel produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Produto com ID " + id + " não encontrado."
                ));

        return modelMapper.map(produto, ProdutoResponseDTO.class);
    }

    @Transactional(readOnly = true)
    public ProdutoResponseDTO obterPorNome(String nome){
        ProdutoModel produto = produtoRepository.findByNomeIgnoreCase(nome)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Produto com o nome " + nome + " não encontrado."
                ));

        return modelMapper.map(produto, ProdutoResponseDTO.class);
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> obterTodos(){
        return produtoRepository.findAll()
                .stream()
                .map(produto -> modelMapper.map(produto, ProdutoResponseDTO.class))
                .toList();
    }

    @Transactional(readOnly = true)
    public boolean existsByNome(String nome) {
        return produtoRepository.existsByNomeIgnoreCase(nome);
    }

    @Transactional
    public ProdutoResponseDTO salvar(ProdutoRequestDTO produtoRequestDTO){
        try{
            ProdutoModel produto = modelMapper.map(produtoRequestDTO, ProdutoModel.class);

            if(existsByNome(produto.getNome())){
                throw new BusinessRuleException("Já existe um produto com o nome " + produto.getNome());
            }

            return modelMapper.map(produtoRepository.save(produto), ProdutoResponseDTO.class);

        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Erro de integridade ao salvar o produto " + produtoRequestDTO.getNome() + ".", e);
        }
    }

    @Transactional
    public ProdutoResponseDTO atualizar(int id, ProdutoRequestDTO produtoRequestDTO){

        try{
            ProdutoModel produtoAtualizado = modelMapper.map(produtoRequestDTO, ProdutoModel.class);
            ProdutoModel produtoExistente = produtoRepository.findById(id)
                    .orElseThrow(() -> new ObjectNotFoundException("Produto com o ID " + id + " não encontrado."));

            produtoExistente.setNome(produtoAtualizado.getNome());
            produtoExistente.setDescricao(produtoAtualizado.getDescricao());
            produtoExistente.setPreco(produtoAtualizado.getPreco());
            produtoExistente.setDisponivel(produtoAtualizado.isDisponivel());
            produtoExistente.setCategoriaProdutoId(produtoAtualizado.getCategoriaProdutoId());// o id da categoria é realmente necessário? um produto pode trocar de categoria?

            ProdutoModel produtoSalvo = produtoRepository.save(produtoExistente);
            return modelMapper.map(produtoSalvo, ProdutoResponseDTO.class);

        }catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                    "Erro de integridade ao atualizar o produto " + produtoRequestDTO.getNome() + ".", e
            );
        }
    }

    @Transactional
    public void deletar(int id){
        try{
            obterPorId(id);
            produtoRepository.deleteById(id);

        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Erro de integridade ao excluir o produto.", e);
        }
    }

}
