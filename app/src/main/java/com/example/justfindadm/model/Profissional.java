package com.example.justfindadm.model;

import com.example.justfindadm.helper.Base64Custom;
import com.example.justfindadm.helper.ConfigurarFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.List;
import java.util.zip.CheckedOutputStream;

public class Profissional {

    private String idProfissional;
    private String nome ;
    private String sobrenome;
    private String nomeFantasia;
    private String email;
    private String cidade;
    private String bairro;
    private String logradouro;
    private String numero;
    private String tipo;
    private String modo;
    private String apresentacao;
    private String estado;
    private String maximo;
    private String minimo;
    private String celular;
    private String whats;
    private String cep;
    private String data;
    private List<String> fotos;

    //gerar o idendificador da profissional
    public Profissional() {
        DatabaseReference profRef = ConfigurarFirebase.getFirebase()
                .child("profissionais");
        setIdProfissional(profRef.push().getKey());
    }
    public void salvar(){

        String idUsuario = ConfigurarFirebase.getIdUsuario();
        DatabaseReference firebase = ConfigurarFirebase.getFirebase();
        firebase.child("profissionais")
                .child(idUsuario)
                .child(tipo)
                .push()
                .setValue(this);
        salvarProfPublico();
    }
    public void salvarProfPublico(){

        FirebaseAuth autenticacao = ConfigurarFirebase.getFirebaseAutenticacao();

        DatabaseReference firebase = ConfigurarFirebase.getFirebase();
        firebase.child("profissionais_publico")
                .child(getEstado())
                .child(getCidade())
                .child(getTipo())
                .child(getModo())
                .child(getIdProfissional())
                .push()
                .setValue(this);
    }

    public String getIdProfissional() {
        return idProfissional;
    }

    public void setIdProfissional(String idProfissional) {
        this.idProfissional = idProfissional;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public String getApresentacao() {
        return apresentacao;
    }

    public void setApresentacao(String apresentacao) {
        this.apresentacao = apresentacao;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMaximo() {
        return maximo;
    }

    public void setMaximo(String maximo) {
        this.maximo = maximo;
    }

    public String getMinimo() {
        return minimo;
    }

    public void setMinimo(String minimo) {
        this.minimo = minimo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getWhats() {
        return whats;
    }

    public void setWhats(String whats) {
        this.whats = whats;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }
}
