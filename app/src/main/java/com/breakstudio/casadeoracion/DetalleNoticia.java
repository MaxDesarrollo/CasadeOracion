package com.breakstudio.casadeoracion;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetalleNoticia extends AppCompatActivity {
private WebView wvContent;
    TextView tvTitulo,tvDetalleFecha,tvDetalleComentarios;
    ListView lvComentario;
    ImageView btnComents;
    LinearLayout lyComments;
    public  CommentsListAdapter lvAdapter;
    public  String Title,Thumbnail,id,fecha,cant_comentario,Content,Author,Date,contentComment;
    public  static  final  String TAG="Comentarios";
    public  static  final  String TAG2="Comments";
    public  static  final  String TAG3="CommentsListView";
    Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_detalle_noticia);

        Bundle bundle = getIntent().getExtras();
        Title = bundle.getString("Title");
        Thumbnail = bundle.getString("Thumbnail");
        id = bundle.getString("Id");
        fecha = bundle.getString("Fecha");
        //Content = bundle.getString("Content");
        cant_comentario = bundle.getString("CantComentario");
        //String Content = bundle.getString("Content");
        Log.i("Llega: ",Title);
        Log.i("Llega: ",Thumbnail);
        Log.d("Llega",id);
        Log.d("Llega",cant_comentario);
        //Log.d("Cotenido",Content);
        //Log.i("Llega: ",Content);

        tvTitulo = (TextView)findViewById(R.id.detalleTitulo);
        tvDetalleComentarios = (TextView) findViewById(R.id.detalleComentarios);
        tvDetalleFecha = (TextView) findViewById(R.id.detalleFecha);
        lvComentario = (ListView) findViewById(R.id.lvComentario);
        btnComents = (ImageView) findViewById(R.id.btnComments);
        lyComments = (LinearLayout) findViewById(R.id.lyComments);
        lvComentario.setVisibility(View.GONE);

        tvDetalleFecha.setText(fecha);
        tvDetalleComentarios.setText(cant_comentario);

        tvTitulo.setText(Title);
        ImageView imageView = (ImageView)findViewById(R.id.image);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(null);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        collapsingToolbarLayout.setContentScrimColor(Color.TRANSPARENT);
        collapsingToolbarLayout.setStatusBarScrimColor(Color.TRANSPARENT);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://hashtag.breakstudio.co/wp-json/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        obtenerContenido();
        obtenerComentario();

        wvContent = (WebView)findViewById(R.id.wvContent);

        Glide.with(this)
                .load(Thumbnail)
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

        //Botton Cerrar
        Button bottonCerrar = (Button)findViewById(R.id.cerrarDetalle);
        bottonCerrar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                DetalleNoticia.super.onBackPressed();
            }
        });

        btnComents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wvContent.getVisibility() == View.VISIBLE){
                    wvContent.setVisibility(View.GONE);
                    lvComentario.setVisibility(View.VISIBLE);
                    lyComments.setBackgroundColor(Color.parseColor("#FF4081"));
                }else if (lvComentario.getVisibility() == View.VISIBLE){
                    lvComentario.setVisibility(View.GONE);
                    wvContent.setVisibility(View.VISIBLE);
                    lyComments.setBackgroundColor(Color.WHITE);
                }

            }
        });


    }

    public  void obtenerContenido(){

        PostService service = retrofit.create(PostService.class);
        Call<Contenido>  call = service.getContenido(id);
        call.enqueue(new Callback<Contenido>() {
            @Override
            public void onResponse(Call<Contenido> call, Response<Contenido> response) {
                Contenido respuesta = response.body();
                List<Comment> comments = respuesta.getComments();
                Log.d("Coments1",comments.toString());
                for (int i = 0; i < comments.size();i++){
                    Author = respuesta.getComments().get(i).getAuthor();
                    Log.d(TAG2,"Author: "+ Author);
                    Date = respuesta.getComments().get(i).getDate();
                    Log.d(TAG2,"Date: " + Date);
                    contentComment = respuesta.getComments().get(i).getContent();
                    Log.d(TAG2,"ContentComentary: " + contentComment);

                }

                Content = respuesta.getContent().toString();
                wvContent.loadData(Content,"text/html; charset=utf-8","utf-8");
                Log.d("Contenido",Content);
                Log.e(TAG,"TodoBien"+ respuesta.toString());
            }

            @Override
            public void onFailure(Call<Contenido> call, Throwable t) {
                Log.e(TAG,"TodoMal: "+ t.getMessage().toString());

            }
        });
    }

    public  void obtenerComentario(){

        PostService service = retrofit.create(PostService.class);
        Call<Contenido>  call = service.getContenido(id);
        call.enqueue(new Callback<Contenido>() {
            @Override
            public void onResponse(Call<Contenido> call, Response<Contenido> response) {
                Contenido  respuesta = response.body();
                List<Comment> comments = respuesta.getComments();
                Log.e(TAG3,"TodoBien"+ respuesta.toString());
                Log.e(TAG3,"Comentarios"+ comments.toString());
                lvAdapter = new CommentsListAdapter(getApplicationContext(),comments);
                lvComentario.setAdapter(lvAdapter);
                /*for (int i = 0; i < comments.size();i++){
                    Author = respuesta.getComments().get(i).getAuthor();
                    Log.d(TAG3,"Author: "+ Author);
                    Date = respuesta.getComments().get(i).getDate();
                    Log.d(TAG3,"Date: " + Date);
                    contentComment = respuesta.getComments().get(i).getContent();
                    Log.d(TAG3,"ContentComentary: " + contentComment);
                    lvAdapter = new CommentsListAdapter(getApplicationContext(),comments);
                    lvComentario.setAdapter(lvAdapter);

                }*/

            }

            @Override
            public void onFailure(Call<Contenido> call, Throwable t) {
                Log.e(TAG3,"TodoMal: "+ t.getMessage().toString());

            }
        });
    }




}
