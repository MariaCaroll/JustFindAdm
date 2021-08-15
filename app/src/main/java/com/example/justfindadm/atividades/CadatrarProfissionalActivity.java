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
import android.widget.Toast;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.example.justfindadm.R;
import com.example.justfindadm.helper.ConfigurarFirebase;
import com.example.justfindadm.helper.DateUtil;
import com.example.justfindadm.helper.Permissoes;
import com.example.justfindadm.model.Profissional;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
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
    private StorageReference storage;
    //permissoes
    private String[] permissoes = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA,};
    //fotos recuperadas
    private List<String> listaFotoRecuperada = new ArrayList<>();
    private List<String> listaURLFotos = new ArrayList<>();
    //Chamado a classe de profissionais
    private Profissional prof;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadatrar_profissional);
        storage = ConfigurarFirebase.getFirebaseStorage();
        //validar permissoes
        Permissoes.validarPermissoes(permissoes, this, 1);

        inicializar();
    }
    //Salvar a profissional
    private void salvarProfissional()
    {
        /**
         * Salvar imagem no Storage
         */
        for (int i = 0; i < listaFotoRecuperada.size(); i++)
        {
            String urlImagem = listaFotoRecuperada.get(i);
            int tamanhoLista = listaFotoRecuperada.size();
            salvarFotoStorage(urlImagem, tamanhoLista, i);
        }

    }
    private  void salvarFotoStorage(String urlString, final int totalFotos, int contador)
    {
        //cria nó no storage
        final StorageReference imagemProf = storage.child("imagens")
                .child("profissionais")
                .child(prof.getIdProfissional())
                .child("imagens" + contador);

        //fazer o uplode da img
        UploadTask uploadTask = imagemProf.putFile(Uri.parse(urlString));
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                return imagemProf.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri url = task.getResult();
                    listaURLFotos.add(url.toString());
                    if(totalFotos == listaURLFotos.size()) {
                        prof.setFotos(listaURLFotos);
                        prof.salvar();
                        //dialog.dismiss();
                        //dialog.hide();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                exibirMensagemErro("Falha ao fazer Upload");
            }
        });

    }

    public Profissional configuraProf()
    {
        prof = new Profissional();
        prof.setNome(campoNome.getText().toString());
        prof.setSobrenome(campoSobrenome.getText().toString());
        prof.setNomeFantasia(campoNomeFantasia.getText().toString());
        prof.setEmail(campoEmail.getText().toString());
        prof.setBairro(campoBairro.getText().toString());
        prof.setLogradouro(campoLogradouro.getText().toString());
        prof.setNumero(campoNumero.getText().toString());
        prof.setModo(campoModo.getSelectedItem().toString());
        prof.setApresentacao(campoApresentacao.getText().toString());
        prof.setMaximo(String.valueOf(campoValorMax.getRawValue()));
        prof.setMinimo(String.valueOf(campoValorMin.getRawValue()));
        prof.setCelular(campoCelular.getText().toString());
        prof.setWhats(campoWhats.getText().toString());
        prof.setCep(campoCep.getText().toString());
        prof.setData(campoDataAtual.getText().toString());

        //facilitar para o filtro
        String tipoProfissional = campoTipo.getSelectedItem().toString();
        prof.setTipo(tipoProfissional);
        String estado = campoEstado.getSelectedItem().toString();
        prof.setEstado(estado);
        String cidade = campoCidade.getText().toString();
        prof.setCidade(cidade);

        return prof;
    }
    public void validarDados(View view)
    {
        prof = configuraProf();
        if(listaFotoRecuperada.size() != 0)
        {
            if(!prof.getNome().isEmpty()){
                if(!prof.getSobrenome().isEmpty()) {
                    if(!prof.getNomeFantasia().isEmpty()){
                        if(!prof.getEmail().isEmpty()){
                            if(!prof.getEstado().isEmpty()){
                                if(!prof.getBairro().isEmpty()){
                                    if(!prof.getLogradouro().isEmpty()){
                                        if(!prof.getNumero().isEmpty()){
                                            if(!prof.getTipo().equals("selecione") ){
                                                if(!prof.getModo().equals("selecione")){
                                                    if(!prof.getApresentacao().isEmpty()){
                                                        if(!prof.getEstado().equals("selecione")){
                                                            if(!prof.getMaximo().isEmpty() && !prof.getMaximo().equals("0")){
                                                                if(!prof.getMinimo().isEmpty() && !prof.getMinimo().equals("0")){
                                                                    if(!prof.getCelular().isEmpty()){
                                                                        if(!prof.getWhats().isEmpty()){
                                                                            if(!prof.getCep().isEmpty()){
                                                                                //salvar a profissional
                                                                                salvarProfissional();
                                                                            }else{
                                                                                exibirMensagemErro("Preencha o Cep");
                                                                            }
                                                                        }else{
                                                                            exibirMensagemErro("Preencha o WhatsApp");
                                                                        }
                                                                    }else{
                                                                        exibirMensagemErro("Preenha o Celular");
                                                                    }
                                                                }else{
                                                                    exibirMensagemErro("Preencha o valor Minimo");
                                                                }
                                                            }else{
                                                                exibirMensagemErro("Preencha o valor Maxímo");
                                                            }
                                                        }else{
                                                            exibirMensagemErro("Selecione o estado");
                                                        }
                                                    }else{
                                                        exibirMensagemErro("Preencha a apresentação");
                                                    }
                                                }else{
                                                    exibirMensagemErro("Selecione o modo de atender");
                                                }
                                            }else{
                                                exibirMensagemErro("Selecione um o tipo de profissional");
                                            }
                                        }else{
                                            exibirMensagemErro("Preencha o Número da residência");
                                        }
                                    }else{
                                        exibirMensagemErro("Preencha a Endereço");
                                    }
                                }else{
                                    exibirMensagemErro("Preencha o Bairro");
                                }
                            }else{
                                exibirMensagemErro("Preencha a Cidade");
                            }
                        }else{
                            exibirMensagemErro("Preencha o E-mail");
                        }
                    }else{
                        exibirMensagemErro("Preencha o Nome Fantasia");
                    }
                }else{
                    exibirMensagemErro("Preencha o Sobrenome");
                }
            }else{
                exibirMensagemErro("Preencha o nome");
            }
        }
        else
        {
            exibirMensagemErro("Selecionar uma foto");
        }

    }
    // mensagem padrao de erro
    private void exibirMensagemErro(String texto)
    {
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
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

        //informando a data atual
       campoDataAtual.setText(DateUtil.dataAtual());

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