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

public class AdapterCategoriaProductos extends RecyclerView.Adapter<AdapterCategoriaProductos.ViewHolder> implements View.OnClickListener {

    LayoutInflater inflater;
    ArrayList<Producto> model;
    Context context;

    //LISTENER
    private View.OnClickListener listener;

    public AdapterCategoriaProductos(Context context, ArrayList<Producto> model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;
        this.context=context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.listado_productos_cliente,parent,false);
        view.setOnClickListener(this);

        return new ViewHolder(view);
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String codigo, nombre, codTienda, descripcion, categoria, foto;
        int stock;
        double precio;

        codigo = model.get(position).getCodigo();
        codTienda = model.get(position).getCodTienda();
        categoria = model.get(position).getCategoria();

        nombre = model.get(position).getNombre();
        descripcion = model.get(position).getDescripcion();
        stock = model.get(position).getStock();
        precio = model.get(position).getPrecio();
        foto = model.get(position).getFoto();


        holder.nombre.setText(nombre);
        holder.descripcion.setText(descripcion);
        holder.stock.setText(String.valueOf("Stock: "+stock));
        holder.precio.setText(String.valueOf("Precio: S/."+precio));

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

        TextView nombre,descripcion,stock, precio;
        ImageView foto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.titulo_producto);
            descripcion = itemView.findViewById(R.id.descripcion_producto);
            stock = itemView.findViewById(R.id.stock_producto);
            precio = itemView.findViewById(R.id.precio_producto);
            foto = itemView.findViewById(R.id.imagen_producto);

        }
    }
}
