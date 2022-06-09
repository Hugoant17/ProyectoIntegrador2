package com.example.proyectointegrador2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.proyectointegrador2.Fragments.*;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MenuAdmin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    TextView muestraUsuario;

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
        setContentView(R.layout.activity_menu_admin);

        //firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //UI del menú
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        //fragmento predeterminado
        getSupportFragmentManager().beginTransaction().add(R.id.content, new UsuariosFragment()).commit();
        setTitle("Administrar Usuarios");

        //Setup Toolbar
        setSupportActionBar(toolbar);

        toggle = setUpDrawerToggle();
        mDrawerLayout.addDrawerListener(toggle);

        //para los items
        navigationView.setNavigationItemSelectedListener(this);

        //InfoUsuario();

    }
    //para que aparezca el nombre del usuario en el menú
    private void InfoUsuario(){

        muestraUsuario = (TextView) findViewById(R.id.txtMuestraUsuario);
        //Nombre del usuario en el Menú

        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.child("nombre").getValue().toString();
                    muestraUsuario.setText(name);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });

    }

    private ActionBarDrawerToggle setUpDrawerToggle(){

        return new ActionBarDrawerToggle(this,
                mDrawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        );
    }

    //Funcion para que al cambiar a modo horizontal el telefono para que se siga mostrando
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        selectItemNav(item);

        return true;
    }

    private void selectItemNav(MenuItem item) {

        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        //Se inicia la informacion del usuario
        //InfoUsuario();

        switch(item.getItemId()){
            case R.id.nav_usuarios:
                ft.replace(R.id.content, new UsuariosFragment()).commit();
                break;
            case R.id.nav_tiendas:
                ft.replace(R.id.content, new TiendasFragment()).commit();
                break;
            case R.id.nav_perfil:
                ft.replace(R.id.content, new PerfilFragment()).commit();
                break;
            case R.id.nav_cerrar:
                mAuth.signOut();
                startActivity(new Intent(MenuAdmin.this,MainActivity.class));
                finish();
                break;
        }
        setTitle(item.getTitle());
        mDrawerLayout.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}