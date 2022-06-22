package com.example.proyectointegrador2.Fragments.Cliente;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectointegrador2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Carrito#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Carrito extends Fragment {



    public Carrito() {
        // Required empty public constructor
    }

    //no borrar
    public static Carrito newInstance() {

        Bundle args = new Bundle();

        Carrito fragment = new Carrito();
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
        return inflater.inflate(R.layout.fragment_carrito, container, false);
    }
}