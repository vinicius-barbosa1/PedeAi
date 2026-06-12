package br.com.ajufood.pedeai.service;

import java.math.BigDecimal;
import java.util.List;

import br.com.ajufood.pedeai.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ajufood.pedeai.exception.DataIntegrityException;
import br.com.ajufood.pedeai.model.ItensPedidoModel;
import br.com.ajufood.pedeai.model.PedidoModel;
import br.com.ajufood.pedeai.model.ProdutoModel;
import br.com.ajufood.pedeai.repositoty.ItensPedidoRepository;
import br.com.ajufood.pedeai.rest.dto.request.ItensPedidoRequestDTO;
import br.com.ajufood.pedeai.rest.dto.response.ItensPedidoResponseDTO;
import br.com.ajufood.pedeai.rest.dto.response.PedidoResponseDTO;
import br.com.ajufood.pedeai.rest.dto.response.ProdutoResponseDTO;


@Service
public class ItensPedidoService {

    @Autowired
    private ProdutoService produtoService; // Para validar o produto e obter o preço unitário, se necessário.

    @Autowired
    private PedidoService pedidoService; // Para validar o pedido ao qual o item pertence.
    
    @Autowired
    private ItensPedidoRepository itensPedidoRepository;

    // @Autowired Talvez seja necessário acessar algum método de Produto.
    // private ProdutoService produtoService;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public ItensPedidoResponseDTO obterPorId(long id) {

        ItensPedidoModel itensPedido = itensPedidoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Item do pedido com ID " + id + " não encontrado."));
                
        ItensPedidoResponseDTO dto = modelMapper.map(itensPedido, ItensPedidoResponseDTO.class);
        dto.setPedidoId(itensPedido.getPedido().getId()); // Adiciona o id do pedido diretamente.
        dto.setProdutoId(itensPedido.getProduto().getId()); // Adiciona o id do produto diretamente.
        return dto;
    }

    @Transactional(readOnly = true)
    public List<ItensPedidoResponseDTO> obterTodos(){
        return itensPedidoRepository.findAll()
                .stream()
                .map(itensPedido -> {
                    ItensPedidoResponseDTO dto = modelMapper.map(itensPedido, ItensPedidoResponseDTO.class);
                    dto.setPedidoId(itensPedido.getPedido().getId());
                    dto.setProdutoId(itensPedido.getProduto().getId());
                    return dto;
                })
                .toList();
    }

    @Transactional
    public ItensPedidoResponseDTO salvar(ItensPedidoRequestDTO itensPedidoDTO) {

        try{
            PedidoResponseDTO pedidoResponseDTO = pedidoService.obterPorId(itensPedidoDTO.getPedidoId());
            PedidoModel pedidoModel = modelMapper.map(pedidoResponseDTO, PedidoModel.class);

            ProdutoResponseDTO produtoResponseDTO = produtoService.obterPorId(itensPedidoDTO.getProdutoId());
            ProdutoModel produtoModel = modelMapper.map(produtoResponseDTO, ProdutoModel.class);

            ItensPedidoModel itensPedido = modelMapper.map(itensPedidoDTO, ItensPedidoModel.class);
            itensPedido.setSubTotal(calcularSubTotal(itensPedido.getQuantidade(), itensPedido.getPrecoUnitario().doubleValue())); //Talvez seja necessário para calcular o subtotal.
            itensPedido.setPedido(pedidoModel); // Associa o item ao pedido.
            itensPedido.setProduto(produtoModel); // Associa o item ao produto.
            
            ItensPedidoModel itensPedidoSalvo = itensPedidoRepository.save(itensPedido);

            ItensPedidoResponseDTO itensPedidoResponse = modelMapper.map(itensPedidoSalvo, ItensPedidoResponseDTO.class);
            itensPedidoResponse.setPedidoId(pedidoModel.getId()); // Adiciona o id do pedido diretamente.
            itensPedidoResponse.setProdutoId(produtoModel.getId()); // Adiciona o id do produto diretamente.

            return itensPedidoResponse;

        }catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                    "Erro de integridade ao salvar o item do pedido " + itensPedidoDTO.getPedidoId() + ".", e
            );
        }

       
    }

    @Transactional
    public ItensPedidoResponseDTO atualizar(long id, ItensPedidoRequestDTO itensPedidoDTO) {

        ItensPedidoModel itensPedidoExistente = itensPedidoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Item do pedido com ID " + id + " não encontrado."));


        modelMapper.map(itensPedidoDTO, itensPedidoExistente);
        itensPedidoExistente.setSubTotal(calcularSubTotal(itensPedidoExistente.getQuantidade(), itensPedidoExistente.getPrecoUnitario().doubleValue())); //Talvez seja necessário para calcular o subtotal.
        ItensPedidoModel itensPedidoAtualizado = itensPedidoRepository.save(itensPedidoExistente);

        return modelMapper.map(itensPedidoAtualizado, ItensPedidoResponseDTO.class);
    }

    @Transactional
    public void deletar(long id) {
        ItensPedidoModel itensPedidoExistente = itensPedidoRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Item do pedido com ID " + id + " não encontrado."));

        itensPedidoRepository.delete(itensPedidoExistente);
    }

    // Possível implementação de métodos para adicionar e remover itens do pedido, calcular subtotal, etc.

    private BigDecimal calcularSubTotal(int quantidade, double precoUnitario) {
        return BigDecimal.valueOf(quantidade).multiply(BigDecimal.valueOf(precoUnitario));
    }
}
