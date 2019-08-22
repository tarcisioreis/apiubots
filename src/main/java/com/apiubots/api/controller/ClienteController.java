package com.apiubots.api.controller;

import com.apiubots.api.dto.ClienteDTO;
import com.apiubots.api.exceptions.BusinessException;
import com.apiubots.api.service.ClienteService;
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
@RequestMapping("/api/v2/cliente")
@CrossOrigin(origins="*")
@Api(value="API REST Cliente Controller")
public class ClienteController {

    private final ClienteService clienteService;

    private List<ClienteDTO> lista;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/list")
    @ApiOperation(value="Listagem de Todos os clientes.")
    ResponseEntity<List<ClienteDTO>> list() {

        try {
            lista = clienteService.findAll();

            if (lista.size() == 0 || lista.isEmpty()) {
                throw new BusinessException("Não foram encontrados clientes.");
            }

            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

    }

}
