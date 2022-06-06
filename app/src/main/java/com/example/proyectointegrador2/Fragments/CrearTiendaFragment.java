package com.example.proyectointegrador2.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectointegrador2.R;
import com.example.proyectointegrador2.Registro;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CrearTiendaFragment extends DialogFragment {


    Button btnAgregaTienda,btnEliminarTienda;
    EditText etcodigo, etnombre, etdireccion;
    DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);

        //Aqui traigo las variables del recycler view cuando lo selecciono
        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                String code = bundle.getString("codigo");
                String name = bundle.getString("nombre");
                String address = bundle.getString("direccion");
                etcodigo.setText(code);
                etnombre.setText(name);
                etdireccion.setText(address);

                if(!code.isEmpty()){
                    etcodigo.setEnabled(false);

                    ActualizarEliminar();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crear_tienda, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();


        etcodigo = view.findViewById(R.id.etCodTienda);
        etnombre = view.findViewById(R.id.etNomTienda);
        etdireccion = view.findViewById(R.id.etDirecTienda);
        btnAgregaTienda = view.findViewById(R.id.btnAgregaTienda);
        btnEliminarTienda = view.findViewById(R.id.btnEliminarTienda);
        //Oculto el botón
        btnEliminarTienda.setVisibility(View.GONE);
        //

        //


        btnAgregaTienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creacionTienda();
            }
        });

        return view;
    }

    public void creacionTienda(){

        String codigo = etcodigo.getText().toString();
        String nombre = etnombre.getText().toString();
        String direccion = etdireccion.getText().toString();


        Map<String, Object> map = new HashMap<>();
        map.put("codigo",codigo);
        map.put("nombre",nombre);
        map.put("direccion",direccion);

        mDatabase.child("Tiendas").child(codigo).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(getContext(),"Registro exitoso", Toast.LENGTH_SHORT).show();
                    getDialog().dismiss();
                }else{
                    Toast.makeText(getContext(),"No se pudo registrar", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public void ActualizarEliminar(){

        btnAgregaTienda.setText("Actualizar");

        //Hago visible el botón de eliminar
        btnEliminarTienda.setVisibility(View.VISIBLE);


        String codigo = etcodigo.getText().toString();
        String nombre = etnombre.getText().toString();
        String direccion = etdireccion.getText().toString();

        btnAgregaTienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> map = new HashMap<>();
                map.put("codigo",codigo);
                map.put("nombre",nombre);
                map.put("direccion",direccion);

                mDatabase.child("Tiendas").child(codigo).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(getContext(),"Actualización exitosa", Toast.LENGTH_SHORT).show();
                            getDialog().dismiss();
                        }else{
                            Toast.makeText(getContext(),"No se pudo Actualizar", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

        btnEliminarTienda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("Tiendas").child(codigo).removeValue();
                Toast.makeText(getContext(),"Se eliminó la tienda: "+ codigo, Toast.LENGTH_SHORT).show();
                getDialog().dismiss();

            }
        });

    }
}