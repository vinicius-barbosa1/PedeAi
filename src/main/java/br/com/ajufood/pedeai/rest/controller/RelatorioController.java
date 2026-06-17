package br.com.ajufood.pedeai.rest.controller;

import br.com.ajufood.pedeai.rest.dto.response.RelatorioVendasResponse;
import br.com.ajufood.pedeai.service.PedidoService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/relatorio")
public class RelatorioController {

    @Autowired
    PedidoService pedidoService;
    @GetMapping("/vendas-por-categoria")
    public ResponseEntity<List<RelatorioVendasResponse>> relatorio(
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim) throws BadRequestException {

        return ResponseEntity.ok(
                pedidoService.gerarRelatorio(dataInicio, dataFim));
    }
}
