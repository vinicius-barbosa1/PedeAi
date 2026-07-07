package br.com.ajufood.pedeai.rest.controller;

import br.com.ajufood.pedeai.rest.dto.request.PedidoRequestDTO;
import br.com.ajufood.pedeai.rest.dto.response.PedidoFluxoStatus;
import br.com.ajufood.pedeai.rest.dto.response.PedidoResponseDTO;
import br.com.ajufood.pedeai.rest.enums.PedidoStatus;
import br.com.ajufood.pedeai.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Operation(summary = "Busca um pedido por id")
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> obterPorId(@PathVariable int id){
        PedidoResponseDTO pedidoResponseDTO = pedidoService.obterPorId(id);
        return ResponseEntity.ok(pedidoResponseDTO);
    }

    @Operation(summary = "Lista todos os pedidos")
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> obterTodos(){
        return ResponseEntity.ok().body(pedidoService.obterTodos());
    }


    @Operation(summary = "Cria um novo pedido")
    @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso")
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> salvar(@Valid @RequestBody PedidoRequestDTO pedidoRequestDTO){
        PedidoResponseDTO pedidoNovo = pedidoService.salvar(pedidoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoNovo);
    }

    @Operation(summary = "Atualiza um pedido existe")
    @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso")
    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> atualizar(@PathVariable int id,
                                                       @Valid @RequestBody PedidoRequestDTO pedidoRequestDTO){
        PedidoResponseDTO pedidoAtualizado = pedidoService.atualizar(id, pedidoRequestDTO);
        return ResponseEntity.ok(pedidoAtualizado);
    }


    @Operation(summary = "Deleta um pedido por id")
    @ApiResponse(responseCode = "204", description = "Pedido deletado com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        pedidoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    // UC-09 - Gerenciar Fluxo de Status do Pedido
    @Operation(summary = "Gerenciar Fluxo de Status do Pedido.")
    @PatchMapping("/{idPedido}/status")
    public ResponseEntity<PedidoFluxoStatus> gerenciarPedidoFluxoStatus(int idPedido, PedidoStatus pedidoStatus){
        PedidoFluxoStatus pedidoFluxoStatus = pedidoService.gerenciarPedidoFluxoStatus(idPedido, pedidoStatus);
        return ResponseEntity.ok(pedidoFluxoStatus);
    }
}
