package com.example.proyectointegrador2.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.proyectointegrador2.MenuAdmin;
import com.example.proyectointegrador2.R;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

public class UsuariosFragment extends Fragment {

   TextView txtPrueba;

    FloatingActionButton fab; //boton flotante



    private StorageReference mStorage;
    private Button mUploadBtn;
    private static final int GALLERY_INTENT = 1;
    private static final int RESULT_OK = -1;

    private ImageView mImageView;
    private ProgressDialog mProgressDialog;

    public UsuariosFragment() {

    }
    /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usuarios, container, false);


        fab = view.findViewById(R.id.fab);


        mStorage = FirebaseStorage.getInstance().getReference();

        mUploadBtn = (Button) view.findViewById(R.id.btnSubir);

        txtPrueba = view.findViewById(R.id.txtPrueba);
        txtPrueba.setText("Eres administrador");

        mImageView = view.findViewById(R.id.imgSubido);
        mProgressDialog = new ProgressDialog(getContext());

        mUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);
            }
        });

        /*Glide.with(getContext())
                .load("https://www.adslzone.net/app/uploads-adslzone.net/2019/04/borrar-fondo-imagen.jpg")
                .placeholder(R.drawable.ic_launcher_background)
                .fitCenter()
                .circleCrop()
                .into(mImageView);*/

        AccionBoton();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){

            mProgressDialog.setTitle("Subiendo foto...");
            mProgressDialog.setTitle("Subiendo foto a firebase");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

            Uri uri = data.getData();

            StorageReference filePath = mStorage.child("fotos").child(uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mProgressDialog.dismiss();
                    //se otiene la direccion o enlace de descarga donde esta almacenada
                    Task<Uri> descargarFoto = taskSnapshot.getStorage().getDownloadUrl();

                    Glide.with(getContext())
                            .load(descargarFoto)
                            .centerCrop()
                            .fitCenter()
                            .into(mImageView);

                    Toast.makeText(getContext(),"Se subio la foto correctamente",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public void AccionBoton(){

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Aqui se agrega usuarios", Toast.LENGTH_SHORT).show();
            }
        });

    }

}