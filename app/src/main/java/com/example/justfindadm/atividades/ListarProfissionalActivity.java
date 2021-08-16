package com.example.justfindadm.atividades;

import android.content.Intent;
import android.os.Bundle;

import com.example.justfindadm.adapter.AdapterProfissional;
import com.example.justfindadm.helper.Base64Custom;
import com.example.justfindadm.helper.ConfigurarFirebase;
import com.example.justfindadm.model.Profissional;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.example.justfindadm.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ListarProfissionalActivity extends AppCompatActivity {
    private List<Profissional> prof = new ArrayList<>();
    private RecyclerView recyclerProf;
    private AdapterProfissional adapterProfissional;
    private DatabaseReference profUsuarioRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_profissional);
        //Configuracoes iniciais

        profUsuarioRef = ConfigurarFirebase.getFirebase()
                .child("profissionais")
                .child(ConfigurarFirebase.getIdUsuario());

        inicializar();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CadatrarProfissionalActivity.class));
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Configurando o recyclerView
        recyclerProf.setLayoutManager(new LinearLayoutManager(this));
        recyclerProf.setHasFixedSize(true);
        adapterProfissional = new AdapterProfissional(prof, this);
        recyclerProf.setAdapter(adapterProfissional);

        //recupera as profissionais
        recuperaProf();
    }
    //listagem de anuncio
    private void recuperaProf()
    {
        profUsuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                prof.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    prof.add(ds.getValue(Profissional.class));

                }
                Collections.reverse(prof);
                adapterProfissional.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void inicializar()
    {
        recyclerProf = findViewById(R.id.recyclerProfissionais);
    }
}