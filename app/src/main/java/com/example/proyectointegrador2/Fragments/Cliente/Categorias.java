package com.example.proyectointegrador2.Fragments.Cliente;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectointegrador2.Adaptadores.AdapterCategoriaProductos;
import com.example.proyectointegrador2.Modelo.Producto;
import com.example.proyectointegrador2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Categorias extends Fragment {


    AdapterCategoriaProductos adapterCategoriaProductos;
    RecyclerView recyclerViewProductos;
    ArrayList<Producto> listaProductos;


    DatabaseReference database;

    public Categorias() {
        // Required empty public constructor
    }
    //no borrar
    public static Fragment newInstance() {

        Bundle args = new Bundle();

        Categorias fragment = new Categorias();
        fragment.setArguments(args);
        return fragment;
    }
    //no borrar

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categorias, container, false);

        database = FirebaseDatabase.getInstance().getReference("Productos");

        recyclerViewProductos = view.findViewById(R.id.recyclerViewCatProductos);

        listaProductos = new ArrayList<>();

        cargarLista();

        mostrarData();


        return view;
    }

    private void cargarLista() {

        //implementando base de datos firebase
        //asi aparece
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaProductos.clear(); //Para que no se dupliquen los datos al volver a leer
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Producto producto = dataSnapshot.getValue(Producto.class);
                    listaProductos.add(producto);
                }
                adapterCategoriaProductos.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
    private void mostrarData(){

        recyclerViewProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterCategoriaProductos = new AdapterCategoriaProductos(getContext(), listaProductos);

        recyclerViewProductos.setAdapter(adapterCategoriaProductos);

    }
}