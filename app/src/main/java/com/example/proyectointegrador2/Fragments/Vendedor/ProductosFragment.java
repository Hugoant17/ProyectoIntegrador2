package com.example.proyectointegrador2.Fragments.Vendedor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.proyectointegrador2.Adaptadores.AdapterProducto;
import com.example.proyectointegrador2.Fragments.CrearTiendaFragment;
import com.example.proyectointegrador2.Modelo.Producto;
import com.example.proyectointegrador2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductosFragment extends Fragment {

    FloatingActionButton fab; //boton flotante

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    AdapterProducto adapterProducto;
    RecyclerView recyclerViewProductos;
    ArrayList<Producto> listaProductos;


    public String codigotienda;


    public ProductosFragment() {

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
        View view = inflater.inflate(R.layout.fragment_productos, container, false);

        fab = view.findViewById(R.id.fab3);

        //firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        recyclerViewProductos = view.findViewById(R.id.recyclerViewProductos);

        listaProductos = new ArrayList<>();

        String id = mAuth.getCurrentUser().getUid();

        mDatabase.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    codigotienda = snapshot.child("tienda").getValue().toString();

                    Bundle bundle = new Bundle();
                    bundle.putString("codigotienda",codigotienda);
                    getParentFragmentManager().setFragmentResult("key1",bundle);

                    cargarData();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mostrarData();

        AccionBoton();

        return view;
    }
    private void cargarData(){

        //Aqui leo los productos de la tienda
        mDatabase.child("Productos").orderByChild("codTienda").equalTo(codigotienda).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaProductos.clear(); //Para que no se dupliquen los datos al volver a leer
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Producto producto = dataSnapshot.getValue(Producto.class);
                    listaProductos.add(producto);

                }
                adapterProducto.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //

    }

    private void mostrarData(){

        recyclerViewProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterProducto = new AdapterProducto(getContext(), listaProductos);

        recyclerViewProductos.setAdapter(adapterProducto);

        //el onclick
        adapterProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombre = listaProductos.get(recyclerViewProductos.getChildAdapterPosition(view)).getNombre();

                Toast.makeText(getContext(), "Selecciono el Producto: "+nombre ,Toast.LENGTH_SHORT).show();

            }
        });

    }
    public void AccionBoton(){


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "AGREGUE PRODUCTO", Toast.LENGTH_SHORT).show();

                //AGREGAR
                CrearProductoFragment fm = new CrearProductoFragment();
                fm.show(getActivity().getSupportFragmentManager(), "CrearProductoFragment");
            }
        });


    }

}