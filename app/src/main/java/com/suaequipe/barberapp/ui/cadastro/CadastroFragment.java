package com.suaequipe.barberapp.ui.cadastro;

import android.os.Bundle;
// import android.util.Log; // Não estamos mais logando aqui, mas pode manter se quiser
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
// Removido: import android.widget.RadioButton;
// Removido: import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.suaequipe.barberapp.R;

import com.suaequipe.barberapp.databinding.FragmentCadastroBinding;

public class CadastroFragment extends Fragment {

    private FragmentCadastroBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCadastroBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // REMOVIDO: Listener para radioGroupTipoPagamento e lógica de visibilidade de layoutDetalhesCartao

        binding.btnProsseguirCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coletarEProcessarDados();
            }
        });
    }

    private void coletarEProcessarDados() {
        // Coletar dados dos campos atualizados
        String nomeCompleto = binding.editTextNomeCompleto.getText().toString().trim();
        String celular = binding.editTextCelular.getText().toString().trim();
        String email = binding.editTextEmail.getText().toString().trim();
        boolean aceitouTermos = binding.checkBoxTermos.isChecked();

        // REMOVIDO: Coleta de tipoPagamento e detalhes do cartão

        // Validação simples
        if (nomeCompleto.isEmpty() || celular.isEmpty() || email.isEmpty()) {
            Toast.makeText(getContext(), "Nome, Celular e E-mail são obrigatórios!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!aceitouTermos) {
            Toast.makeText(getContext(), "Você deve aceitar os termos e condições!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Criar o Bundle para passar os dados atualizados
        Bundle bundle = new Bundle();
        bundle.putString("nomeCompleto", nomeCompleto);
        bundle.putString("celular", celular);
        bundle.putString("email", email);
        bundle.putBoolean("aceitouTermos", aceitouTermos);

        // REMOVIDO: Adição de dados de pagamento ao bundle

        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(R.id.nav_confirmacao_cadastro, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}