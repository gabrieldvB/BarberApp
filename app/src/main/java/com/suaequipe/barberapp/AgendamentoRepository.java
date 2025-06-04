package com.suaequipe.barberapp; // Certifique-se que este é seu pacote raiz

import java.util.ArrayList;
import java.util.List;

public class AgendamentoRepository {

    private static final List<Agendamento> listaAgendamentos = new ArrayList<>();

    public static void addAgendamento(Agendamento agendamento) {
        listaAgendamentos.add(agendamento);
    }

    public static List<Agendamento> getListaAgendamentos() {
        return new ArrayList<>(listaAgendamentos); // Retorna uma cópia
    }

    public static void clearAgendamentos() { // Opcional
        listaAgendamentos.clear();
    }
}