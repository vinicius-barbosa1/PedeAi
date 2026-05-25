package br.com.ajufood.pedeai.rest.controller;


import br.com.ajufood.pedeai.rest.dto.request.PagamentoRequestDTO;
import br.com.ajufood.pedeai.rest.dto.response.PagamentoResponseDTO;
import br.com.ajufood.pedeai.service.PagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @Operation(summary = "Busca um pagamento pelo id")
    @GetMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> obterPorId(@PathVariable int id){
        PagamentoResponseDTO pagamentoResponseDTO = pagamentoService.obterPorId(id);
        return ResponseEntity.ok(pagamentoResponseDTO);
    }

    @Operation(summary = "Lista todos os Pagamentos")
    @GetMapping
    public ResponseEntity<List<PagamentoResponseDTO>> obterTodosPagamentos(){
        return ResponseEntity.ok(pagamentoService.obterTodos());
    }

    @Operation(summary = "Cria um novo pagamento")
    @ApiResponse(responseCode = "201", description = "Pagamento criado com sucesso")
    @PostMapping
    public ResponseEntity<PagamentoResponseDTO> salvar(@Valid @RequestBody PagamentoRequestDTO pagamentoRequestDTO){
        PagamentoResponseDTO pagamentoSalvo = pagamentoService.salvar(pagamentoRequestDTO);
        return ResponseEntity.ok(pagamentoSalvo);
    }

    @Operation(summary = "Atualiza um pagamento")
    @ApiResponse(responseCode = "200", description = "Pagamento atualizado com sucesso")
    @PutMapping("/{id}")
    public ResponseEntity<PagamentoResponseDTO> atualizar(
            @PathVariable int id,
            @Valid @RequestBody PagamentoRequestDTO pagamentoRequestDTO){

        PagamentoResponseDTO pagamentoResponseDTO = pagamentoService.atualizar(id, pagamentoRequestDTO);
        return ResponseEntity.ok(pagamentoResponseDTO);

    }


    @Operation(summary = "Deleta um pagamento pelo id")
    @ApiResponse(responseCode = "204", description = "Pagamento deletado com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        pagamentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }


}
