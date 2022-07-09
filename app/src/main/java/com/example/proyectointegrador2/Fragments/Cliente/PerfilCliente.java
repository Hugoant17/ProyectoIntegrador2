package com.example.proyectointegrador2.Fragments.Cliente;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.proyectointegrador2.MainActivity;
import com.example.proyectointegrador2.MenuCliente;
import com.example.proyectointegrador2.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilCliente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilCliente extends Fragment {


    private Button mButtonSignOut;
    private FirebaseAuth mAuth;

    public PerfilCliente() {
        // Required empty public constructor
    }
    //no borrar
    public static PerfilCliente newInstance() {

        Bundle args = new Bundle();

        PerfilCliente fragment = new PerfilCliente();
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_perfil_cliente, container, false);

        mAuth = FirebaseAuth.getInstance();
        mButtonSignOut = (Button) view.findViewById(R.id.btnCerrarCli);

        mButtonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

        return view;
    }
}