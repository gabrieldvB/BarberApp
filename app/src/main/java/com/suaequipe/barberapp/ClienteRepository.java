package com.suaequipe.barberapp; // Certifique-se que este é seu pacote raiz

import java.util.ArrayList;
import java.util.List;

public class ClienteRepository {

    private static final List<Cliente> listaClientes = new ArrayList<>();

    public static void addCliente(Cliente cliente) {
        listaClientes.add(cliente);
    }

    public static List<Cliente> getListaClientes() {
        return new ArrayList<>(listaClientes); // Retorna uma cópia para evitar modificação externa direta
    }

    public static void clearClientes() { // Opcional: para limpar a lista para testes
        listaClientes.clear();
    }
}