package com.example.agendacontactos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agendacontactos.adapters.contactoAdapter;
import com.example.agendacontactos.db.Contacto;
import com.example.agendacontactos.db.DbHelper;

import java.util.ArrayList;

public class tabla extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Contacto> contactos;
    DbHelper db;
    EditText txt_busqueda;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabla);
        txt_busqueda = findViewById(R.id.txt_busqueda);
        contactos = new ArrayList<Contacto>();
        db = new DbHelper(tabla.this);
        contactosAlmacenados();
        recyclerView = findViewById(R.id.recyview);

        contactoAdapter adapter = new contactoAdapter(tabla.this,contactos);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(tabla.this));

    }

    public void contactosAlmacenados(){
        Cursor cursor = db.readAllContactos();
        if(cursor.getCount() <= 0){
            Toast.makeText(this,"No hay contactos.",Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){

                contactos.add(new Contacto(

                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                        ));
            }
        }
    }

    public void busquedaContactos(View view) {

        Cursor cursor = db.readContacto(txt_busqueda.getText().toString().trim());

        if(cursor.getCount() <= 0){
            Toast.makeText(this,"No hay coincidencia.",Toast.LENGTH_SHORT).show();
        }else{
            contactos.clear();
            while (cursor.moveToNext()){

                contactos.add(new Contacto(

                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                ));
            }
        }
        contactoAdapter adapter = new contactoAdapter(tabla.this,contactos);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(tabla.this));
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Si hay una señal de que se debe actualizar la información, hacerlo
        if (getIntent().getStringExtra("update") != null) {
            contactos.clear();
            contactosAlmacenados();
            contactoAdapter adapter = new contactoAdapter(tabla.this,contactos);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(tabla.this));
        }
    }
    public void startAgregar(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}