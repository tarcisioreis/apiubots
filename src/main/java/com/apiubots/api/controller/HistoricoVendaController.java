package com.apiubots.api.controller;

import com.apiubots.api.dto.ClienteDTO;
import com.apiubots.api.dto.HistoricoVendaDTO;
import com.apiubots.api.dto.ProdutoDTO;
import com.apiubots.api.exceptions.BusinessException;
import com.apiubots.api.service.ClienteService;
import com.apiubots.api.service.HistoricoVendaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v2/historicovenda")
@CrossOrigin(origins="*")
@Api(value="API REST Historico Venda Controller")
public class HistoricoVendaController {

    private final HistoricoVendaService historicoVendaService;
    private final ClienteService clienteService;

    private List<HistoricoVendaDTO> lista;
    private HistoricoVendaDTO historicoVendaDTO;

    @Autowired
    public HistoricoVendaController(HistoricoVendaService historicoVendaService,
                                    ClienteService clienteService) {
        this.historicoVendaService = historicoVendaService;
        this.clienteService = clienteService;
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

    @GetMapping("/listClienteMaiorValorTotalCompra")
    @ApiOperation(value="Listagem de Clientes pelo maior valor total de compras.")
    ResponseEntity<List<HistoricoVendaDTO>> listClienteMaiorValorTotalCompra() {

        try {
            lista = historicoVendaService.findClienteMaiorValorTotalCompra();

            if (lista.size() == 0 || lista.isEmpty()) {
                throw new BusinessException("Não foram encontrados historico de venda para clientes.");
            }

            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

    }

    @GetMapping("/listClienteMaiorValorTotalCompraUnicaPorAno/{ano}")
    @ApiOperation(value="Mostra o Cliente com maior valor total de compra unica por ano.")
    ResponseEntity<List<HistoricoVendaDTO>> listClienteMaiorValorTotalCompraUnicaPorAno(@Valid @PathVariable int ano) {

        try {
            lista = historicoVendaService.findClienteMaiorValorTotalCompraUnicaPorAno(ano);

            if (lista.size() == 0 || lista.isEmpty()) {
                throw new BusinessException("Não foi encontrado historico de venda para o ano informado.");
            }

            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

    }

    @GetMapping("/recomendarVinhoaClientePorPerfildeCompra")
    @ApiOperation(value="Recomenda Vinho a determinado Cliente conforme seu perfil de compra.")
    ResponseEntity<List<HistoricoVendaDTO>> recomendarVinhoaClientePorPerfildeCompra(@Valid @RequestParam(name = "variedade") String variedade,
                                                                                            @RequestParam(name = "nome") String nome) {

        try {
            ProdutoDTO produtoDTO = historicoVendaService.findByVariedade(variedade);
            ClienteDTO clienteDTO = clienteService.findByNome(nome);

            if (produtoDTO == null) {
                throw new Exception("Não foi encontrado nenhum produto com variedade informada.");
            }

            if (clienteDTO == null) {
                throw new Exception("Não foi encontrado nenhum cliente com nome informado.");
            }

            lista = historicoVendaService.recomendarVinhoaClientePorPerfildeCompra(variedade, nome);

            if (lista.size() == 0 || lista.isEmpty()) {
                throw new BusinessException("Não foi encontrado nenhuma compra com variedade informado para perfil do cliente informado.");
            }

            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

    }

}
