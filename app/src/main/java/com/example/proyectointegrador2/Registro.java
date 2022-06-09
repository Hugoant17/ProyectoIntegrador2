package com.example.proyectointegrador2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    EditText txtRegistroCorreo, txtRegistroContra, txtRegistroNom, txtRegistroDirec, txtRegistroDni;
    Button btnRegistrar;

    //VARIABLES DE LOS DATOS QUE VAN A SER REGISTRADOS

    private String name = "";
    private String email = "";
    private String password = "";
    private String address="";
    private String rol = "";
    //AGREGAR EL DNI Y NACIMIENTO
    private String dni = "";
    private String nacimiento = "";

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        txtRegistroCorreo = (EditText) findViewById(R.id.txtRegistroCorreo);
        txtRegistroContra = (EditText) findViewById(R.id.txtRegistroContra);
        txtRegistroNom = (EditText) findViewById(R.id.txtRegistroNom);
        txtRegistroDirec = (EditText) findViewById(R.id.txtRegistroDirec);
        txtRegistroDni = (EditText) findViewById(R.id.txtRegistroDni);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = txtRegistroNom.getText().toString();
                email = txtRegistroCorreo.getText().toString();
                password = txtRegistroContra.getText().toString();
                address = txtRegistroDirec.getText().toString();
                dni = txtRegistroDni.getText().toString();
                rol = "Cliente";

                if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !address.isEmpty() && !dni.isEmpty()){

                    if (password.length() >= 6){
                        registerUser();
                    }else{
                        Toast.makeText(Registro.this, "El password debe ser mayor a 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(Registro.this, "Debe completar", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void registerUser(){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    Map<String, Object> map = new HashMap<>();
                    map.put("nombre",name);
                    map.put("correo",email);
                    map.put("contraseña",password);
                    map.put("direccion",address);
                    map.put("dni",dni);
                    map.put("rol",rol);

                    String id = mAuth.getCurrentUser().getUid();

                    mDatabase.child("Usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                startActivity(new Intent(Registro.this,MainActivity.class));
                                Toast.makeText(Registro.this,"Registro exitoso", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else{
                                Toast.makeText(Registro.this,"No se pudieron crear los datos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else{
                    Toast.makeText(Registro.this, "No se pudo registrar al usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}