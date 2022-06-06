package com.example.proyectointegrador2.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectointegrador2.Adaptadores.AdapterTienda;
import com.example.proyectointegrador2.Entidades.Tienda;
import com.example.proyectointegrador2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TiendasFragment extends Fragment {

    FloatingActionButton fab; //boton flotante


    AdapterTienda adapterTienda;
    RecyclerView recyclerViewTiendas;
    ArrayList<Tienda> listaTiendas;

    DatabaseReference database;

    public TiendasFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_tiendas, container, false);


        //boton flotante
        fab = view.findViewById(R.id.fab);

        //firebase
        database = FirebaseDatabase.getInstance().getReference("Tiendas");

        recyclerViewTiendas = view.findViewById(R.id.recyclerView);

        listaTiendas = new ArrayList<>();

        cargarLista();
        //mostrar data
        mostrarData();

        AccionBoton();



        //NO ELIMINAR
        return view;
    }

    public void cargarLista(){

        //implementando base de datos firebase
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaTiendas.clear(); //Para que no se dupliquen los datos al volver a leer
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Tienda tienda = dataSnapshot.getValue(Tienda.class);

                    listaTiendas.add(tienda);
                }
                adapterTienda.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        /*
        listaTiendas.add(new Tienda("T001","Ohashi","Av. Los Olivos 3205"));
        listaTiendas.add(new Tienda("T002","Web","Av. Los Natywi 2035"));
        listaTiendas.add(new Tienda("T003","Ola","Av. Los Huggy Wuggy 22205"));
        listaTiendas.add(new Tienda("T004","Naruto","Av. Los Alisos 4232"));
        listaTiendas.add(new Tienda("T005","Gigi","Av. Los Frikis 45555"));
        listaTiendas.add(new Tienda("T006","Blue Demon","Av. Túpac Amaru 7822"));
        listaTiendas.add(new Tienda("T007","Demon Slayer","Av. Los Alisos 12432"));

        */
    }
    public void mostrarData(){
        //get context porque estamos sen un fragment y no un this
        recyclerViewTiendas.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterTienda = new AdapterTienda(getContext(), listaTiendas);

        recyclerViewTiendas.setAdapter(adapterTienda);


        //el onclick
        adapterTienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Aqui atrapo los datos del que selecciono
                String codigo = listaTiendas.get(recyclerViewTiendas.getChildAdapterPosition(view)).getCodigo();
                String nombre = listaTiendas.get(recyclerViewTiendas.getChildAdapterPosition(view)).getNombre();
                String direccion = listaTiendas.get(recyclerViewTiendas.getChildAdapterPosition(view)).getDireccion();

                //llamo el fragment dialog
                CrearTiendaFragment fm = new CrearTiendaFragment();
                fm.show(getActivity().getSupportFragmentManager(), "EditartiendaFragment");

                //Envio los datos seleccionados
                Bundle bundle = new Bundle();
                bundle.putString("codigo",codigo);
                bundle.putString("nombre",nombre);
                bundle.putString("direccion",direccion);
                getParentFragmentManager().setFragmentResult("key",bundle);

                //Toast de confirmación
                Toast.makeText(getContext(), "Selecciono: "+nombre ,Toast.LENGTH_SHORT).show();

            }
        });

    }


    public void AccionBoton(){

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "AGREGUE UNA TIENDA", Toast.LENGTH_SHORT).show();

                //AGREGAR
                CrearTiendaFragment fm = new CrearTiendaFragment();
                fm.show(getActivity().getSupportFragmentManager(), "CrearTiendaFragment");
                
            }
        });

    }


}