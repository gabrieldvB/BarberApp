package com.suaequipe.barberapp.ui.clientes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager; // Importar LinearLayoutManager
import com.suaequipe.barberapp.Cliente; // Importar sua classe Cliente
import com.suaequipe.barberapp.ClienteRepository; // Importar seu ClienteRepository
import com.suaequipe.barberapp.databinding.FragmentClientesBinding; // Usaremos ViewBinding
import java.util.List;

public class ClientesFragment extends Fragment {

    private FragmentClientesBinding binding;
    private ClienteAdapter clienteAdapter; // Variável para o Adapter

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentClientesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setupRecyclerView();

        return root;
    }

    // É uma boa prática atualizar a lista quando o Fragment se torna visível novamente,
    // caso novos clientes tenham sido adicionados enquanto esta tela não estava visível.
    @Override
    public void onResume() {
        super.onResume();
        carregarClientes();
    }

    private void setupRecyclerView() {
        // Configura o LayoutManager para o RecyclerView (vertical, como uma lista)
        binding.recyclerViewClientes.setLayoutManager(new LinearLayoutManager(getContext()));
        // Não criamos o adapter aqui ainda, pois a lista será carregada em onResume/carregarClientes
    }

    private void carregarClientes() {
        List<Cliente> clientes = ClienteRepository.getListaClientes();

        if (clientes.isEmpty()) {
            binding.recyclerViewClientes.setVisibility(View.GONE);
            binding.textViewListaVazia.setVisibility(View.VISIBLE);
        } else {
            binding.recyclerViewClientes.setVisibility(View.VISIBLE);
            binding.textViewListaVazia.setVisibility(View.GONE);

            // Se o adapter ainda não foi criado, crie-o.
            // Se já existe, atualize a lista dele.
            if (clienteAdapter == null) {
                clienteAdapter = new ClienteAdapter(clientes);
                binding.recyclerViewClientes.setAdapter(clienteAdapter);
            } else {
                clienteAdapter.atualizarLista(clientes);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        clienteAdapter = null; // Limpar referência do adapter também
    }
}