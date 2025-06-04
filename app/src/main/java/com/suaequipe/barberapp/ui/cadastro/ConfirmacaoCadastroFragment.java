package com.suaequipe.barberapp.ui.cadastro;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.suaequipe.barberapp.Cliente;
import com.suaequipe.barberapp.ClienteRepository;
import com.suaequipe.barberapp.R;
import com.suaequipe.barberapp.databinding.FragmentConfirmacaoCadastroBinding;

public class ConfirmacaoCadastroFragment extends Fragment {

    private FragmentConfirmacaoCadastroBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentConfirmacaoCadastroBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            binding.textViewConfirmNomeCompleto.setText(arguments.getString("nomeCompleto", ""));
            binding.textViewConfirmCelular.setText(arguments.getString("celular", ""));
            binding.textViewConfirmEmail.setText(arguments.getString("email", ""));
            // REMOVIDA LÓGICA DE EXIBIÇÃO DE TIPO DE PAGAMENTO E DETALHES DO CARTÃO
            // O layout layoutConfirmDetalhesCartao foi removido do XML, então não precisamos mais mexer na visibilidade dele aqui.
            binding.textViewConfirmTermos.setText(arguments.getBoolean("aceitouTermos", false) ? "Aceito" : "Não Aceito");
        }

        NavController navController = Navigation.findNavController(view);

        binding.btnOkConfirmacao.setOnClickListener(v -> {
            if (arguments != null) {
                // Criar objeto Cliente com os dados atualizados do Bundle
                Cliente novoCliente = new Cliente(
                        arguments.getString("nomeCompleto", ""),
                        arguments.getString("celular", ""),
                        arguments.getString("email", ""),
                        arguments.getBoolean("aceitouTermos", false)
                );
                // REMOVIDOS CAMPOS DE PAGAMENTO DA CRIAÇÃO DO OBJETO Cliente

                ClienteRepository.addCliente(novoCliente);
                Log.d("ConfirmacaoCadastro", "Cliente adicionado: " + novoCliente.toString());
                Log.d("ConfirmacaoCadastro", "Total de clientes na sessão: " + ClienteRepository.getListaClientes().size());
            }

            Toast.makeText(getContext(), "Dados Corretos! Cliente cadastrado na sessão.", Toast.LENGTH_LONG).show();

            // Navegar de volta para a tela inicial após o cadastro bem-sucedido
            // Limpa a pilha de volta até nav_home (não inclusivo), efetivamente finalizando o fluxo de cadastro.
            if (navController.getGraph().findNode(R.id.nav_home) != null) {
                navController.popBackStack(R.id.nav_home, false);
            } else {
                // Fallback se nav_home não estiver na pilha de alguma forma (improvável com NavDrawer)
                // ou se o gráfico for diferente. Neste caso, apenas pop este fragmento.
                navController.popBackStack();
            }
        });

        binding.btnCancelarConfirmacao.setOnClickListener(v -> {
            navController.popBackStack(); // Volta para o CadastroFragment
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}