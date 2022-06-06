package com.example.proyectointegrador2.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectointegrador2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class PerfilFragment extends Fragment {

    FirebaseAuth mAuth;


    DatabaseReference database;

    EditText etPerfCorreo, etPerfNom, etPerfContra, etPerfDirec;
    Button btnGuardPerfil;

    String rol = "";

    private String idUser;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        mAuth = FirebaseAuth.getInstance();

        idUser = mAuth.getCurrentUser().getUid();

        etPerfCorreo = (EditText) view.findViewById(R.id.etPerfCorreo);
        etPerfNom = (EditText) view.findViewById(R.id.etPerfNom);
        etPerfContra = (EditText) view.findViewById(R.id.etPerfContra);
        etPerfDirec = (EditText) view.findViewById(R.id.etPerfDirec);

        btnGuardPerfil = (Button) view.findViewById(R.id.btnGuardPerfil);

        database = FirebaseDatabase.getInstance().getReference("Usuarios");

        MostrarInfo();

        GuardarInfo();


        return view;
    }

    public void MostrarInfo(){
        database.child(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    String correo = snapshot.child("correo").getValue().toString();
                    String nombre = snapshot.child("nombre").getValue().toString();
                    String direccion = snapshot.child("direccion").getValue().toString();
                    String contra = snapshot.child("contraseña").getValue().toString();
                    rol = snapshot.child("rol").getValue().toString();

                    etPerfCorreo.setText(correo);
                    etPerfNom.setText(nombre);
                    etPerfDirec.setText(direccion);
                    etPerfContra.setText(contra);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void GuardarInfo(){


        btnGuardPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String correo = etPerfCorreo.getText().toString();
                String nombre = etPerfNom.getText().toString();
                String direccion = etPerfDirec.getText().toString();
                String contraseña = etPerfContra.getText().toString();

                Map<String, Object> map = new HashMap<>();
                map.put("correo",correo);
                map.put("nombre",nombre);
                map.put("direccion",direccion);
                map.put("contraseña",contraseña);
                map.put("rol",rol);

                database.child(idUser).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(),"Actualización exitosa", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(),"No se pudo Actualizar", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });

    }
}