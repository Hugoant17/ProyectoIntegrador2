package com.example.proyectointegrador2.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyectointegrador2.Modelo.Usuario;
import com.example.proyectointegrador2.R;

import java.util.ArrayList;

public class AdapterUsuario extends RecyclerView.Adapter<AdapterUsuario.ViewHolder> implements View.OnClickListener {

    LayoutInflater inflater;
    ArrayList<Usuario> model;
    Context context;

    //LISTENER
    private View.OnClickListener listener;

    public AdapterUsuario(Context context, ArrayList<Usuario> model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;
        this.context=context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.listausuarios, parent,false);

        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String nombre = model.get(position).getNombre();
        String correo = model.get(position).getCorreo();
        String dni = model.get(position).getDni();
        String direccion = model.get(position).getDireccion();
        String rol = model.get(position).getRol();
        String contraseña = model.get(position).getContraseña();

        String foto = model.get(position).getFoto();

        holder.nombre.setText(nombre);
        holder.correo.setText(correo);
        holder.dni.setText(dni);
        holder.rol.setText(rol);
        /*
        holder.codigo.setText(codigo);
        holder.direccion.setText(direccion);
        holder.contraseña.setText(contraseña);*/

        Glide.with(context).load(foto).circleCrop().into(holder.foto);

    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //TextView codigo, direccion, contraseña;
        TextView nombre,correo,dni,rol;
        ImageView foto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.titulo_usuario);
            correo = itemView.findViewById(R.id.correo_usuario);
            dni = itemView.findViewById(R.id.dni_usuario);
            rol = itemView.findViewById(R.id.rol_usuario);
            foto = itemView.findViewById(R.id.imagen_usuario);

            /*
            codigo = itemView.findViewById(R.id.correo_usuario1);
            direccion = itemView.findViewById(R.id.correo_usuario2);
            contraseña = itemView.findViewById(R.id.correo_usuario3);*/
        }
    }
}
