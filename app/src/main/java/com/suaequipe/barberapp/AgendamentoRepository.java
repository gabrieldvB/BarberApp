package com.suaequipe.barberapp;

import java.util.ArrayList;
import java.util.List;

public class AgendamentoRepository {

    private static final List<Agendamento> listaAgendamentos = new ArrayList<>();

    public static void addAgendamento(Agendamento agendamento) {
        listaAgendamentos.add(agendamento);
    }

    public static List<Agendamento> getListaAgendamentos() {
        return new ArrayList<>(listaAgendamentos);
    }

    // NOVO MÉTODO PARA REMOVER UM AGENDAMENTO
    public static void removeAgendamento(Agendamento agendamento) {
        if (agendamento != null) {
            listaAgendamentos.remove(agendamento);
        }
    }

    public static void clearAgendamentos() {
        listaAgendamentos.clear();
    }
}