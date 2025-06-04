package com.suaequipe.barberapp.ui.missao;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.suaequipe.barberapp.databinding.FragmentMissaoBinding; // Gerado a partir de fragment_missao.xml

public class MissaoFragment extends Fragment {

    private FragmentMissaoBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMissaoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // Se você quiser interagir com os TextViews via código, pode fazer aqui.
        // Ex: binding.textViewMissaoTitulo.setText("Nossa Grande Missão");
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}