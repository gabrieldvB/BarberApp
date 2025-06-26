package com.suaequipe.barberapp.ui.historicoagendamentos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton; // NOVO
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.suaequipe.barberapp.Agendamento;
import com.suaequipe.barberapp.R;
import java.util.List;

public class HistoricoAgendamentosAdapter extends RecyclerView.Adapter<HistoricoAgendamentosAdapter.HistoricoViewHolder> {

    private List<Agendamento> listaAgendamentos;
    private final OnDeleteClickListener listener; // NOVO

    // NOVA INTERFACE
    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    // CONSTRUTOR MODIFICADO
    public HistoricoAgendamentosAdapter(List<Agendamento> listaAgendamentos, OnDeleteClickListener listener) {
        this.listaAgendamentos = listaAgendamentos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HistoricoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_agendamento, parent, false);
        return new HistoricoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoricoViewHolder holder, int position) {
        // ... (lógica existente para preencher os dados)

        // NOVO: Ação para o botão de excluir
        holder.btnExcluir.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaAgendamentos == null ? 0 : listaAgendamentos.size();
    }

    // VIEWHOLDER MODIFICADO
    static class HistoricoViewHolder extends RecyclerView.ViewHolder {
        // ... (TextViews existentes)
        ImageButton btnExcluir; // NOVO

        public HistoricoViewHolder(@NonNull View itemView) {
            super(itemView);
            // ... (findViewById para os TextViews)
            btnExcluir = itemView.findViewById(R.id.btnExcluirAgendamento); // NOVO
        }
    }

    public void atualizarLista(List<Agendamento> novaLista) {
        this.listaAgendamentos = novaLista;
        notifyDataSetChanged();
    }
}