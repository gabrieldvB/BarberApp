package com.suaequipe.barberapp.ui.historicoagendamentos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton; // Importe o ImageButton
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.suaequipe.barberapp.Agendamento;
import com.suaequipe.barberapp.R;
import java.util.List;

public class HistoricoAgendamentosAdapter extends RecyclerView.Adapter<HistoricoAgendamentosAdapter.HistoricoViewHolder> {

    private List<Agendamento> listaAgendamentos;
    private final OnDeleteClickListener listener;

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

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
        Agendamento agendamentoAtual = listaAgendamentos.get(position);

        holder.textViewClienteNome.setText(agendamentoAtual.getNomeCliente());
        holder.textViewServico.setText(agendamentoAtual.getServico());
        holder.textViewData.setText(agendamentoAtual.getData());
        holder.textViewHora.setText(agendamentoAtual.getHora());
        holder.textViewFormaPagamento.setText(agendamentoAtual.getFormaPagamento());

        if (agendamentoAtual.getDetalhesCartao() != null && !agendamentoAtual.getDetalhesCartao().isEmpty()) {
            holder.textViewDetalhesCartao.setText("Cartão: " + agendamentoAtual.getDetalhesCartao());
            holder.textViewDetalhesCartao.setVisibility(View.VISIBLE);
        } else {
            holder.textViewDetalhesCartao.setVisibility(View.GONE);
        }

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

    // Classe ViewHolder CORRIGIDA E COMPLETA
    static class HistoricoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewClienteNome;
        TextView textViewServico;
        TextView textViewData;
        TextView textViewHora;
        TextView textViewFormaPagamento;
        TextView textViewDetalhesCartao;
        ImageButton btnExcluir; // O botão de exclusão

        public HistoricoViewHolder(@NonNull View itemView) {
            super(itemView);
            // Garantindo que TODOS os componentes sejam encontrados
            textViewClienteNome = itemView.findViewById(R.id.textViewItemAgClienteNome);
            textViewServico = itemView.findViewById(R.id.textViewItemAgServico);
            textViewData = itemView.findViewById(R.id.textViewItemAgData);
            textViewHora = itemView.findViewById(R.id.textViewItemAgHora);
            textViewFormaPagamento = itemView.findViewById(R.id.textViewItemAgFormaPagamento);
            textViewDetalhesCartao = itemView.findViewById(R.id.textViewItemAgDetalhesCartao);
            btnExcluir = itemView.findViewById(R.id.btnExcluirAgendamento);
        }
    }

    public void atualizarLista(List<Agendamento> novaLista) {
        this.listaAgendamentos = novaLista;
        notifyDataSetChanged();
    }
}