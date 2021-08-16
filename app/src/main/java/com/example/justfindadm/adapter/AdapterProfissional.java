package com.example.justfindadm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import com.example.justfindadm.R;
import com.example.justfindadm.model.Profissional;

import java.util.List;

public class AdapterProfissional  extends RecyclerView.Adapter<AdapterProfissional.MyViewHolder>{

    private List<Profissional> profissionalList;
    private Context context;

    public AdapterProfissional(List<Profissional> profissionalList, Context context) {
        this.profissionalList = profissionalList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_profissional, parent, false);
        return new MyViewHolder( item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Profissional profissional = profissionalList.get(position);
        holder.fantasia.setText(profissional.getNomeFantasia());
        holder.descricao.setText(profissional.getApresentacao());
        holder.modo.setText(profissional.getModo());
        holder.valorMax.setText(profissional.getMaximo());
        holder.valorMin.setText(profissional.getMinimo());
        holder.contato.setText(profissional.getCelular());
        holder.whats.setText(profissional.getWhats());

        //retorna imagem
        List<String> urlFotos = profissional.getFotos();
        String urlCapa  = urlFotos.get(0);

        // carrega a imagem
        Picasso.get().load(urlCapa).into(holder.fotoAdapter);

    }

    @Override
    public int getItemCount() {
        return profissionalList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView fantasia;
        TextView descricao;
        TextView modo;
        TextView contato;
        TextView whats;
        TextView valorMin;
        TextView valorMax;
        ImageView fotoAdapter;
        public MyViewHolder(View itemView){
            super (itemView);
            fantasia = itemView.findViewById(R.id.txtAdapterNome);
            descricao = itemView.findViewById(R.id.edtAdpteApresetacao);
            modo = itemView.findViewById(R.id.txtAdapterModo);
            contato = itemView.findViewById(R.id.txtAdapeterCelular);
            whats = itemView.findViewById(R.id.txtAdpaterWhats);
            valorMax = itemView.findViewById(R.id.txtAdapterMaximo);
            valorMin = itemView.findViewById(R.id.txtAdapterMin);
            fotoAdapter = itemView.findViewById(R.id.imgAdapter);

        }
    }

}
