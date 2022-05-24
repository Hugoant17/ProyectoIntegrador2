package com.example.proyectointegrador2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private EditText txtUsuario, txtPassword;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    String email, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //splash screen
        setTheme(R.style.Theme_ProyectoIntegrador2);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }
    public void validación(View view){
        email = txtUsuario.getText().toString();
        pass = txtPassword.getText().toString();

        if(email.isEmpty() && pass.isEmpty()){
            Toast.makeText(getApplicationContext(), "Falta rellenar los campos", Toast.LENGTH_SHORT).show();
        }else if (email.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Introduzca su email", Toast.LENGTH_SHORT).show();
            txtUsuario.requestFocus();
        } else if (pass.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Introduzca su contraseña", Toast.LENGTH_SHORT).show();
            txtPassword.requestFocus();
        } else {
            login();
        }
    }
    private void login(){
        mAuth.signInWithEmailAndPassword(txtUsuario.getText().toString().trim(),txtPassword.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String id = mAuth.getCurrentUser().getUid();
                    databaseReference.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                String rol = snapshot.child("rol").getValue().toString();
                                if(rol.equals("Administrador")){
                                    startActivity(new Intent(getApplicationContext(),MenuAdmin.class));
                                    Toast.makeText(getApplicationContext(), "Bienvenido Administrador", Toast.LENGTH_SHORT).show();
                                }else{
                                    startActivity(new Intent(getApplicationContext(),MenuCliente.class));
                                    Toast.makeText(getApplicationContext(), "Bienvenido Cliente", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(), "Email y/o contraseña incorrecto",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Si ya inicio su sesión se hará lo siguiente
    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() != null) {

            String id = mAuth.getCurrentUser().getUid();

            databaseReference.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        String rol = snapshot.child("rol").getValue().toString();
                        if(rol.equals("Administrador")){
                            startActivity(new Intent(getApplicationContext(),MenuAdmin.class));
                            finish();
                       }else if(rol.equals("Vendedor")){
                            startActivity(new Intent(getApplicationContext(),MenuAdmin.class));
                            finish();
                        }else{
                            startActivity(new Intent(getApplicationContext(),MenuCliente.class));
                            finish();
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }
    }

    public void Registrarse(View view){
        Intent siguiente = new Intent(this, Registro.class);
        startActivity(siguiente);
    }
}