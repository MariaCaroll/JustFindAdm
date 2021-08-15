package com.example.justfindadm.atividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.justfindadm.R;
import com.example.justfindadm.helper.ConfigurarFirebase;
import com.google.firebase.auth.FirebaseAuth;

public class ProfissionalActivity extends AppCompatActivity {
    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profissional);

        //configuracoes iniciais
        autenticacao = ConfigurarFirebase.getFirebaseAutenticacao();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //verifica  e altera os tipos de menu a ser exibido
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(autenticacao.getCurrentUser() == null)
        {
            // usuario deslogado
            menu.setGroupVisible(R.id.group_deslogado, true);
        }
        else
        {
            // usuario logado
            menu.setGroupVisible(R.id.group_logado, true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_cadastrar:
                startActivity(new Intent(getApplicationContext(), CadastroActivity.class));
                break;
            case R.id.menu_sair:
                autenticacao.signOut();
                invalidateOptionsMenu();
                break;
            case R.id.menu_profissionais:
                startActivity(new Intent(getApplicationContext(), ListarProfissionalActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}