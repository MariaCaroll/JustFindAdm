package com.example.justfindadm.atividades;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.example.justfindadm.R;
import com.example.justfindadm.helper.Permissoes;
import com.santalu.maskara.widget.MaskEditText;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CadatrarProfissionalActivity extends AppCompatActivity implements View.OnClickListener{

    //Componentes da Tela
    private ImageView imgProf;
    private EditText campoNome, campoSobrenome, campoNomeFantasia, campoEmail, campoCidade, campoBairro;
    private EditText campoLogradouro, campoComplemento, campoNumero, campoApresentacao, campoDataAtual;
    private Spinner campoTipo, campoModo, campoEstado;
    private CurrencyEditText campoValorMin, campoValorMax;
    private MaskEditText campoCelular, campoWhats, campoCep;
    private Button btnCadastrar;

    //permissoes
    private String[] permissoes = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
        };

    //fotos recuperadas
    private List<String> listaFotoRecuperada = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadatrar_profissional);
        //validar permissoes
        Permissoes.validarPermissoes(permissoes, this, 1);

        inicializar();
    }
    //Pega o que foi digiado e salva no firebase
    private void salvarProfissional(View view)
    {



        long maximo = campoValorMax.getRawValue();
        long minimo = campoValorMin.getRawValue();
        String celular = campoCelular.getText().toString();
        String whats = campoWhats.getText().toString();
    }

    //click da imagem
    @Override
    public void onClick(View v) {
        Log.d("onClick", "onClick: " + v.getId());
        switch (v.getId()) {
            case R.id.imgCadastro:
                Log.d("onClick", "onClick: ");
                escolherImage(1);
                break;
        }
    }
    //metodo para selecionar imagem
    public void escolherImage(int requestCode)
    {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, requestCode);
    }

    //gerar o caminho da imagem
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK)
        {
            //recupera a imagem
            Uri imagemSelecionada = data.getData();
            String caminhoImagem = imagemSelecionada.toString();

            //configura a imagem no ImageView
            if(requestCode == 1) {
                imgProf.setImageURI(imagemSelecionada);
            }
            listaFotoRecuperada.add(caminhoImagem);
        }
    }

    private void inicializar()
    {
        //identifica os camponentes da tela
        imgProf = findViewById(R.id.imgCadastro);
        campoNome = findViewById(R.id.edtCriarNome);
        campoSobrenome = findViewById(R.id.edtCriarSobrenome);
        campoNomeFantasia = findViewById(R.id.edtCriaNomeFantasia);
        campoEmail = findViewById(R.id.edtCriarEmail);
        campoCidade = findViewById(R.id.edtCriarCidade);
        campoBairro = findViewById(R.id.edtCriarBairro);
        campoLogradouro = findViewById(R.id.edtCriarLogradouro);
        campoNumero = findViewById(R.id.edtCriarNumero);
        campoTipo = findViewById(R.id.spnCriarTipo);
        campoModo = findViewById(R.id.spnCriaModoAtender);
        campoApresentacao = findViewById(R.id.edtCiarApresentacao);
        campoEstado = findViewById(R.id.spnCriarUF);
        campoValorMax = findViewById(R.id.edtCriarValorMaximo);
        campoValorMin = findViewById(R.id.edtCriarValorMinimo);
        campoCelular = findViewById(R.id.edtCriarCelular);
        campoWhats = findViewById(R.id.edtCriarWhats);
        campoCep = findViewById(R.id.edtCriarCep);
        campoDataAtual = findViewById(R.id.edtDate);
        btnCadastrar = findViewById(R.id.btnCadastrarProfissional);

        //setando a imagem
        imgProf.setOnClickListener(this);

        //Mascara da Moeda RS
        Locale locale = new Locale("pt", "BR");
        campoValorMin.setLocale(locale);
        campoValorMax.setLocale(locale);

        //informado a data atual
       // campoDataAtual.setText(DateUtils.);

    }

    //verificar se o usuario autorizou o acesso a galeria
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for( int permissaoResultado : grantResults)
        {
            if(permissaoResultado == PackageManager.PERMISSION_DENIED)
            {
                alertarValidarPermissao();
            }
        }
    }

    private void alertarValidarPermissao()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões negadas");
        builder.setMessage("Para ultizar o app e necessáriso aceitar as permissões!");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}