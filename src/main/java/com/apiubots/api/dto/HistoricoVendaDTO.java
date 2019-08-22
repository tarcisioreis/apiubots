package com.apiubots.api.dto;

import java.io.Serializable;
import java.util.List;

public class HistoricoVendaDTO implements Serializable {

    private String codigo;
    private String data;
    private ClienteDTO clienteDTO;
    private List<ProdutoDTO> itens;
    private Double precoTotal;

    public HistoricoVendaDTO() { super(); }

    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

    public ClienteDTO getClienteDTO() {
        return clienteDTO;
    }
    public void setClienteDTO(ClienteDTO clienteDTO) {
        this.clienteDTO = clienteDTO;
    }

    public List<ProdutoDTO> getItens() {
        return itens;
    }
    public void setItens(List<ProdutoDTO> itens) {
        this.itens = itens;
    }

    public Double getPrecoTotal() {
        return precoTotal;
    }
    public void setPrecoTotal(Double precoTotal) {
        this.precoTotal = precoTotal;
    }

}

