package br.com.ajufood.pedeai.rest.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoResumoDTO(
        // Pedido
        Integer idPedido,
        LocalDateTime dataHora,
        String status,
        BigDecimal valorTotal,

        // Endereço de entrega
        String endereco,
        Integer numero,
        String bairro,
        String cidade,

        // Item de pedidos
        List<ItemPedidoResumoDTO> itens
) {

    public static PedidoResumoDTO montarPedidoResumo(
            PedidoResponseDTO pedido,
            EnderecoResponseDTO endereco,
            List<ItemPedidoResumoDTO> listaDeItensPronta
    ) {
        return new PedidoResumoDTO(
                pedido.getId(),
                pedido.getDataHora(),
                pedido.getStatus(),
                pedido.getValorTotal(),

                endereco.getEndereco(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getCidade(),

                listaDeItensPronta
        );
    }
}