package com.suaequipe.barberapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.suaequipe.barberapp.R; // Importante para os IDs de navegação
import com.suaequipe.barberapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final NavController navController = Navigation.findNavController(view);

        binding.btnMainCadastro.setOnClickListener(v -> navController.navigate(R.id.nav_cadastro));
        binding.btnMainClientes.setOnClickListener(v -> navController.navigate(R.id.nav_clientes));
        binding.btnMainAgendamentos.setOnClickListener(v -> navController.navigate(R.id.nav_agendamentos));
        binding.btnMainProgramaMilhas.setOnClickListener(v -> navController.navigate(R.id.nav_programa_milhas));
        binding.btnMainMissao.setOnClickListener(v -> navController.navigate(R.id.nav_missao));
        binding.btnMainSobre.setOnClickListener(v -> navController.navigate(R.id.nav_sobre));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}