package com.example.agendacontactos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static String DATABASE_NAME = "agenda.db";
    public static final String TABLE_CONTACTOS = "t_contactos";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME   , null , DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_CONTACTOS+"(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT," +
                "telefono TEXT," +
                "direccion TEXT," +
                "correo TEXT" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CONTACTOS);
        onCreate(db);
    }

    public long addContacto(Contacto contacto){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put( "nombre",contacto.getNombre());
        cv.put( "telefono",contacto.getTelefono());
        cv.put("direccion",contacto.getDireccion() );
        cv.put("correo",contacto.getCorreo());

        return db.insert(TABLE_CONTACTOS,null,cv);
    }

    public Cursor readAllContactos(){
        SQLiteDatabase db = this.getReadableDatabase();
        if(db != null){
            return db.rawQuery("SELECT * FROM "+TABLE_CONTACTOS, null);
        }
        return null;
    }

    public Cursor readContacto(String busqueda){
        SQLiteDatabase db = this.getReadableDatabase();
        if(db != null){
            return db.rawQuery("SELECT * FROM "+TABLE_CONTACTOS+" WHERE nombre LIKE '%"+busqueda+"%' " +
                    "OR direccion LIKE '%"+busqueda+"%' OR telefono LIKE '%"+busqueda+"%'" +
                    "OR correo LIKE '%"+busqueda+"%'", null);
        }
        return null;
    }
}
