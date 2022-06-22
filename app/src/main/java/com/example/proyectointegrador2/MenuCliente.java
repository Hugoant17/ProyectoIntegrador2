package com.example.proyectointegrador2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.proyectointegrador2.Fragments.Cliente.Carrito;
import com.example.proyectointegrador2.Fragments.Cliente.Categorias;
import com.example.proyectointegrador2.Fragments.Cliente.Pedidos;
import com.example.proyectointegrador2.Fragments.Cliente.PerfilCliente;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MenuCliente extends AppCompatActivity {

    private BottomNavigationView bnvMenu;
    private Fragment fragment;
    private FragmentManager manager;


    private Button mButtonSignOut;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cliente);

        mAuth = FirebaseAuth.getInstance();
        mButtonSignOut = (Button) findViewById(R.id.btnCerrarCli);

        mButtonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(MenuCliente.this,MainActivity.class));
                finish();
            }
        });

        initView();
        initValues();
        initListener();

    }
    private void initView(){
        bnvMenu = findViewById(R.id.bnvMenu);
    }
    private void initValues(){
        manager = getSupportFragmentManager();
        loadFirstFragment();
    }
    private void initListener(){
        bnvMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int idMenu = item.getItemId();
                switch (idMenu){
                    case R.id.item_categorias:
                        //getSupportActionBar().setTitle("Categorias");
                        fragment = Categorias.newInstance();
                        openFragment(fragment);
                        return true;
                    case R.id.item_perfil:
                        //getSupportActionBar().setTitle("Perfil");
                        fragment = PerfilCliente.newInstance();
                        openFragment(fragment);
                        return true;
                    case R.id.item_pedidos:
                        //getSupportActionBar().setTitle("Pedidos");
                        fragment = Pedidos.newInstance();
                        openFragment(fragment);
                        return true;
                    case R.id.item_carrito:
                        //getSupportActionBar().setTitle("Carrito");
                        fragment = Carrito.newInstance();
                        openFragment(fragment);
                        return true;
                }

                return false;
            }
        });
    }
    private void openFragment(Fragment fragment){
        manager.beginTransaction()
                .replace(R.id.frameContainer, fragment)
                .commit();
    }
    private void loadFirstFragment(){
        //getSupportActionBar().setTitle("Categorias");
        fragment = Carrito.newInstance();
        openFragment(fragment);
    }
}