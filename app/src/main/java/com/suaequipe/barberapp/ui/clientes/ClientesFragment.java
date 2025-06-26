package com.suaequipe.barberapp.ui.clientes;

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
import com.suaequipe.barberapp.Cliente;
import com.suaequipe.barberapp.ClienteRepository;
import com.suaequipe.barberapp.databinding.FragmentClientesBinding;
import java.util.List;

// MODIFICADO PARA IMPLEMENTAR A NOVA INTERFACE
public class ClientesFragment extends Fragment implements ClienteAdapter.OnDeleteClickListener {

    private FragmentClientesBinding binding;
    private ClienteAdapter clienteAdapter;
    private List<Cliente> clientes; // Adicionar lista como variável de classe

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentClientesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setupRecyclerView();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        carregarClientes();
    }

    private void setupRecyclerView() {
        binding.recyclerViewClientes.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void carregarClientes() {
        clientes = ClienteRepository.getListaClientes(); // Usar a variável de classe

        if (clientes.isEmpty()) {
            binding.recyclerViewClientes.setVisibility(View.GONE);
            binding.textViewListaVazia.setVisibility(View.VISIBLE);
        } else {
            binding.recyclerViewClientes.setVisibility(View.VISIBLE);
            binding.textViewListaVazia.setVisibility(View.GONE);

            if (clienteAdapter == null) {
                // MODIFICADO: Passar o listener para o adapter
                clienteAdapter = new ClienteAdapter(clientes, this);
                binding.recyclerViewClientes.setAdapter(clienteAdapter);
            } else {
                clienteAdapter.atualizarLista(clientes);
            }
        }
    }

    // NOVO MÉTODO DA INTERFACE PARA EXCLUSÃO
    @Override
    public void onDeleteClick(int position) {
        // Pega o cliente da lista para mostrar o nome no alerta
        Cliente clienteParaExcluir = clientes.get(position);

        new AlertDialog.Builder(requireContext())
                .setTitle("Confirmar Exclusão")
                .setMessage("Você tem certeza que deseja excluir o cliente " + clienteParaExcluir.getNomePrincipal() + "?")
                .setPositiveButton("Sim", (dialog, which) -> {
                    // Ação de exclusão
                    ClienteRepository.removeCliente(clienteParaExcluir);
                    Toast.makeText(getContext(), "Cliente removido.", Toast.LENGTH_SHORT).show();
                    // Recarregar a lista para atualizar a UI
                    carregarClientes();
                })
                .setNegativeButton("Não", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        clienteAdapter = null;
    }
}