package br.com.ajufood.pedeai.rest.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ajufood.pedeai.rest.dto.request.EnderecoRequestDTO;
import br.com.ajufood.pedeai.rest.dto.response.EnderecoResponseDTO;
import br.com.ajufood.pedeai.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {
    
    @Autowired
    private EnderecoService enderecoService;

    @Operation(summary = "Busca um endereço pelo id")
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoResponseDTO> obterPorId(@PathVariable int id){
        EnderecoResponseDTO enderecoResponseDTO = enderecoService.obterPorId(id);
        return ResponseEntity.ok(enderecoResponseDTO);
    }


    @Operation(summary = "Busca todos os endereços")
    @GetMapping
    public ResponseEntity<List<EnderecoResponseDTO>> obterTodos(){
        return ResponseEntity.ok(enderecoService.obterTodos());
    }

    @Operation(summary = "Salva um novo endereço")
    @ApiResponse(responseCode = "201", description = "Endereço criado com sucesso")
    @PostMapping
    public ResponseEntity<EnderecoResponseDTO> salvar(@Valid @RequestBody EnderecoRequestDTO enderecoRequestDTO){
        EnderecoResponseDTO enderecoSalvo = enderecoService.salvar(enderecoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoSalvo);
    }

    @Operation(summary = "Atualiza um endereço existente pelo id")
    @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso")
    @PutMapping("/{id}")
    public ResponseEntity<EnderecoResponseDTO> atualizar(@PathVariable int id,
                                                         @Valid @RequestBody EnderecoRequestDTO enderecoRequestDTO){
        EnderecoResponseDTO enderecoAtualizado = enderecoService.atualizar(id, enderecoRequestDTO);
        return ResponseEntity.ok(enderecoAtualizado);
    }



    @Operation(summary = "Deleta um endereço pelo id")
    @ApiResponse(responseCode = "204", description = "Endereço deletado com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id){
        enderecoService.deletar(id);
        return ResponseEntity.noContent().build();
    }


    
}
