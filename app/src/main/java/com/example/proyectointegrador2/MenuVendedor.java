package com.example.proyectointegrador2;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.proyectointegrador2.Fragments.UsuariosFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MenuVendedor extends AppCompatActivity  {

    Button btnCerrarVendedor;

    DrawerLayout mDrawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    //icono de la hamburguesa
    ActionBarDrawerToggle toggle;

    //firebase
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_vendedor);


        btnCerrarVendedor = findViewById(R.id.btnCerrarVendedor);

        //firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //UI del menú
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        //fragmento predeterminado
       // getSupportFragmentManager().beginTransaction().add(R.id.content, new UsuariosFragment()).commit();
        //setTitle("Administrar Usuarios");

        //Setup Toolbar
        setSupportActionBar(toolbar);

        //toggle = setUpDrawerToggle();
        //mDrawerLayout.addDrawerListener(toggle);

        //para los items
        //navigationView.setNavigationItemSelectedListener(this);


        btnCerrarVendedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cerrarsesion();
            }
        });

    }

    public void Cerrarsesion(){
        mAuth.signOut();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }


}