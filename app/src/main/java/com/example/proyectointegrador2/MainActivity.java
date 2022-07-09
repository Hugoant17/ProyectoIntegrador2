package com.example.proyectointegrador2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private EditText txtUsuario, txtPassword;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    String email, pass;

    //google
    Button btnIngresoGoogle;
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;


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

        //GOOGLE
        crearSolicitud();

        btnIngresoGoogle = (Button) findViewById(R.id.btnIngresoGoogle);

        //Evento al presionar
        btnIngresoGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInGoogle();
            }
        });


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
                                }else if(rol.equals("Vendedor")){
                                    startActivity(new Intent(getApplicationContext(),MenuVendedor.class));
                                    Toast.makeText(getApplicationContext(), "Bienvenido Vendedor", Toast.LENGTH_SHORT).show();
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
                            startActivity(new Intent(getApplicationContext(),MenuVendedor.class));
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



    //SOLICITUD GOOGLE
    private void crearSolicitud(){
        //Se configura el inicio de sesion
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id1))
                .requestEmail()
                .build();

        //creamos un googlesinginclient con las opciones especificadas por gso
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
    }
    //crear pantalla de google
    private void signInGoogle(){
        Intent signIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Resultado devuelto al iniciar la inteción desde GoogleSignInApi.getSignIntent
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                AutenticacionFirebase(account);
            }catch (ApiException e){
                Toast.makeText(this,"No se pudo conectar: "+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    //metodo para autenticar con firebase
    private void AutenticacionFirebase(GoogleSignInAccount account){

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Si inicio correctamente
                            FirebaseUser user = mAuth.getCurrentUser();

                            //El usuario inicio sesion por primera vez
                            if(task.getResult().getAdditionalUserInfo().isNewUser()){
                                String uid = user.getUid();
                                String correo = user.getEmail();
                                String nombre = user.getDisplayName();
                                String rol = "Cliente";

                                //aqui pasamos los datos
                                HashMap<Object,String> DatosUsuario = new HashMap<>();

                                DatosUsuario.put("nombre",nombre);
                                DatosUsuario.put("correo",correo);
                                DatosUsuario.put("direccion","");
                                DatosUsuario.put("dni","");
                                DatosUsuario.put("rol",rol);
                                DatosUsuario.put("foto","");

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference reference = database.getReference("Usuarios");
                                reference.child(uid).setValue(DatosUsuario);
                            }

                            //Ahora nos dirigimos a la actividad
                            startActivity(new Intent(MainActivity.this,MenuCliente.class));

                        } else{
                            Toast.makeText(MainActivity.this,"No se pudo iniciar",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }



}