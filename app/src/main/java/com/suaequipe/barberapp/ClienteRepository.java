package com.suaequipe.barberapp;

import java.util.ArrayList;
import java.util.List;

public class ClienteRepository {

    private static final List<Cliente> listaClientes = new ArrayList<>();

    public static void addCliente(Cliente cliente) {
        listaClientes.add(cliente);
    }

    public static List<Cliente> getListaClientes() {
        return new ArrayList<>(listaClientes);
    }

    // NOVO MÉTODO PARA REMOVER UM CLIENTE
    public static void removeCliente(Cliente cliente) {
        if (cliente != null) {
            listaClientes.remove(cliente);
        }
    }

    public static void clearClientes() {
        listaClientes.clear();
    }
}