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
import com.example.proyectointegrador2.Modelo.Producto;
import com.example.proyectointegrador2.R;

import java.util.ArrayList;

public class AdapterProducto extends RecyclerView.Adapter<AdapterProducto.ViewHolder> implements View.OnClickListener{

    LayoutInflater inflater;
    ArrayList<Producto> model;
    Context context;


    //LISTENER
    private View.OnClickListener listener;

    public AdapterProducto(Context context, ArrayList<Producto> model) {
        this.inflater = LayoutInflater.from(context);
        this.model = model;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.listaproductos, parent,false);
        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProducto.ViewHolder holder, int position) {

        String nombre = model.get(position).getNombre();
        String codigo = model.get(position).getCodigo();
        //String codTienda = model.get(position).getCodTienda();
        //String descripcion = model.get(position).getDescripcion();
        String categoria = model.get(position).getCategoria();
        String foto = model.get(position).getFoto();

        //int stock = model.get(position).getStock();
        double precio = model.get(position).getPrecio();

        holder.nombre.setText(nombre);
        holder.codigo.setText(codigo);
        holder.categoria.setText(categoria);
        holder.precio.setText(String.valueOf("Precio: "+precio));

        Glide.with(context).load(foto).circleCrop().into(holder.foto);
    }

    @Override
    public int getItemCount() {
        return model.size();
    }


    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView codigo, nombre, categoria, precio;
        ImageView foto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            codigo = itemView.findViewById(R.id.codigodelproducto);
            nombre = itemView.findViewById(R.id.titulodelproducto);
            categoria = itemView.findViewById(R.id.categoriadelProducto);
            precio = itemView.findViewById(R.id.preciodelproducto);
            foto = itemView.findViewById(R.id.imagendelpruducto);

        }
    }
}
