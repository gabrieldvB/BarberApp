package com.suaequipe.barberapp.ui.historicoagendamentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.suaequipe.barberapp.Agendamento;
import com.suaequipe.barberapp.AgendamentoRepository;
import com.suaequipe.barberapp.databinding.FragmentHistoricoAgendamentosBinding; // ViewBinding
import java.util.List;

public class HistoricoAgendamentosFragment extends Fragment {

    private FragmentHistoricoAgendamentosBinding binding;
    private HistoricoAgendamentosAdapter adapter;

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
        // Carregar/atualizar os agendamentos sempre que o fragmento se tornar visível
        carregarAgendamentos();
    }

    private void setupRecyclerView() {
        binding.recyclerViewHistoricoAgendamentos.setLayoutManager(new LinearLayoutManager(getContext()));
        // O adapter será criado/atualizado em carregarAgendamentos
    }

    private void carregarAgendamentos() {
        List<Agendamento> listaDeAgendamentos = AgendamentoRepository.getListaAgendamentos();

        if (listaDeAgendamentos.isEmpty()) {
            binding.recyclerViewHistoricoAgendamentos.setVisibility(View.GONE);
            binding.textViewHistoricoVazio.setVisibility(View.VISIBLE);
        } else {
            binding.recyclerViewHistoricoAgendamentos.setVisibility(View.VISIBLE);
            binding.textViewHistoricoVazio.setVisibility(View.GONE);

            if (adapter == null) {
                adapter = new HistoricoAgendamentosAdapter(listaDeAgendamentos);
                binding.recyclerViewHistoricoAgendamentos.setAdapter(adapter);
            } else {
                adapter.atualizarLista(listaDeAgendamentos);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Limpar a referência do binding
        adapter = null; // Limpar a referência do adapter
    }
}