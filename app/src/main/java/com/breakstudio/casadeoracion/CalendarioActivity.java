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
        obtenerDatos();
        //cambio
        //
    }

    private  static  final String TAG = "Calendario";
    private Retrofit retrofit;

    ListView lvCalendario;
    private RecyclerView recyclerView;
    private ListaCalendarioAdapter listaCalendarioAdapter;


    private void obtenerDatos() {

        PostService service = retrofit.create(PostService.class);
        Call<CalendarioRespuesta> calendarioRespuestaCall = service.obtenerCalenadario();

        calendarioRespuestaCall.enqueue(new Callback<CalendarioRespuesta>() {
            @Override
            public void onResponse(Call<CalendarioRespuesta> call, Response<CalendarioRespuesta> response) {
                if(response.isSuccessful()){
                    Log.e(TAG,"onResponse: ECalendario");
                    CalendarioRespuesta calendarioRespuesta = response.body();
                    lvCalendario = (ListView)findViewById(R.id.lvCalendario);
                    ArrayList<Post> listaPost = calendarioRespuesta.getPosts();
                    listaCalendarioAdapter = new ListaCalendarioAdapter(getApplicationContext(),listaPost);
                    lvCalendario.setAdapter(listaCalendarioAdapter);


                }
                else {
                    Log.e(TAG,"onResponse: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<CalendarioRespuesta> call, Throwable t) {
                Log.e(TAG,"onFailure: "+ t.getMessage());

            }
        });

    }
}
