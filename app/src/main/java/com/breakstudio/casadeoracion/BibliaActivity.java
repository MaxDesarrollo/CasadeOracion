package com.breakstudio.casadeoracion;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class BibliaActivity extends AppCompatActivity{

    Button  btnCerrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biblia);


        InicioFragment();
    }

    public void InicioFragment(){

        LibrosBibliaFragment fragment = new LibrosBibliaFragment();
        //fragment.setArguments(data);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.FrameBiblia, fragment, "fragment_libros_biblia");
        transaction.commit();
    }


}
