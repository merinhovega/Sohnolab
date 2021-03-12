package com.example.sohnolab;

import java.util.ArrayList;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Datos {
    private static String bd = "Soportes";
    private static ArrayList<Soporte> soportes = new ArrayList();
    private static DatabaseReference databaseReference = FirebaseDatabase.
            getInstance().getReference();
    private static StorageReference storageReference = FirebaseStorage.
            getInstance().getReference();

    public static String getId(){
        return databaseReference.push().getKey();
    }

    public static void guardar(Soporte p){
        databaseReference.child(bd).child(p.getId()).setValue(p);
    }
    public static void eliminar(Soporte p){
        databaseReference.child(bd).child(p.getId()).removeValue();
        storageReference.child(p.getId()).delete();
    }

    public static void setSoportes(ArrayList<Soporte> soportes){
        soportes = soportes;
    }

}
