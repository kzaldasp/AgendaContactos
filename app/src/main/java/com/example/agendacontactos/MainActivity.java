package com.example.agendacontactos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agendacontactos.db.Contacto;
import com.example.agendacontactos.db.DbHelper;

public class MainActivity extends AppCompatActivity {

    EditText txt_nombre, txt_telefono,txt_direccion,txt_correo;
    Button btn_delete;
    Boolean modo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_nombre = findViewById(R.id.txt_nombre);
        txt_telefono = findViewById(R.id.txt_telefono);
        txt_direccion = findViewById(R.id.txt_direccion);
        txt_correo = findViewById(R.id.txt_correo);
        btn_delete = findViewById(R.id.btn_delete);
        modo = true;
        btn_delete.setEnabled(false);
        if (getIntent().hasExtra("id")){
            btn_delete.setEnabled(true);
            Bundle bundle = getIntent().getExtras();
            txt_nombre.setText(bundle.getString("nombre"));
            txt_telefono.setText(bundle.getString("telefono"));
            txt_direccion.setText(bundle.getString("direccion"));
            txt_correo.setText(bundle.getString("correo"));
            modo = false;
        }

    }

    public void onClick_AddContacto(View view){
        DbHelper db = new DbHelper(MainActivity.this);
        Contacto contacto= new Contacto(txt_nombre.getText().toString(),
                txt_direccion.getText().toString(),txt_telefono.getText().toString(),
                txt_correo.getText().toString());

        long result;
        String agreActu;
        if (modo){
             result = db.addContacto(contacto);
            agreActu = "Agregado";
        }else{
            contacto.setId(getIntent().getExtras().getString("id"));
             result = db.updateData(contacto);
            agreActu = "Actualizado";
        }


        if (result>0){
            limpiar();
            Toast.makeText(MainActivity.this, "Contacto "+agreActu,Toast.LENGTH_LONG).show();
            // Señalizar que se debe actualizar la información al volver al primer activity
            Intent intent = new Intent(this, tabla.class);
            intent.putExtra("update", "true");
            startActivity(intent);
        }else{
            Toast.makeText(MainActivity.this, "Contacto NO "+agreActu,Toast.LENGTH_LONG).show();
        }

    }
    public void onClick_DeleteContacto(View view){
        DbHelper db = new DbHelper(MainActivity.this);
        long result = db.deleteData(getIntent().getExtras().getString("id"));


        if (result>0){
            limpiar();
            Toast.makeText(MainActivity.this, "Contacto Eliminado",Toast.LENGTH_LONG).show();
            // Señalizar que se debe actualizar la información al volver al primer activity
            Intent intent = new Intent(this, tabla.class);
            intent.putExtra("update", "true");
            startActivity(intent);
        }else{
            Toast.makeText(MainActivity.this, "Contacto NO Eliminado",Toast.LENGTH_LONG).show();
        }

    }
    public void startTabla(View view){
        Intent intent = new Intent(this, tabla.class);
        startActivity(intent);
    }

    public void limpiar(){
        txt_nombre.setText("");
        txt_direccion.setText("");
        txt_correo.setText("");
        txt_telefono.setText("");
    }
}