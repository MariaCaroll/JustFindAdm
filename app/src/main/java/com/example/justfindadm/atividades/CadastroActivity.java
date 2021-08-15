package com.example.justfindadm.atividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.justfindadm.R;
import com.example.justfindadm.helper.ConfigurarFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroActivity extends AppCompatActivity {
    // firebase
    private FirebaseAuth autenticacao;

    private Button btAcessar;
    private EditText campoEmail, campoSenha;
    private Switch tipoAcesso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        // cor da logo 3e37ca
        ininicalizar();

        autenticacao = ConfigurarFirebase.getFirebaseAutenticacao();
        //botao entrar
        btAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = campoEmail.getText().toString();
                String senha = campoSenha.getText().toString();
                if(!email.isEmpty())
                {
                    if(!senha.isEmpty())
                    {
                        //verificar o estado do swicht
                        if(tipoAcesso.isChecked())
                        {
                            //cadastrar
                            autenticacao.createUserWithEmailAndPassword(
                                    email, senha
                            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso!",
                                                Toast.LENGTH_SHORT).show();
                                        // direcionar para tela principal
                                    }
                                    else
                                    {
                                        String erroExcessao="";
                                        try
                                        {
                                            throw task.getException();
                                        }
                                        catch (FirebaseAuthWeakPasswordException e)
                                        {
                                            erroExcessao = "Digite uma senha mais forte!";
                                        }
                                        catch (FirebaseAuthInvalidCredentialsException e)
                                        {
                                            erroExcessao = "Por favor, digite um e-mail válido!";
                                        }
                                        catch (FirebaseAuthUserCollisionException e)
                                        {
                                            erroExcessao = "Esta conta já foi cadastrada!";
                                        }
                                        catch (Exception e)
                                        {
                                            erroExcessao = "ao cadastrar usuário: " + e.getMessage();
                                            e.printStackTrace();
                                        }
                                        Toast.makeText(CadastroActivity.this, "Erro: " + erroExcessao,
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else
                        {
                            //logar
                            autenticacao.signInWithEmailAndPassword(
                                    email, senha
                            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(CadastroActivity.this, "Logado Com Sucesso!",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                    else
                                    {
                                        Toast.makeText(CadastroActivity.this, "Erro ao fazer login: " +task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                    else
                    {
                        Toast.makeText(CadastroActivity.this, "Preencha a Senha!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(CadastroActivity.this, "Preencha o Email!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void ininicalizar()
    {
        campoEmail = findViewById(R.id.edtLogin);
        campoSenha = findViewById(R.id.edtSenha);
        tipoAcesso = findViewById(R.id.switchAcesso);
        btAcessar = findViewById(R.id.btnAcesso);
    }
}