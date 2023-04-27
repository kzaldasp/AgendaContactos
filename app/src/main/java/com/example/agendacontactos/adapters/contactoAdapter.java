package com.example.agendacontactos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agendacontactos.R;
import com.example.agendacontactos.db.Contacto;

import java.util.ArrayList;

public class contactoAdapter extends RecyclerView.Adapter<contactoAdapter.myViewHolder> {

    Context context;
    ArrayList<Contacto> contactos;
    public contactoAdapter(Context context, ArrayList<Contacto> contactos){
        this.context=context;
        this.contactos = contactos;
    }

    @NonNull
    @Override
    public contactoAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.view_contacto,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull contactoAdapter.myViewHolder holder, int position) {
        holder.txt_nombre.setText(contactos.get(position).getNombre());
        holder.txt_correo.setText(contactos.get(position).getCorreo());
        holder.txt_telefono.setText(contactos.get(position).getTelefono());
        holder.txt_direccion.setText(contactos.get(position).getDireccion());
    }

    @Override
    public int getItemCount() {
        return contactos.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView txt_nombre, txt_direccion,txt_telefono,txt_correo;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_nombre = itemView.findViewById(R.id.txt_nombre);
            txt_direccion = itemView.findViewById(R.id.txt_direccion);
            txt_telefono = itemView.findViewById(R.id.txt_telefono);
            txt_correo = itemView.findViewById(R.id.txt_correo);
        }
    }
}
