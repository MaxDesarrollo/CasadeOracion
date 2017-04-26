package com.breakstudio.casadeoracion;

import android.nfc.Tag;
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

public class PredicaActivity extends AppCompatActivity {

    private  static  final String TAG = "PREDICA";
    private Retrofit retrofit;

    ListView lvPredica;
    private RecyclerView recyclerView;
    private ListaPredicaAdapter listaPredicaAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predica);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://hashtag.breakstudio.co/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        obtenerDatos();
    }

    public void obtenerDatos() {

        PostService service = retrofit.create(PostService.class);
        Call<PredicaRespuesta> predicaRespuestaCall = service.obtenerPredicas();
        predicaRespuestaCall.enqueue(new Callback<PredicaRespuesta>() {
            @Override
            public void onResponse(Call<PredicaRespuesta> call, Response<PredicaRespuesta> response) {
                if(response.isSuccessful()){
                    Log.e(TAG,"onResponse: Entra");
                    PredicaRespuesta predicaRespuesta = response.body();
                    lvPredica = (ListView)findViewById(R.id.lvPredica1);
                    ArrayList<Post> listaPost = predicaRespuesta.getPosts();
                    listaPredicaAdapter = new ListaPredicaAdapter(getApplicationContext(),listaPost);
                    lvPredica.setAdapter(listaPredicaAdapter);


                }
                else {
                    Log.e(TAG,"onResponse: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PredicaRespuesta> call, Throwable t) {
                Log.e(TAG,"onFailure: "+ t.getMessage());
            }
        });

    }
}
