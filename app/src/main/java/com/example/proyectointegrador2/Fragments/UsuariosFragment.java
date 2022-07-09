package com.example.proyectointegrador2.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyectointegrador2.Modelo.Usuario;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;


import com.example.proyectointegrador2.Adaptadores.AdapterUsuario;
import com.example.proyectointegrador2.R;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class UsuariosFragment extends Fragment {

    FloatingActionButton fab2; //boton flotante

    AdapterUsuario adapterUsuario;
    RecyclerView recyclerViewUsuarios;
    ArrayList<Usuario> listaUsuarios;

    DatabaseReference database;


    public UsuariosFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usuarios, container, false);

        fab2 = view.findViewById(R.id.fab2);
        //firebase
        database = FirebaseDatabase.getInstance().getReference("Usuarios");

        recyclerViewUsuarios = view.findViewById(R.id.recyclerViewUsuarios);

        listaUsuarios = new ArrayList<>();

        cargarLista();

        mostrarData();

        AccionBoton();




        return view;
    }
    /*
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }*/

    public void cargarLista(){

        //implementando base de datos firebase
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaUsuarios.clear(); //Para que no se dupliquen los datos al volver a leer
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Usuario usuario = dataSnapshot.getValue(Usuario.class);
                    listaUsuarios.add(usuario);
                }
                adapterUsuario.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    public void mostrarData(){
        //get context porque estamos sen un fragment y no un this
        recyclerViewUsuarios.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterUsuario = new AdapterUsuario(getContext(), listaUsuarios);

        recyclerViewUsuarios.setAdapter(adapterUsuario);

        //el onclick
        adapterUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombre = listaUsuarios.get(recyclerViewUsuarios.getChildAdapterPosition(view)).getNombre();

                Toast.makeText(getContext(), "Selecciono al Usuario: "+nombre ,Toast.LENGTH_SHORT).show();

            }
        });

    }



    public void AccionBoton(){

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "AGREGUE USUARIO", Toast.LENGTH_SHORT).show();

            }
        });


    }

}