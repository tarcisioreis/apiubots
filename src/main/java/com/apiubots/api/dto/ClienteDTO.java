package com.apiubots.api.dto;

import java.io.Serializable;

public class ClienteDTO implements Serializable {

    private int id;
    private String nome;
    private String cpf;

    public ClienteDTO() { super(); }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

}
