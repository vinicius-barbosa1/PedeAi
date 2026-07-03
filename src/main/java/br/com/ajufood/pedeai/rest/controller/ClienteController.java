package br.com.ajufood.pedeai.rest.controller;

import br.com.ajufood.pedeai.rest.dto.request.ClienteEnderecoRequestDTO;
import br.com.ajufood.pedeai.rest.dto.request.ClienteRequestDTO;
import br.com.ajufood.pedeai.rest.dto.response.ClienteResponseDTO;
import br.com.ajufood.pedeai.rest.dto.response.PedidoResumoDTO;
import br.com.ajufood.pedeai.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador responsável por gerenciar as operações relacionadas aos clientes.
 */
@RestController
@RequestMapping("/cliente")
public class ClienteController {
    /**
     * Instância do serviço de clientes, responsável por encapsular a lógica de negócios
     * e intermediar as operações entre o controlador e o repositório.
     */
    @Autowired
    private ClienteService clienteService;

    /**
     * Obtém um cliente pelo ID.
     * Link: http://localhost:8080/ajufood/pedeai/cliente/?
     *
     * @param id ID do cliente.
     * @return clienteResponseDTO representando o cliente encontrado.
     */
    @Operation(summary = "Busca um cliente pelo id")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> obterPorId(@PathVariable int id) {
        ClienteResponseDTO clienteResponseDTO = clienteService.obterPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(clienteResponseDTO);
    }

    /**
     * Obtém a lista de todos os clientes cadastrados.
     * Link: http://localhost:8080/ajufood/pedeai/cliente
     *
     * @return Lista de clienteResponseDTOS representando os clientes cadastrados.
     */
    @Operation(summary = "Lista todos os clientes")
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> obterTodos() {
        List<ClienteResponseDTO> clienteResponseDTOS = clienteService.obterTodos();
        return ResponseEntity.ok(clienteResponseDTOS);
    }

    /**
     * Salva um novo cliente na base de dados.
     * Link: http://localhost:8080/ajufood/pedeai/cliente
     *
     * @param clienteRequestDTO contendo os dados do novo cliente.
     * @return clienteNovo representando o cliente salvo.
     */
    @Operation(summary = "Cadastra um novo cliente")
    @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso")
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> salvar(@Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
        ClienteResponseDTO clienteNovo = clienteService.salvar(clienteRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteNovo);
    }

    /**
     * Atualiza os dados de um cliente existente.
     * Link: http://localhost:8080/ajufood/pedeai/cliente
     *
     * @param clienteRequestDTO contendo os dados atualizados do cliente.
     * @return clienteAtualizadoDTO representando o cliente atualizado.
     */
    @Operation(summary = "Atualiza um cliente existente")
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizar(
            @PathVariable int id,
            @RequestBody @Valid ClienteRequestDTO clienteRequestDTO) {
        ClienteResponseDTO clienteAtualizadoDTO = clienteService.atualizar(id, clienteRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(clienteAtualizadoDTO);
    }

    /**
     * Deleta um cliente da base de dados.
     * Link: http://localhost:8080/ajufood/pedeai/cliente
     *
     * @param id contendo o identificador do cliente a ser deletado.
     */
    @Operation(summary = "Remove um cliente pelo id")
    @ApiResponse(responseCode = "204", description = "Cliente removido com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // UC 02 - Semana 01 - Médio
    @Operation(summary = "Busca o histórico de pedidos por cliente")
    @GetMapping("/{id}/pedidos")
    public ResponseEntity<Page<PedidoResumoDTO>> buscarHistoricoPorCliente(
            @PathVariable Integer id,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "pagina", defaultValue = "0") int pagina,
            @RequestParam(value = "tamanho", defaultValue = "10") int tamanho
    ){
        Pageable pageable = PageRequest.of(
                pagina,
                tamanho,
                Sort.by("data_hora").descending()
        );

        return ResponseEntity.ok(clienteService.buscarHistoricoPorCliente(id, status, pageable));
    }

    @Operation(summary = "Cadastra cliente com um endereço associado")
    @ApiResponse(responseCode = "201", description = "Cliente + Endereço cadastrado com sucesso")
    @PostMapping("/criar-cliente-endereco")
    public ResponseEntity<ClienteResponseDTO> criarClienteComEndereco(@Valid @RequestBody ClienteEnderecoRequestDTO dto){
        ClienteResponseDTO clienteResponseDTO = clienteService.criarClienteComEndereco(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteResponseDTO);
    }

}
