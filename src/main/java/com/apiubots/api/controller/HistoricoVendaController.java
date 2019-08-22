package com.apiubots.api.controller;

import com.apiubots.api.dto.ClienteDTO;
import com.apiubots.api.dto.HistoricoVendaDTO;
import com.apiubots.api.exceptions.BusinessException;
import com.apiubots.api.service.HistoricoVendaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2/historicovenda")
@CrossOrigin(origins="*")
@Api(value="API REST Historico Venda Controller")
public class HistoricoVendaController {

    private final HistoricoVendaService historicoVendaService;

    private List<HistoricoVendaDTO> lista;

    @Autowired
    public HistoricoVendaController(HistoricoVendaService historicoVendaService) {
        this.historicoVendaService = historicoVendaService;
    }

    @GetMapping("/list")
    @ApiOperation(value="Listagem de Todo o historico de vendas.")
    ResponseEntity<List<HistoricoVendaDTO>> list() {

        try {
            lista = historicoVendaService.findAll();

            if (lista.size() == 0 || lista.isEmpty()) {
                throw new BusinessException("Não foram encontrados historico de vendas.");
            }

            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

    }

}
