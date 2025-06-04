package com.suaequipe.barberapp; // Certifique-se que este é seu pacote raiz

public class Agendamento {
    private String nomeCliente; // Ou o ID do cliente, se preferir referenciar o objeto Cliente
    private String emailCliente; // Adicionado para fácil exibição
    private String servico;
    private String data;
    private String hora;
    private String formaPagamento;
    // Opcional: mais detalhes do cartão se precisar exibir, mas geralmente não se exibe tudo
    private String detalhesCartao; // Ex: "Visa final 1234"

    public Agendamento(String nomeCliente, String emailCliente, String servico, String data, String hora, String formaPagamento, String detalhesCartao) {
        this.nomeCliente = nomeCliente;
        this.emailCliente = emailCliente;
        this.servico = servico;
        this.data = data;
        this.hora = hora;
        this.formaPagamento = formaPagamento;
        this.detalhesCartao = detalhesCartao; // Pode ser uma string simples ou null
    }

    // Getters
    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public String getServico() {
        return servico;
    }

    public String getData() {
        return data;
    }

    public String getHora() {
        return hora;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public String getDetalhesCartao() {
        return detalhesCartao;
    }
}