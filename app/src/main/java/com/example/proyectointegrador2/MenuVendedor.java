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
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.proyectointegrador2.Fragments.PerfilFragment;
import com.example.proyectointegrador2.Fragments.TiendasFragment;
import com.example.proyectointegrador2.Fragments.UsuariosFragment;
import com.example.proyectointegrador2.Fragments.Vendedor.PerfilVendedor;
import com.example.proyectointegrador2.Fragments.Vendedor.ProductosFragment;
import com.example.proyectointegrador2.Fragments.Vendedor.TiendaVendedorFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MenuVendedor extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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

        //firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //UI del menú
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view_vendedor);
        toolbar = findViewById(R.id.toolbar);

        //fragmento predeterminado
        getSupportFragmentManager().beginTransaction().add(R.id.content, new ProductosFragment()).commit();
        setTitle("Administrar Productos");

        //Setup Toolbar
        setSupportActionBar(toolbar);

        toggle = setUpDrawerToggle();
        mDrawerLayout.addDrawerListener(toggle);

        //para los items
        navigationView.setNavigationItemSelectedListener(this);

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


        switch(item.getItemId()){
            case R.id.nav_productos:
                ft.replace(R.id.content, new ProductosFragment()).commit();
                break;
            case R.id.nav_tiendainfo:
                ft.replace(R.id.content, new TiendaVendedorFragment()).commit();
                break;
            case R.id.nav_perfilvendedor:
                ft.replace(R.id.content, new PerfilVendedor()).commit();
                break;
            case R.id.nav_cerrarvendedor:
                mAuth.signOut();
                startActivity(new Intent(MenuVendedor.this,MainActivity.class));
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