package br.com.ajufood.pedeai.rest.dto.response;

import java.util.List;

public record EnderecoListaAtualizadaResponseDTO(
        List<EnderecoResponseDTO> listaAtualizada
) {}
