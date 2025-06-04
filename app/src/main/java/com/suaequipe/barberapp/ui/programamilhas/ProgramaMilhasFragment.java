package com.suaequipe.barberapp.ui.programamilhas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
// Removido: import android.widget.Spinner; // Não é mais necessário importar diretamente se usar binding.spinnerClientesMilhas
// Removido: import android.widget.TextView; // Não é mais necessário importar diretamente se usar binding.textViewSaldoMilhasCliente
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.suaequipe.barberapp.Cliente;
import com.suaequipe.barberapp.ClienteRepository;
import com.suaequipe.barberapp.databinding.FragmentProgramaMilhasBinding; // Importar o binding
import java.util.ArrayList;
import java.util.List;

public class ProgramaMilhasFragment extends Fragment {

    private FragmentProgramaMilhasBinding binding; // Usar a variável de binding
    private List<Cliente> listaDeClientesCadastrados;
    private ArrayAdapter<String> spinnerAdapterClientesMilhas;
    private List<String> nomesClientesParaSpinnerMilhas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar o layout usando ViewBinding
        binding = FragmentProgramaMilhasBinding.inflate(inflater, container, false);
        return binding.getRoot(); // Retornar a view raiz do binding
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupClienteSpinnerMilhas(); // Configurar o adapter e o spinner

        binding.spinnerClientesMilhas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View selectedItemView, int position, long id) {
                // Verificar se a lista de clientes carregada não é nula e se a posição é válida
                // A posição 'position' aqui é o índice na lista 'nomesClientesParaSpinnerMilhas'
                // que corresponde ao índice em 'listaDeClientesCadastrados' se não houver o item "Nenhum cliente..."

                if (listaDeClientesCadastrados != null && !listaDeClientesCadastrados.isEmpty()) {
                    // Se o spinner está habilitado, significa que há clientes reais na listaDeClientesCadastrados
                    if (binding.spinnerClientesMilhas.isEnabled() && position < listaDeClientesCadastrados.size()) {
                        Cliente clienteSelecionado = listaDeClientesCadastrados.get(position);
                        binding.textViewSaldoMilhasCliente.setText(String.valueOf(clienteSelecionado.getPontosMilhas()));
                    } else {
                        // Isso pode acontecer se o spinner tiver apenas o item "Nenhum cliente..."
                        // ou alguma inconsistência de índice, reseta para 0.
                        binding.textViewSaldoMilhasCliente.setText("0");
                    }
                } else {
                    // Se listaDeClientesCadastrados é nula ou vazia, não há pontos para mostrar.
                    binding.textViewSaldoMilhasCliente.setText("0");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                binding.textViewSaldoMilhasCliente.setText("0");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // Carregar/atualizar os clientes no spinner sempre que o fragmento ficar visível
        carregarClientesNoSpinnerMilhas();
    }

    private void setupClienteSpinnerMilhas() {
        nomesClientesParaSpinnerMilhas = new ArrayList<>();
        // Usar requireContext() para o ArrayAdapter para garantir que o contexto não é nulo
        spinnerAdapterClientesMilhas = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, nomesClientesParaSpinnerMilhas);
        spinnerAdapterClientesMilhas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerClientesMilhas.setAdapter(spinnerAdapterClientesMilhas);
    }

    private void carregarClientesNoSpinnerMilhas() {
        listaDeClientesCadastrados = ClienteRepository.getListaClientes(); // Pega a lista de objetos Cliente
        nomesClientesParaSpinnerMilhas.clear(); // Limpa a lista de strings que vai para o adapter

        boolean isSpinnerEnabled;
        if (listaDeClientesCadastrados.isEmpty()) {
            nomesClientesParaSpinnerMilhas.add("Nenhum cliente cadastrado");
            isSpinnerEnabled = false;
            binding.textViewSaldoMilhasCliente.setText("0");
        } else {
            for (Cliente cliente : listaDeClientesCadastrados) {
                String displayCliente = cliente.getNomePrincipal();
                if (cliente.getEmail() != null && !cliente.getEmail().isEmpty()) {
                    displayCliente += " (" + cliente.getEmail() + ")";
                } else if (cliente.getCelular() != null && !cliente.getCelular().isEmpty()) {
                    displayCliente += " (" + cliente.getCelular() + ")";
                }
                nomesClientesParaSpinnerMilhas.add(displayCliente);
            }
            isSpinnerEnabled = true;
        }

        binding.spinnerClientesMilhas.setEnabled(isSpinnerEnabled);
        spinnerAdapterClientesMilhas.notifyDataSetChanged(); // Notifica o adapter sobre a mudança nos dados

        // Após notificar o adapter, se a lista de clientes reais não estiver vazia,
        // seleciona o primeiro item e o listener onItemSelected atualizará os pontos.
        if (!listaDeClientesCadastrados.isEmpty() && isSpinnerEnabled) {
            // Garante que a seleção seja válida após popular o spinner.
            // Se o spinner já tinha uma seleção e a lista mudou, onItemSelected pode não ser chamado
            // se a posição selecionada ainda for válida. Para forçar a atualização dos pontos
            // para o item atualmente selecionado (ou o primeiro se a seleção for resetada):
            int currentSelection = binding.spinnerClientesMilhas.getSelectedItemPosition();
            if (currentSelection == AdapterView.INVALID_POSITION || currentSelection >= listaDeClientesCadastrados.size()) {
                binding.spinnerClientesMilhas.setSelection(0); // Dispara onItemSelected para a posição 0
            } else {
                // Se a seleção atual ainda é válida, atualiza os pontos para essa seleção
                Cliente clienteAtual = listaDeClientesCadastrados.get(currentSelection);
                binding.textViewSaldoMilhasCliente.setText(String.valueOf(clienteAtual.getPontosMilhas()));
            }
        } else if (listaDeClientesCadastrados.isEmpty()) {
            // Se a lista está vazia (apenas "Nenhum cliente cadastrado" no spinner), garante que o saldo é 0
            binding.textViewSaldoMilhasCliente.setText("0");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Importante para evitar memory leaks com ViewBinding
    }
}