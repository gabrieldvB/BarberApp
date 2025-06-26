package com.suaequipe.barberapp.ui.historicoagendamentos;

import android.app.AlertDialog; // NOVO
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast; // NOVO
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.suaequipe.barberapp.Agendamento;
import com.suaequipe.barberapp.AgendamentoRepository;
import com.suaequipe.barberapp.databinding.FragmentHistoricoAgendamentosBinding;
import java.util.List;

// MODIFICADO PARA IMPLEMENTAR A NOVA INTERFACE
public class HistoricoAgendamentosFragment extends Fragment implements HistoricoAgendamentosAdapter.OnDeleteClickListener {

    private FragmentHistoricoAgendamentosBinding binding;
    private HistoricoAgendamentosAdapter adapter;
    private List<Agendamento> listaDeAgendamentos; // Adicionar lista como variável de classe

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHistoricoAgendamentosBinding.inflate(inflater, container, false);
        setupRecyclerView();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        carregarAgendamentos();
    }

    private void setupRecyclerView() {
        binding.recyclerViewHistoricoAgendamentos.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void carregarAgendamentos() {
        listaDeAgendamentos = AgendamentoRepository.getListaAgendamentos(); // Usar a variável de classe

        if (listaDeAgendamentos.isEmpty()) {
            binding.recyclerViewHistoricoAgendamentos.setVisibility(View.GONE);
            binding.textViewHistoricoVazio.setVisibility(View.VISIBLE);
        } else {
            binding.recyclerViewHistoricoAgendamentos.setVisibility(View.VISIBLE);
            binding.textViewHistoricoVazio.setVisibility(View.GONE);

            if (adapter == null) {
                // MODIFICADO: Passar o listener
                adapter = new HistoricoAgendamentosAdapter(listaDeAgendamentos, this);
                binding.recyclerViewHistoricoAgendamentos.setAdapter(adapter);
            } else {
                adapter.atualizarLista(listaDeAgendamentos);
            }
        }
    }

    // NOVO MÉTODO DA INTERFACE PARA EXCLUSÃO
    @Override
    public void onDeleteClick(int position) {
        Agendamento agendamentoParaExcluir = listaDeAgendamentos.get(position);

        new AlertDialog.Builder(requireContext())
                .setTitle("Confirmar Cancelamento")
                .setMessage("Deseja cancelar o agendamento de " + agendamentoParaExcluir.getNomeCliente() + " em " + agendamentoParaExcluir.getData() + "?")
                .setPositiveButton("Sim", (dialog, which) -> {
                    AgendamentoRepository.removeAgendamento(agendamentoParaExcluir);
                    Toast.makeText(getContext(), "Agendamento cancelado.", Toast.LENGTH_SHORT).show();
                    carregarAgendamentos();
                })
                .setNegativeButton("Não", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        adapter = null;
    }
}