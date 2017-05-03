package com.breakstudio.casadeoracion;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroActivity extends AppCompatActivity {

    private static final String TAG = "Firebase: ";
    EditText txtEmailRegistro;
    EditText txtUserRegistro;
    EditText txtPasswordRegistro;
    Button btnRegistro;
    private String name;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    //public ProgressBar pbar;
    private ProgressDialog pDialog;
    private Context MContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        //Fixes Panning when softkeyboard shows //
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        ////***************/////

        txtEmailRegistro = (EditText) findViewById(R.id.txtEmailRegistro);
        txtUserRegistro = (EditText) findViewById(R.id.txtUserRegistro);
        txtPasswordRegistro = (EditText) findViewById(R.id.txtPasswordRegistro);
        btnRegistro = (Button) findViewById(R.id.btnRegistro);
        mAuth = FirebaseAuth.getInstance();
        //pbar = (ProgressBar)findViewById(R.id.progress_bar);
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Un momento por favor");
        pDialog.setMessage("Registransdose...");


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog.show();
                //pbar.setVisibility(View.VISIBLE);
                createAccount(txtEmailRegistro.getText().toString(),txtPasswordRegistro.getText().toString());
            }
        });

    }
    protected void createAccount(String email,String pass){
        mAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //pbar.setVisibility(View.GONE);
                        pDialog.dismiss();
                        Log.d(TAG,"CrearUsuarioconEmail:"+task.isSuccessful());
                        if (!task.isSuccessful()){
                            //task.getException().toString();
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthWeakPasswordException e) {
                                Toast.makeText(RegistroActivity.this,"Password Debil",Toast.LENGTH_LONG).show();
                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(RegistroActivity.this,"Correo Invalido",Toast.LENGTH_LONG).show();
                            } catch(FirebaseAuthUserCollisionException e) {
                                Toast.makeText(RegistroActivity.this,"Ese correo ya existe",Toast.LENGTH_LONG).show();
                            } catch(FirebaseException e) {
                                Toast.makeText(RegistroActivity.this,"Error al registrar, por favor intente mas tarde",Toast.LENGTH_LONG).show();
                            } catch(Exception e) {
                                Log.e(TAG, e.getMessage());
                            }


                        }else{
                            FirebaseUser Usuario = task.getResult().getUser();
                            createNewUser(Usuario);
                            if(Usuario.getDisplayName()!=null){
                            Toast.makeText(RegistroActivity.this,"Bienvenido ",Toast.LENGTH_SHORT).show();
                            goMainActivity();
                            }else {
                                UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(txtUserRegistro.getText().toString())
                                        .build();
                                Usuario.updateProfile(profileUpdate);
                                goMainActivity();
                            }
                        }
                    }
                });

    }
    private void createNewUser(final FirebaseUser user) {
        //FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
        name = txtUserRegistro.getText().toString();
        UserProfileChangeRequest addProfileName = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        user.updateProfile(addProfileName)
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, user.getDisplayName());
                        }
                    }

                });
    }
    public void goMainActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }
}
