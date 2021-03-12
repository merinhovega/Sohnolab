package com.example.sohnolab;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class DetalleSoporte extends AppCompatActivity {

    private ImageView foto;
    private TextView solicitadoPor,fecha,tiempoEjecutado,casoMaximo, atendidoPor, tipoServicio, medio, plataforma, descripcion;
    private Bundle bundle;
    private Intent intent;
    private StorageReference storageReference;
    private Soporte s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_soporte);

        String solicitadoPorDB,tiempoEjecutadoDB,fechaDB,atendidoPorDB,tipoServicioDB, medioDB,plataformaDB, casoMaximoDB, descripcionDB,idDB;
        foto = findViewById(R.id.imgFotoDetalle);
        solicitadoPor = findViewById(R.id.lblSolicitadoPorDetalle);
        fecha = findViewById(R.id.lblFechaDetalle);
        tiempoEjecutado = findViewById(R.id.lblTiempoEjecutadoDetalle);
        atendidoPor = findViewById(R.id.lblAtendidoPorDetalle);
        tipoServicio = findViewById(R.id.lblTipoServicioDetalle);
        medio = findViewById(R.id.lblMedioDetalle);
        plataforma = findViewById(R.id.lblPlataformaDetalle);
        casoMaximo = findViewById(R.id.lblCasoMaximoDetalle);
        descripcion = findViewById(R.id.lblDescripcionDetalle);

        intent = getIntent();
        bundle = intent.getBundleExtra("datos");

        idDB = bundle.getString("id");
        solicitadoPorDB = bundle.getString("solicitadoPor");
        fechaDB = bundle.getString("fecha");
        tiempoEjecutadoDB = bundle.getString("tiempoEjecutado");
        atendidoPorDB = bundle.getString("atendidoPor");
        tipoServicioDB = bundle.getString("tipoServicio");
        medioDB = bundle.getString("medio");
        plataformaDB = bundle.getString("plataforma");
        casoMaximoDB = bundle.getString("casoMaximo");
        descripcionDB = bundle.getString("descripcion");

        s = new Soporte(solicitadoPorDB,fechaDB,tiempoEjecutadoDB,atendidoPorDB,tipoServicioDB,medioDB,plataformaDB,casoMaximoDB,descripcionDB,idDB);

        solicitadoPor.setText(solicitadoPorDB);
        fecha.setText(fechaDB);
        tiempoEjecutado.setText(tiempoEjecutadoDB);
        atendidoPor.setText(atendidoPorDB);
        tipoServicio.setText(tipoServicioDB);
        medio.setText(medioDB);
        plataforma.setText(plataformaDB);
        casoMaximo.setText(casoMaximoDB);
        descripcion.setText(descripcionDB);

        storageReference = FirebaseStorage.getInstance().getReference();
        storageReference.child(idDB).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(foto);
            }
        });
    }

    public void Eliminar(View v){
        String positivo, negativo;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.mensaje_eliminar);
        builder.setMessage(R.string.pregunta_eliminar_soporte);
        positivo = getString(R.string.mensaje_si);
        negativo = getString(R.string.mensaje_no);

        builder.setPositiveButton(positivo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                s.eliminar();
                onBackPressed();
            }
        });

        builder.setNegativeButton(negativo, null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onBackPressed(){
        finish();
        Intent intent = new Intent(DetalleSoporte.this, MainActivity.class);
    }
}