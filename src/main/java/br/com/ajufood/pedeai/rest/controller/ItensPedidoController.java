package br.com.ajufood.pedeai.rest.controller;

import br.com.ajufood.pedeai.rest.dto.request.ItensPedidoRequestDTO;
import br.com.ajufood.pedeai.rest.dto.response.ItensPedidoResponseDTO;
import br.com.ajufood.pedeai.service.ItensPedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens-pedido")
public class ItensPedidoController {

    @Autowired
    private ItensPedidoService itensPedidoService;

    @Operation(summary = "Busca os itens pedidos por id")
    @GetMapping("/{id}")
    public ResponseEntity<ItensPedidoResponseDTO> obterPorId(@PathVariable int id){
        ItensPedidoResponseDTO itensPedidoResponseDTO = itensPedidoService.obterPorId(id);
        return ResponseEntity.ok(itensPedidoResponseDTO);
    }


    @Operation(summary = "Lista todos os itens pedidos")
    @GetMapping
    public ResponseEntity<List<ItensPedidoResponseDTO>> obterTodos(){
        return ResponseEntity.ok(itensPedidoService.obterTodos());
    }


    @Operation(summary = "Cria itens pedido")
    @ApiResponse(responseCode = "201", description = "Itens pedido criado com sucesso")
    @PostMapping
    public ResponseEntity<ItensPedidoResponseDTO> salvar(@Valid @RequestBody ItensPedidoRequestDTO itensPedidoRequestDTO){
        ItensPedidoResponseDTO itensPedidoNovo = itensPedidoService.salvar(itensPedidoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(itensPedidoNovo);
    }


    @Operation(summary = "Atualiza itens pedido existente")
    @ApiResponse(responseCode = "200", description = "Itens pedido atualizado com sucesso")
    @PutMapping("/{id}")
    public ResponseEntity<ItensPedidoResponseDTO> atualizar(@PathVariable int id,
                                                            @Valid @RequestBody ItensPedidoRequestDTO itensPedidoRequestDTO){
        ItensPedidoResponseDTO itensPedidoAtualizado = itensPedidoService.atualizar(id, itensPedidoRequestDTO);
        return ResponseEntity.ok(itensPedidoAtualizado);
    }

    @Operation(summary = "Deleta itens pedido existente")
    @ApiResponse(responseCode = "204", description = "Itens pedido deletado com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id){
        itensPedidoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
