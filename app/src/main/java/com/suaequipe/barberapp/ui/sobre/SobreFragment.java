package com.suaequipe.barberapp.ui.sobre;

import android.os.Bundle;
import android.text.method.LinkMovementMethod; // Necessário para links em TextViews se não usar autoLink
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.suaequipe.barberapp.databinding.FragmentSobreBinding;

public class SobreFragment extends Fragment {

    private FragmentSobreBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSobreBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Tornar os links de email e site clicáveis (autoLink já deve fazer isso)
        // binding.textViewEmail.setMovementMethod(LinkMovementMethod.getInstance());
        // binding.textViewSite.setMovementMethod(LinkMovementMethod.getInstance());

        // Para as redes sociais, se você não usar autoLink e quiser definir URLs:
        // Exemplo para Facebook (requereria que o TextView NÃO tivesse autoLink="web"):
        // binding.textViewFacebook.setOnClickListener(v -> {
        //     Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/barberapp"));
        //     startActivity(browserIntent);
        // });
        // Similar para Instagram e X.

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}