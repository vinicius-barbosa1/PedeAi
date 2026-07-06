package br.com.ajufood.pedeai.rest.controller;

import br.com.ajufood.pedeai.rest.dto.request.ProdutoDisponibilidadeRequestDTO;
import br.com.ajufood.pedeai.rest.dto.request.ProdutoRequestDTO;
import br.com.ajufood.pedeai.rest.dto.response.ProdutoResponseDTO;
import br.com.ajufood.pedeai.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Operation(summary = "Busca um produto por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> obterPorId(@PathVariable int id){
        ProdutoResponseDTO produtoResponseDTO = produtoService.obterPorId(id);
        return ResponseEntity.ok(produtoResponseDTO);
    }

    @Operation(summary = "Lista todos os produtos")
    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> obterTodos(){
        return ResponseEntity.ok(produtoService.obterTodos());
    }

    @Operation(summary = "Busca um produto pelo nome")
    @GetMapping("/nome/{nome}")
    public ResponseEntity<ProdutoResponseDTO> obterPorNome(@PathVariable String nome){
        return ResponseEntity.ok(produtoService.obterPorNome(nome));
    }

    @Operation(summary = "Cadastra um novo produto")
    @ApiResponse(responseCode = "201", description = "Produto criado com sucesso")
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> salvar(@Valid @RequestBody ProdutoRequestDTO produtoRequestDTO){
        ProdutoResponseDTO produtoNovo = produtoService.salvar(produtoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoNovo);
    }

    @Operation(summary = "Atualiza um produto existente")
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable int id, @Valid @RequestBody ProdutoRequestDTO produtoRequestDTO){
        ProdutoResponseDTO produtoAtualizado = produtoService.atualizar(id, produtoRequestDTO);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @Operation(summary = "Remove um produto por ID")
    @ApiResponse(responseCode = "204", description = "Produto removido com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(int id){
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // UC 01 - Semana 01 -
    @Operation(summary = "Lista todos os produtos disponíveis (com filtro opcional)")
    @GetMapping("/disponivel")
    public ResponseEntity<List<ProdutoResponseDTO>> ListarProdutosPorDisponibilidade(
            @RequestParam(value = "categoriaId", required = false) Integer categoriaProdutoId){
        return ResponseEntity.ok(produtoService.ListarProdutosPorDisponibilidade(categoriaProdutoId));
    }

    // UC 07 - Ativar e Desativar Produto
    @Operation(summary = "Ativa ou desativa a disponibilidade de um produto.")
    @PatchMapping("/{produtoId}/disponibilidade")
    public ResponseEntity<ProdutoResponseDTO> AtivarEDesativarProduto(
            @PathVariable int produtoId, @RequestBody @Valid ProdutoDisponibilidadeRequestDTO disponivel){
        return ResponseEntity.ok(produtoService.AtivarEDesativarProduto(produtoId, disponivel));
    }

}
