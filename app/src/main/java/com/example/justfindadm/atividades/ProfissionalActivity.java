package com.example.justfindadm.atividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.justfindadm.R;
import com.example.justfindadm.adapter.AdapterProfissional;
import com.example.justfindadm.helper.ConfigurarFirebase;
import com.example.justfindadm.model.Profissional;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfissionalActivity extends AppCompatActivity {
    private FirebaseAuth autenticacao;

    //Listar prof publico
    private RecyclerView recyclerViewProfPublico;
    private Button btCidade, btTipo, btModo;
    private AdapterProfissional adapterProfissional;
    private List<Profissional> lista = new ArrayList<>();
    private DatabaseReference profPublicoRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profissional);
        inicializar();
        //configuracoes iniciais
        autenticacao = ConfigurarFirebase.getFirebaseAutenticacao();
        profPublicoRef = ConfigurarFirebase.getFirebase()
                .child("profissionais_publico");

        // COnfiguando recyclerView
        recyclerViewProfPublico.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewProfPublico.setHasFixedSize(true);
        adapterProfissional = new AdapterProfissional(lista, this);
        recyclerViewProfPublico.setAdapter(adapterProfissional);

        recuperarProfPublico();
    }

    public void recuperarProfPublico()
    {
        lista.clear();
        profPublicoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot estados: snapshot.getChildren() ){
                    for(DataSnapshot cidades: estados.getChildren()){
                        for(DataSnapshot tipo: cidades.getChildren()){
                            for(DataSnapshot modo: tipo.getChildren()){
                                for(DataSnapshot prof: tipo.getChildren()){
                                    Profissional profissional = prof.getValue(Profissional.class);
                                    lista.add(profissional);
                                    Collections.reverse(lista);
                                    adapterProfissional.notifyDataSetChanged();
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

    public void inicializar()
    {
        recyclerViewProfPublico = findViewById(R.id.recyleListarProfPublico);
    }
}