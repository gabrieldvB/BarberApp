package com.suaequipe.barberapp;

import android.os.Bundle;
import android.util.Log; // <-- ADICIONE ESTA LINHA
import android.view.Menu;
import android.view.View;
import android.widget.Toast; // <-- ADICIONE ESTA LINHA

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.suaequipe.barberapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    // Se você estiver usando ViewBinding para MainActivity:
    // private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Se estiver usando ViewBinding:
        // binding = ActivityMainBinding.inflate(getLayoutInflater());
        // setContentView(binding.getRoot());
        // Toolbar toolbar = binding.appBarMain.toolbar; // Ou o caminho correto no seu binding
        // DrawerLayout drawer = binding.drawerLayout;
        // NavigationView navigationView = binding.navView;

        // Se NÃO estiver usando ViewBinding para MainActivity:
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Fim do bloco de findViewById

        setSupportActionBar(toolbar);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_cadastro, R.id.nav_clientes,
                R.id.nav_agendamentos, R.id.nav_historico_agendamentos, // ADICIONADO AQUI
                R.id.nav_programa_milhas, R.id.nav_missao, R.id.nav_sobre)
                .setOpenableLayout(drawer)
                .build();

        // O ID do NavHostFragment pode ser diferente se você não usou o template exato
        // ou se seu activity_main.xml / app_bar_main.xml for diferente.
        // Confirme o ID do seu FragmentContainerView que hospeda o nav_graph.
        // Frequentemente é R.id.nav_host_fragment_content_main ou similar.
        View navHostFragmentContainer = findViewById(R.id.nav_host_fragment_content_main);
        if (navHostFragmentContainer == null) {
            // Tente encontrar dentro de um possível layout incluído se estiver usando binding.appBarMain...
            // Exemplo, se o container estiver em app_bar_main.xml e activity_main.xml incluir app_bar_main:
            // ViewStub appBarMainViewStub = findViewById(R.id.app_bar_main_stub_id); // Se você tiver um ID para o include
            // if (appBarMainViewStub != null) {
            //    View inflatedView = appBarMainViewStub.inflate(); // Pode não ser a melhor forma
            //    navHostFragmentContainer = inflatedView.findViewById(R.id.nav_host_fragment_content_main);
            // }
            // Se estiver usando ViewBinding para MainActivity e o include tem ID appBarMain:
            // navHostFragmentContainer = binding.appBarMain.navHostFragmentContentMain; // Acessando via binding do include
            // É crucial que este ID esteja correto.
            // Se ainda não encontrar, pode haver um problema na estrutura do seu layout.
            Log.e("MainActivity", "nav_host_fragment_content_main NOT FOUND!");
            Toast.makeText(this, "Erro crítico: NavHostFragment não encontrado.", Toast.LENGTH_LONG).show();
            return; // Evitar crash se não encontrar
        }

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    // ... (onCreateOptionsMenu e onSupportNavigateUp como antes)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}