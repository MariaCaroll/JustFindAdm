package com.example.justfindadm.atividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.example.justfindadm.R;
import com.example.justfindadm.helper.Permissoes;
import com.santalu.maskara.widget.MaskEditText;

import java.util.Locale;

public class CadatrarProfissionalActivity extends AppCompatActivity {

    //Componentes da Tela
    private ImageView imgProf;
    private EditText campoNome, campoSobrenome, campoNomeFantasia, campoEmail, campoCidade, campoBairro;
    private EditText campoLogradouro, campoComplemento, campoNumero, campoApresentacao;
    private Spinner campoTipo, campoModo, campoEstado;
    private CurrencyEditText campoValorMin, campoValorMax;
    private MaskEditText campoCelular, campoWhats, campoCep;
    private Button btnCadastrar;

    //permissoes
    private String[] permissoes = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
        };

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
        btnCadastrar = findViewById(R.id.btnCadastrarProfissional);

        //Mascara da Moeda RS
        Locale locale = new Locale("pt", "BR");
        campoValorMin.setLocale(locale);
        campoValorMax.setLocale(locale);

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
        builder.setMessage("Para ultizar o app e necessário aceitar as permissões!");
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