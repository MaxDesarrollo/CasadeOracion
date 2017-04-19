package com.breakstudio.casadeoracion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImagenesActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private  static  final String TAG = "IMAGENES";
    GridView gvImagenes;

    private  ListaImagenesAdapter listaImagenesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagenes);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://hashtag.breakstudio.co/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        obtenerDatos();

    }

    private void obtenerDatos() {

        PostService service = retrofit.create(PostService.class);
        Call<ImagenesRespuesta> imagenesRespuestaCall = service.obtenerImagenes();
        imagenesRespuestaCall.enqueue(new Callback<ImagenesRespuesta>() {
            @Override
            public void onResponse(Call<ImagenesRespuesta> call, Response<ImagenesRespuesta> response) {
                if(response.isSuccessful()){
                    Log.e(TAG,"onResponse : Entra");
                    ImagenesRespuesta imagenRespuesta = response.body();
                    gvImagenes = (GridView) findViewById(R.id.gvGaleria);
                    ArrayList<Post> listaPost = imagenRespuesta.getPosts();
                    listaImagenesAdapter = new ListaImagenesAdapter(getApplicationContext(),listaPost);
                    gvImagenes.setAdapter(listaImagenesAdapter);
                }

                else {
                    Log.e(TAG,"onResponse: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ImagenesRespuesta> call, Throwable t) {
                Log.e(TAG,"onFailure: "+ t.getMessage());
            }
        });

    }
}
