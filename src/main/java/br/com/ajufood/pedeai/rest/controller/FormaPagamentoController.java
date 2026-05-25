package br.com.ajufood.pedeai.rest.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.ajufood.pedeai.rest.dto.request.FormaPagamentoRequestDTO;
import br.com.ajufood.pedeai.rest.dto.response.FormaPagamentoResponseDTO;
import br.com.ajufood.pedeai.service.FormaPagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/forma-pagamento")
public class FormaPagamentoController {
    

    @Autowired
    private FormaPagamentoService formaPagamentoService;


    @Operation(summary = "Busca uma forma de pagamento pelo id")
    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamentoResponseDTO> obterPorId(@PathVariable int id) {
        FormaPagamentoResponseDTO formaPagamentoResponseDTO = formaPagamentoService.obterPorId(id);
        return ResponseEntity.ok(formaPagamentoResponseDTO);
    }

    @Operation(summary = "Busca uma forma de pagamento pelo nome")
    @GetMapping("/nome")
    public ResponseEntity<FormaPagamentoResponseDTO> obterPorNome(@RequestParam String nome) {
        FormaPagamentoResponseDTO formaPagamentoResponseDTO = formaPagamentoService.obterPorNome(nome);
        return ResponseEntity.ok(formaPagamentoResponseDTO);
    }

    @Operation(summary = "Lista todas as formas de pagamento")
    @GetMapping
    public ResponseEntity<List<FormaPagamentoResponseDTO>> obterTodos() {
        List<FormaPagamentoResponseDTO> formaPagamentoResponseDTOS = formaPagamentoService.obterTodos();
        return ResponseEntity.ok(formaPagamentoResponseDTOS);
    }


    @Operation(summary = "Salva uma nova forma de pagamento")
    @ApiResponse(responseCode = "201", description = "Forma de pagamento criada com sucesso")
    @PostMapping
    public ResponseEntity<FormaPagamentoResponseDTO> salvar(@RequestBody @Valid FormaPagamentoRequestDTO formaPagamentoRequestDTO) {
        FormaPagamentoResponseDTO formaPagamentoSalva = formaPagamentoService.salvar(formaPagamentoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(formaPagamentoSalva);
    }
    

    @Operation(summary = "Atualiza uma forma de pagamento existente")
    @ApiResponse(responseCode = "200", description = "Forma de pagamento atualizada com sucesso")
    @PutMapping("/{id}")
    public ResponseEntity<FormaPagamentoResponseDTO> atualizar(@PathVariable int id, @Valid @RequestBody FormaPagamentoRequestDTO formaPagamentoRequestDTO) {
        FormaPagamentoResponseDTO formaPagamentoAtualizada = formaPagamentoService.atualizar(id, formaPagamentoRequestDTO);
        return ResponseEntity.status(200).body(formaPagamentoAtualizada);
    }

    @Operation(summary = "Deleta uma forma de pagamento pelo id")
    @ApiResponse(responseCode = "204", description = "Forma de pagamento deletada com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        formaPagamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
