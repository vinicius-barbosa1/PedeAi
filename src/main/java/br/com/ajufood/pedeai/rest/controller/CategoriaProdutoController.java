package br.com.ajufood.pedeai.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ajufood.pedeai.rest.dto.request.CategoriaProdutoRequestDTO;
import br.com.ajufood.pedeai.rest.dto.response.CategoriaProdutoResponseDTO;
import br.com.ajufood.pedeai.service.CategoriaProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categoria-produto")
public class CategoriaProdutoController {

    @Autowired
    private CategoriaProdutoService categoriaProdutoService;
    
    @Operation(summary = "Busca uma categoria de produto pelo id")
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaProdutoResponseDTO> obterPorId(@PathVariable int id){
        CategoriaProdutoResponseDTO categoriaProdutoResponseDTO = categoriaProdutoService.obterPorId(id);
        return ResponseEntity.ok(categoriaProdutoResponseDTO);
    }

    @Operation(summary = "Busca todas as categorias de produto")
    @GetMapping
    public ResponseEntity<List<CategoriaProdutoResponseDTO>> obterTodos() {
        List<CategoriaProdutoResponseDTO> categoriaProdutoResponseDTOS = categoriaProdutoService.obterTodos();
        return ResponseEntity.ok(categoriaProdutoResponseDTOS);
    }

    @Operation(summary = "Salva uma nova categoria de produto")
    @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso")
    @PostMapping
    public ResponseEntity<CategoriaProdutoResponseDTO> salvar(@Valid @RequestBody CategoriaProdutoRequestDTO CategoriaProdutoRequestDTO) {
        CategoriaProdutoResponseDTO categoriaProdutoSalva = categoriaProdutoService.salvar(CategoriaProdutoRequestDTO);
        return ResponseEntity.ok(categoriaProdutoSalva);
    }

    @Operation(summary = "Atualiza uma categoria de produto existente")
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaProdutoResponseDTO> atualizar(@Valid @PathVariable int id, 
        @Valid @RequestBody CategoriaProdutoRequestDTO categoriaProdutoRequestDTO) {
        CategoriaProdutoResponseDTO categoriaProdutoAtualizada = categoriaProdutoService.atualizar(id, categoriaProdutoRequestDTO);
        return ResponseEntity.ok(categoriaProdutoAtualizada);
    }


    @Operation(summary = "Deleta uma categoria de produto pelo id")
    @ApiResponse(responseCode = "204", description = "Categoria deletada com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        categoriaProdutoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
