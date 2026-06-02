package br.com.ajufood.pedeai.service;

import br.com.ajufood.pedeai.exception.BusinessRuleException;
import br.com.ajufood.pedeai.exception.DataIntegrityException;
import br.com.ajufood.pedeai.exception.ObjectNotFoundException;
import br.com.ajufood.pedeai.model.CategoriaProdutoModel;
import br.com.ajufood.pedeai.model.ProdutoModel;
import br.com.ajufood.pedeai.repositoty.ProdutoRepository;
import br.com.ajufood.pedeai.rest.dto.request.ProdutoRequestDTO;
import br.com.ajufood.pedeai.rest.dto.response.CategoriaProdutoResponseDTO;
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
    private CategoriaProdutoService categoriaProdutoService;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public ProdutoResponseDTO obterPorId(int id){
        ProdutoModel produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Produto com ID " + id + " não encontrado."
                ));

        ProdutoResponseDTO dto = modelMapper.map(produto, ProdutoResponseDTO.class);
        dto.setCategoriaProdutoId(produto.getCategoriaProduto().getId());

        return dto;
    }

    @Transactional(readOnly = true)
    public ProdutoResponseDTO obterPorNome(String nome){
        ProdutoModel produto = produtoRepository.findByNomeIgnoreCase(nome)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Produto com o nome " + nome + " não encontrado."
                ));

        ProdutoResponseDTO dto = modelMapper.map(produto, ProdutoResponseDTO.class);
        dto.setNome(produto.getNome());

        return dto;
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> obterTodos(){
        return produtoRepository.findAll()
                .stream()
                .map(produto -> {
                    ProdutoResponseDTO dto = modelMapper.map(produto, ProdutoResponseDTO.class);
                    dto.setCategoriaProdutoId(produto.getCategoriaProduto().getId());
                    return dto;
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public boolean existsByNome(String nome) {
        return produtoRepository.existsByNomeIgnoreCase(nome);
    }

    @Transactional
    public ProdutoResponseDTO salvar(ProdutoRequestDTO produtoRequestDTO){
        try{
            CategoriaProdutoResponseDTO categoriaProdutoResponseDTO = categoriaProdutoService.obterPorId(produtoRequestDTO.getCategoriaProdutoId());
            CategoriaProdutoModel categoriaProdutoModel = modelMapper.map(categoriaProdutoResponseDTO, CategoriaProdutoModel.class);

            ProdutoModel produto = modelMapper.map(produtoRequestDTO, ProdutoModel.class);

//            if(existsByNome(produto.getNome())){ //Talvez tenha que deletar essa validação por que não faz tanto sentido.
//                throw new BusinessRuleException("Já existe um produto com o nome " + produto.getNome());
//            }
//
            produto.setCategoriaProduto(categoriaProdutoModel);

            ProdutoModel produtoSalvo = produtoRepository.save(produto);

            return modelMapper.map(produtoSalvo, ProdutoResponseDTO.class);

        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Erro de integridade ao salvar o produto " + produtoRequestDTO.getNome() + ".", e);
        }
    }

    @Transactional
    public ProdutoResponseDTO atualizar(int id, ProdutoRequestDTO produtoRequestDTO){

        try{

            ProdutoModel produtoExistente = produtoRepository.findById(id)
                    .orElseThrow(() -> new ObjectNotFoundException("Produto com o ID " + id + " não encontrado."));

            modelMapper.map(produtoRequestDTO, produtoExistente);


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

            ProdutoModel produto = produtoRepository.findById(id)
                            .orElseThrow(() -> new ObjectNotFoundException(
                                    "Produto com ID " + id + " não encontrado."
                            ));

            CategoriaProdutoModel categoriaProdutoModel = produto.getCategoriaProduto();

            if (categoriaProdutoModel != null){
                categoriaProdutoModel.getProdutos().remove(produto);
            }

            produtoRepository.deleteById(id);

        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Erro de integridade ao excluir o produto.", e);
        }
    }

}
