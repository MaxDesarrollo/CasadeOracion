package com.breakstudio.casadeoracion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CalendarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);
    }

    private  static  final String TAG = "Calendario";
    private Retrofit retrofit;

    ListView lvCalendario;
    private RecyclerView recyclerView;
    private ListaCalendarioAdapter listaCalendarioAdapter;


    private void obtenerDatos() {


    }
}
