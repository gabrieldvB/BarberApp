package com.suaequipe.barberapp;

public class Cliente {
    private String nomePrincipal;
    private String celular;
    private String email;
    private boolean aceitouTermos;
    private int pontosMilhas; // NOVO CAMPO para os pontos

    // Construtor atualizado
    public Cliente(String nomePrincipal, String celular, String email, boolean aceitouTermos) {
        this.nomePrincipal = nomePrincipal;
        this.celular = celular;
        this.email = email;
        this.aceitouTermos = aceitouTermos;
        this.pontosMilhas = 0; // Clientes começam com 0 pontos
    }

    // Getters
    public String getNomePrincipal() { return nomePrincipal; }
    public String getCelular() { return celular; }
    public String getEmail() { return email; }
    public boolean hasAceitouTermos() { return aceitouTermos; }
    public int getPontosMilhas() { return pontosMilhas; } // Getter para os pontos

    // Método para adicionar pontos
    public void adicionarPontos(int pontos) {
        this.pontosMilhas += pontos;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nomePrincipal='" + nomePrincipal + '\'' +
                ", celular='" + celular + '\'' +
                ", email='" + email + '\'' +
                ", pontosMilhas=" + pontosMilhas + // Adicionado ao toString
                '}';
    }
}