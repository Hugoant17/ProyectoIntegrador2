package com.example.proyectointegrador2.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
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
import com.example.proyectointegrador2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.auth.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class PerfilFragment extends Fragment {

    FirebaseAuth mAuth;


    DatabaseReference database;

    EditText etPerfCorreo, etPerfNom, etPerfContra, etPerfDirec;
    Button btnGuardPerfil;
    ImageView fotoPerf;

    String rol = "";

    private String idUser;

    //lo que agregue
    private StorageReference mStorage;
    private static final int GALLERY_INTENT = 1;
    private static final int RESULT_OK = -1;
    private ProgressDialog mProgressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        mStorage = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        idUser = mAuth.getCurrentUser().getUid();

        mProgressDialog = new ProgressDialog(getContext());

        etPerfCorreo = (EditText) view.findViewById(R.id.etPerfCorreo);
        etPerfNom = (EditText) view.findViewById(R.id.etPerfNom);
        etPerfContra = (EditText) view.findViewById(R.id.etPerfContra);
        etPerfDirec = (EditText) view.findViewById(R.id.etPerfDirec);
        fotoPerf = (ImageView) view.findViewById(R.id.fotoPerf);

        btnGuardPerfil = (Button) view.findViewById(R.id.btnGuardPerfil);

        database = FirebaseDatabase.getInstance().getReference("Usuarios");

        MostrarInfo();

        GuardarInfo();

        Actualizarfoto();

        return view;
    }

    public void MostrarInfo(){
        database.child(idUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    String correo = snapshot.child("correo").getValue().toString();
                    String nombre = snapshot.child("nombre").getValue().toString();
                    String direccion = snapshot.child("direccion").getValue().toString();
                    String contra = snapshot.child("contraseña").getValue().toString();
                    String foto = snapshot.child("foto").getValue().toString();
                    rol = snapshot.child("rol").getValue().toString();

                    etPerfCorreo.setText(correo);
                    etPerfNom.setText(nombre);
                    etPerfDirec.setText(direccion);
                    etPerfContra.setText(contra);

                    //se agrega esto para mostrar la imagen de firebase
                    Glide.with(getContext()).load(foto).into(fotoPerf);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void GuardarInfo(){

        btnGuardPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String correo = etPerfCorreo.getText().toString();
                String nombre = etPerfNom.getText().toString();
                String direccion = etPerfDirec.getText().toString();
                String contraseña = etPerfContra.getText().toString();

                Map<String, Object> map = new HashMap<>();
                map.put("correo",correo);
                map.put("nombre",nombre);
                map.put("direccion",direccion);
                map.put("contraseña",contraseña);
                map.put("rol",rol);

                database.child(idUser).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getContext(),"Datos actualizados", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(),"No se pudo Actualizar", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                //AQUI SE CAMBIA EL CORREO Y PASSWORD EN EL AUTHENTICATION TAMBIEN
                user.updateEmail(etPerfCorreo.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        user.updatePassword(etPerfContra.getText().toString());
                        //
                    }
                });


            }
        });

    }
    public void Actualizarfoto(){

        fotoPerf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);
            }
        });
    }
    //lo que se agrega
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){

            mProgressDialog.setTitle("Subiendo...");
            mProgressDialog.setTitle("Subiendo la foto de Perfil...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

            Uri idfoto = data.getData();

            StorageReference filePath = mStorage.child("fotos").child(idfoto.getLastPathSegment());
            filePath.putFile(idfoto).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    //se otiene la direccion o enlace de descarga donde esta almacenada
                    final StorageReference file_name = FirebaseStorage.getInstance().getReference().child("fotos");
                    file_name.putFile(idfoto).addOnSuccessListener(taskSnapshot1 -> file_name.getDownloadUrl().addOnSuccessListener(uri1 ->{
                        String url=String.valueOf(uri1);

                        //se sube aqui al firebase realtime
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("foto",url);
                        database.child(idUser).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                   mProgressDialog.dismiss();
                                }else{
                                    Toast.makeText(getContext(),"No se pudo subir la foto", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } ));

                }
            });
        }

    }
}