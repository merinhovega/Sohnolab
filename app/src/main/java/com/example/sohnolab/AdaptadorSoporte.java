package com.example.sohnolab;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
public class AdaptadorSoporte extends RecyclerView.Adapter<AdaptadorSoporte.SoporteViewHolder> {

    private ArrayList<Soporte> soportes;
    private OnSoporteClickListener clickListener;

    public AdaptadorSoporte(ArrayList<Soporte> soportes, OnSoporteClickListener clickListener) {
        this.soportes = soportes;
        this.clickListener = clickListener;
    }

    public SoporteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_soportes, parent,false);
        return new SoporteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SoporteViewHolder holder, int position) {
        Soporte s = soportes.get(position);
        StorageReference storageReference;
        storageReference = FirebaseStorage.getInstance().getReference();

        storageReference.child(s.getId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.foto);
            }
        });
        holder.descripcion.setText(s.getDescripcion());
        holder.solicitadoPor.setText(s.getSolicitadoPor());
        holder.fecha.setText(s.getFecha());
        holder.medio.setText(s.getMedio());
        holder.plataforma.setText(s.getPlataforma());


        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onSoporteClick(s);
            }
        });
    }

    @Override
    public int getItemCount() {
        return soportes.size();
    }

    public static class SoporteViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView foto;
        private TextView descripcion,solicitadoPor,fecha,atendidoPor,casoMaximo, tipoServicio, medio, plataforma;
        private View v;

        public SoporteViewHolder(@NonNull View itemView) {
            super(itemView);
            v = itemView;
            foto = v.findViewById(R.id.imgFotoItem);
            descripcion = v.findViewById(R.id.lblDescripcion);
            solicitadoPor = v.findViewById(R.id.lblSolicitadoPor);
            fecha = v.findViewById(R.id.lblFecha);
            medio = v.findViewById(R.id.lblMedio);
            plataforma = v.findViewById(R.id.lblPlataforma);
        }
    }

    public interface OnSoporteClickListener{
        void onSoporteClick(Soporte s);
    }
}
