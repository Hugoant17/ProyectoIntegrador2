package com.example.proyectointegrador2.Adaptadores;

import android.content.ClipData;
import android.content.Context;
import android.media.MediaRouter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyectointegrador2.Entidades.Tienda;
import com.example.proyectointegrador2.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AdapterTienda extends RecyclerView.Adapter<AdapterTienda.ViewHolder> implements View.OnClickListener {


    LayoutInflater inflater;
    ArrayList<Tienda> model;
    //se agrego
    Context context;


    //LISTENER
    private View.OnClickListener listener;


    public AdapterTienda(Context context, ArrayList<Tienda> model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;
        //se agregó
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.lista, parent,false);

        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nombre = model.get(position).getNombre();
        String codigo = model.get(position).getCodigo();
        String direccion = model.get(position).getDireccion();
        String imagen = model.get(position).getImagen();

        holder.nombre.setText(nombre);
        holder.codigo.setText(codigo);
        holder.direccion.setText(direccion);

        //se agrega esto para mostrar la imagen
        Glide.with(context).load(imagen).circleCrop().into(holder.imagen);
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

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView codigo, nombre, direccion;
        ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.titulo_tienda);
            codigo = itemView.findViewById(R.id.codigo_tienda);
            direccion = itemView.findViewById(R.id.direccion_tienda);
            imagen = itemView.findViewById(R.id.imagen_tienda);
        }
    }



}
