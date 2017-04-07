package com.breakstudio.casadeoracion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.breakstudio.casadeoracion.LoginActivity;

public class iniciarSesion extends AppCompatActivity {

    static final String TAG ="Login: " ;
    private TextView email;
    private TextView pass;
    private Button btnInicioSesion;
    private FirebaseAuth mAuth;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        email = (TextView) findViewById(R.id.loginEmail);
        pass = (TextView) findViewById(R.id.loginPassword);
        mAuth = FirebaseAuth.getInstance();
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Un momento por favor");
        pDialog.setMessage("Iniciando sesion...");
        btnInicioSesion = (Button) findViewById(R.id.btnInicSecion);
        btnInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog.show();
                IniniodeSecion(email.getText().toString(),pass.getText().toString());

            }
        });


    }
    public void IniniodeSecion(String email,String pass) {

        mAuth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            pDialog.dismiss();
                            Toast.makeText(iniciarSesion.this,"Inicio de sesion correcto",Toast.LENGTH_SHORT).show();
                            goMainActivity();
                        }else{
                            pDialog.dismiss();
                            Log.w(TAG, "signInWithEmail", task.getException());
                            Toast.makeText(iniciarSesion.this, "Usuario o Contraseña no validos.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void goMainActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }

}
