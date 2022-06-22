package com.example.proyectointegrador2.Fragments.Cliente;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectointegrador2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilCliente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilCliente extends Fragment {


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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil_cliente, container, false);
    }
}