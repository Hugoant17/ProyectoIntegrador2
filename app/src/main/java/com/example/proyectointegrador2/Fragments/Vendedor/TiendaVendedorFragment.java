package com.example.proyectointegrador2.Fragments.Vendedor;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.proyectointegrador2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class TiendaVendedorFragment extends Fragment {

    //firebase
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    TextView txtdireccionTienda,txtnombreTienda;
    ImageView imgTienda;


    public TiendaVendedorFragment() {
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tienda_vendedor, container, false);

        txtdireccionTienda = (TextView) view.findViewById(R.id.txtdireccionTienda);
        txtnombreTienda = (TextView) view.findViewById(R.id.txtnombreTienda);
        imgTienda = (ImageView) view.findViewById(R.id.imgTienda);

        //firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        MostrarDatosdeTienda();

        return view;
    }
    public void MostrarDatosdeTienda(){


        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String codigotienda = snapshot.child("tienda").getValue().toString();

                    mDatabase.child("Tiendas").child(codigotienda).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.exists()){
                                String nombre = snapshot.child("nombre").getValue().toString();
                                String direccion = snapshot.child("direccion").getValue().toString();
                                String foto = snapshot.child("imagen").getValue().toString();

                                txtnombreTienda.setText(nombre);
                                txtdireccionTienda.setText(direccion);

                                Glide.with(getContext()).load(foto).into(imgTienda);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}