package br.com.ajufood.pedeai.rest.enums;

public enum PedidoStatus {
    AGUARDANDO_CONFIRMACAO("Aguardando Confirmação."),
    CONFIRMADO("Pedido confirmado."),
    EM_PREPARO("Pedido está sendo preparado."),
    SAIU_PARA_ENTREGA("Pedido está a caminho."),
    ENTREGUE("Pedido entregue com sucesso."),
    CANCELADO("Pedido cancelado com sucesso.");


    private final String descricao;

    PedidoStatus(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }

    public boolean podeIrPara(PedidoStatus destino) {

        return switch (this) {

            case AGUARDANDO_CONFIRMACAO ->
                    destino == CONFIRMADO ||
                            destino == CANCELADO;

            case CONFIRMADO ->
                    destino == EM_PREPARO ||
                            destino == CANCELADO;

            case EM_PREPARO ->
                    destino == SAIU_PARA_ENTREGA ||
                            destino == CANCELADO;

            case SAIU_PARA_ENTREGA ->
                    destino == ENTREGUE ||
                            destino == CANCELADO;

            case ENTREGUE, CANCELADO ->
                    false;
        };
    }
}
