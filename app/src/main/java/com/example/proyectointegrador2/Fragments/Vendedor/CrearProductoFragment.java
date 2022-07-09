package com.example.proyectointegrador2.Fragments.Vendedor;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CrearProductoFragment extends DialogFragment {

    Button btnAgregarProducto,btnEliminarProducto;
    EditText etCodigo, etNombre, etDescripcion,etCategoria, etPrecio, etStock;

    //String codigotienda;

    DatabaseReference mDatabase;

    public CrearProductoFragment() {
        // Required empty public constructor
    }

    public static CrearProductoFragment newInstance(String param1, String param2) {
        CrearProductoFragment fragment = new CrearProductoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crear_producto, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        etNombre = view.findViewById(R.id.etNomProducto);
        etCodigo = view.findViewById(R.id.etCodProducto);
        etDescripcion = view.findViewById(R.id.etDescProducto);
        etCategoria = view.findViewById(R.id.etCatProducto);
        etStock = view.findViewById(R.id.etStockProducto);
        etPrecio = view.findViewById(R.id.etPreProducto);

        btnAgregarProducto = view.findViewById(R.id.btnAgregaProducto);
        btnEliminarProducto = view.findViewById(R.id.btnEliminarProducto);
        //Oculto el botón
        btnEliminarProducto.setVisibility(View.GONE);


        btnAgregarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                creacionProducto();
            }
        });


        return view;
    }

    public void creacionProducto(){

        final String[] codigotienda = new String[1];

        getParentFragmentManager().setFragmentResultListener("key1", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                codigotienda[0] = result.getString("codigotienda");
            }
        });

        String codigo = etCodigo.getText().toString();
        String nombre = etNombre.getText().toString();
        String descripcion = etDescripcion.getText().toString();
        String categoria = etCategoria.getText().toString();
        int stock = Integer.parseInt(etStock.getText().toString());
        double precio = Double.parseDouble(etPrecio.getText().toString());

        final String code=codigotienda[0];

        Map<String, Object> map = new HashMap<>();
        map.put("codigo",codigo);
        map.put("nombre",nombre);
        map.put("descripcion",descripcion);
        map.put("categoria",categoria);
        map.put("stock",stock);
        map.put("precio",precio);
        map.put("codTienda",code);

        mDatabase.child("Productos").child(codigo).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
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

}