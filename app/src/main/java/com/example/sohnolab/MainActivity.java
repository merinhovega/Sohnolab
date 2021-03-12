package com.example.sohnolab;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdaptadorSoporte.OnSoporteClickListener {
    private RecyclerView lista;
    private AdaptadorSoporte adapter;
    private LinearLayoutManager llm;
    private ArrayList<Soporte> soportes;
    private DatabaseReference databaseReference;
    private String bd = "Soportes";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        lista = findViewById(R.id.lstSoportes);

        soportes = new ArrayList<>();
        llm = new LinearLayoutManager(this);
        adapter = new AdaptadorSoporte(soportes,this::onSoporteClick);
        llm.setOrientation(RecyclerView.VERTICAL);

        lista.setLayoutManager(llm);
        lista.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(bd).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                soportes.clear();
                if(snapshot.exists()){
                    for(DataSnapshot snap: snapshot.getChildren()){
                        Soporte s = snap.getValue(Soporte.class);
                        soportes.add(s);
                    }
                }
               adapter.notifyDataSetChanged();
               Datos.setSoportes(soportes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void crear(View v){
        Intent intent;
        intent = new Intent(MainActivity.this, CrearSoportes.class);
        startActivity(intent);
    }

    public void onSoporteClick(Soporte s) {
        Intent intent;
        Bundle bundle;
        bundle = new Bundle();
        bundle.putString("id", s.getId());
        bundle.putString("solicitadoPor", s.getSolicitadoPor());
        bundle.putString("fecha", s.getFecha());
        bundle.putString("tiempoEjecutado", s.getTiempoEjecutado());
        bundle.putString("atendidoPor", s.getAtendidoPor());
        bundle.putString("casoMaximo", s.getCasoMaximo());
        bundle.putString("tipoServicio", s.getTipoServicio());
        bundle.putString("medio", s.getMedio());
        bundle.putString("plataforma", s.getPlataforma());
        bundle.putString("descripcion", s.getDescripcion());

        intent = new Intent(MainActivity.this, DetalleSoporte.class);
        intent.putExtra("datos", bundle);
        startActivity(intent);
    }

}