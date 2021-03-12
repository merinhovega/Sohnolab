package com.example.sohnolab;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class CrearSoportes extends AppCompatActivity {

    private EditText solicitadoPor,fecha,tiempoEjecutado,casoMaximo, descripcion;
    private Spinner comboAtendidoPor, comboTipoServicio, comboMedio, comboPlataforma;
    private ImageView foto;
    private Uri uri;
    private StorageReference storageReference;
    private ArrayAdapter<String> adapterAtendidoPor, adapterTipoServicio, adapterMedio,adapterPlataforma;
    Calendar c= Calendar.getInstance();
    private int mAnoIni, mMesIni, mDiaIni, sAnoIni, sMesIni, sDiaIni;
    static final int DATE_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_crear_soportes);
        sAnoIni = c.get(Calendar.YEAR);
        sMesIni = c.get(Calendar.MONTH);
        sDiaIni = c.get(Calendar.DAY_OF_MONTH);

        solicitadoPor = findViewById(R.id.txtSolicitadoPor);
        fecha = findViewById(R.id.txtFecha);

        fecha.setOnClickListener(v -> {
            showDialog(DATE_ID);
        });

        tiempoEjecutado = findViewById(R.id.txtTiempoEjecutado);
        comboAtendidoPor = findViewById(R.id.cmbAtendidoPor);
        comboTipoServicio = findViewById(R.id.cmbTipoServicio);
        comboMedio = findViewById(R.id.cmbMedio);
        comboPlataforma = findViewById(R.id.cmbPlataforma);
        casoMaximo = findViewById(R.id.txtCasoMaximo);
        descripcion = findViewById(R.id.txtDescripcion);
        foto = findViewById(R.id.imgFotoSeleccionada);
        storageReference = FirebaseStorage.getInstance().getReference();

        String[] atendidoPor = getResources().getStringArray(R.array.atendidoPor);
        String[] tipoServicio = getResources().getStringArray(R.array.tipoServicio);
        String[] medio = getResources().getStringArray(R.array.medio);
        String[] plataforma = getResources().getStringArray(R.array.plataforma);

        adapterAtendidoPor = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, atendidoPor);
        comboAtendidoPor.setAdapter(adapterAtendidoPor);

        adapterTipoServicio = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tipoServicio);
        comboTipoServicio.setAdapter(adapterTipoServicio);

        adapterMedio = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, medio);
        comboMedio.setAdapter(adapterMedio);

        adapterPlataforma = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, plataforma);
        comboPlataforma.setAdapter(adapterPlataforma);
    }

    public void guardar(View v){
        String solicitadoPorGuardar,fechaGuardar,tiempoEjecutadoGuardar,atendidoPorGuardar, tipoServicioGuardar, medioGuardar, plataformaGuardar, casoMaximoGuardar, descripcionGuardar, id;
        Soporte s;
        InputMethodManager imp;

        imp = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imp.hideSoftInputFromWindow(tiempoEjecutado.getWindowToken(),0);

        if(validar()) {
            solicitadoPorGuardar = solicitadoPor.getText().toString();
            fechaGuardar = fecha.getText().toString();
            tiempoEjecutadoGuardar = tiempoEjecutado.getText().toString();
            atendidoPorGuardar = comboAtendidoPor.getSelectedItem().toString();
            tipoServicioGuardar = comboTipoServicio.getSelectedItem().toString();
            medioGuardar = comboMedio.getSelectedItem().toString();
            plataformaGuardar = comboPlataforma.getSelectedItem().toString();
            casoMaximoGuardar = casoMaximo.getText().toString();
            descripcionGuardar = descripcion.getText().toString();
            id = Datos.getId();
            s = new Soporte(solicitadoPorGuardar,fechaGuardar,tiempoEjecutadoGuardar,atendidoPorGuardar, tipoServicioGuardar, medioGuardar, plataformaGuardar,casoMaximoGuardar, descripcionGuardar, id);
            s.guardar();
            Snackbar.make(v, R.string.mensaje_guardado_exitoso, Snackbar.LENGTH_LONG).show();
            limpiar();
            subir_foto(id);
            uri = null;
        }

    }

    public void subir_foto(String id){
        StorageReference child = storageReference.child(id);
        UploadTask uploadTask = child.putFile(uri);
    }

    public void limpiar(View v){
        limpiar();
    }

    public void limpiar(){
        solicitadoPor.setText("");
        fecha.setText("");
        tiempoEjecutado.setText("");
        comboAtendidoPor.setSelection(0);
        comboTipoServicio.setSelection(0);
        comboMedio.setSelection(0);
        comboPlataforma.setSelection(0);
        casoMaximo.setText("");
        descripcion.setText("");
        descripcion.requestFocus();
        foto.setImageResource(android.R.drawable.ic_menu_gallery);
    }

    public void seleccionarFoto(View v){
        Intent in = new Intent();
        in.setType("image/*");
        in.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(in,getString(R.string.mensaje_seleccion_foto)),1);
    }

    public boolean validar(){

        if(uri == null){
            Snackbar.make(solicitadoPor,R.string.mensaje_seleccion_foto,Snackbar.LENGTH_LONG).show();
            return false;
        }

        if (descripcion.getText().toString().isEmpty()){
            descripcion.setError(getString(R.string.error_ingreso_descripcion));
            descripcion.requestFocus();
            return false;
        }

        if(solicitadoPor.getText().toString().isEmpty()){
            solicitadoPor.setError(getString(R.string.error_ingreso_solicitadoPor));
            solicitadoPor.requestFocus();
            return false;
        }

        if (fecha.getText().toString().isEmpty()){
            fecha.setError(getString(R.string.error_ingreso_fecha));
            fecha.requestFocus();
            return false;
        }

        if (tiempoEjecutado.getText().toString().isEmpty()){
            tiempoEjecutado.setError(getString(R.string.error_ingreso_tiempoEjecutado));
            tiempoEjecutado.requestFocus();
            return false;
        }

        if (comboAtendidoPor.getSelectedItemPosition() == 0){
            TextView errorText=(TextView)comboAtendidoPor.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText(getString(R.string.error_ingreso_atendidoPor));
            comboAtendidoPor.requestFocus();
            return false;
        }

        if (comboTipoServicio.getSelectedItemPosition() == 0){
            TextView errorText=(TextView)comboTipoServicio.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText(getString(R.string.error_ingreso_tipoServicio));
            comboTipoServicio.requestFocus();
            return false;
        }

        if (comboPlataforma.getSelectedItemPosition() == 0){
            TextView errorText=(TextView)comboPlataforma.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText(getString(R.string.error_ingreso_plataforma));
            comboPlataforma.requestFocus();
            return false;
        }

        if (comboMedio.getSelectedItemPosition() == 0){
            TextView errorText=(TextView)comboMedio.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText(getString(R.string.error_ingreso_medio));
            comboMedio.requestFocus();
            return false;
        }

        if (comboMedio.getSelectedItemPosition() == 2 && casoMaximo.getText().toString().isEmpty() ){
                casoMaximo.setError(getString(R.string.error_ingreso_casoMaximo));
                casoMaximo.requestFocus();
                return false;
        }
        return true;
    }

    public void onBackPressed(){
        finish();
        Intent i = new Intent(CrearSoportes.this, MainActivity.class);
        startActivity(i);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            uri = data.getData();
            if(uri != null){
                foto.setImageURI(uri);
            }
        }
    }

    private void colocar_fecha() {
        fecha.setText(mDiaIni + "/" + (mMesIni + 1) + "/" + mAnoIni+" ");
    }

    private DatePickerDialog.OnDateSetListener nDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int ano, int mesAno, int diaMes) {
                    mAnoIni = ano;
                    mMesIni = mesAno;
                    mDiaIni = diaMes;
                    colocar_fecha();

                }
    };

    protected Dialog onCreateDialog(int id){
        switch (id){
            case DATE_ID:
                return new DatePickerDialog(this, nDateSetListener, sAnoIni, sMesIni, sDiaIni);
        }
        return  null;
    }

}