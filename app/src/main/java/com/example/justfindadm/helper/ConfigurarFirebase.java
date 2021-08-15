package com.example.justfindadm.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ConfigurarFirebase {

    private static DatabaseReference referenciaFirebase;
    private static FirebaseAuth referenciaAutenticacao;
    private static StorageReference referenciaStorage;

    //Retonar a referencia do database
    public static DatabaseReference getFirebase()
    {
        if(referenciaFirebase == null)
        {
            referenciaFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return referenciaFirebase;
    }

    //retorna a instancia da autenticacao
    public static FirebaseAuth getFirebaseAutenticacao()
    {
        if(referenciaAutenticacao == null)
        {
            referenciaAutenticacao = FirebaseAuth.getInstance();
        }
        return referenciaAutenticacao;
    }

    //retorna a instancia do Storage
    public static StorageReference getFirebaseStorage()
    {
        if(referenciaFirebase == null)
        {
            referenciaStorage = FirebaseStorage.getInstance().getReference();
        }
        return referenciaStorage;
    }
}
