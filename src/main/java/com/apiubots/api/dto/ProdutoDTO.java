package com.apiubots.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

public class ProdutoDTO implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String codigo;

    private String descricao;
    private String variedade;
    private String paisOrigem;
    private String categoria;
    private String safra;
    private Double preco;

    public ProdutoDTO() { super(); }

    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getVariedade() {
        return variedade;
    }
    public void setVariedade(String variedade) {
        this.variedade = variedade;
    }

    public String getPaisOrigem() {
        return paisOrigem;
    }
    public void setPaisOrigem(String paisOrigem) {
        this.paisOrigem = paisOrigem;
    }

    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSafra() {
        return safra;
    }
    public void setSafra(String safra) {
        this.safra = safra;
    }

    public Double getPreco() {
        return preco;
    }
    public void setPreco(Double preco) {
        this.preco = preco;
    }

}
