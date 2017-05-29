package com.breakstudio.casadeoracion;

import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    LinearLayout lyComments,lyShaeredButton;
    public  CommentsListAdapter lvAdapter;
    public  String Title,Thumbnail,id,fecha,Content,Author,Date,contentComment,Link;
    int cant_comentario;
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
        Link = bundle.getString("Link");
        //Content = bundle.getString("Content");
        cant_comentario = bundle.getInt("CantComentario");
        //String Content = bundle.getString("Content");
        Log.i("Llega: ",Title);
        Log.i("Llega: ",Thumbnail);
        Log.d("Llega",id);
        Log.d("Llega",String.valueOf(cant_comentario));
        //Log.d("Cotenido",Content);
        //Log.i("Llega: ",Content);

        tvTitulo = (TextView)findViewById(R.id.detalleTitulo);
        tvDetalleComentarios = (TextView) findViewById(R.id.detalleComentarios);
        tvDetalleFecha = (TextView) findViewById(R.id.detalleFecha);
        lvComentario = (ListView) findViewById(R.id.lvComentario);
        btnComents = (ImageView) findViewById(R.id.btnComments);
        lyComments = (LinearLayout) findViewById(R.id.lyComments);
        lvComentario.setVisibility(View.GONE);
        lyShaeredButton = (LinearLayout) findViewById(R.id.LySharedButton);

        tvDetalleFecha.setText(fecha);
        tvDetalleComentarios.setText(String.valueOf(cant_comentario));

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
        //obtenerComentario();

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
                obtenerComentario();
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

        lyShaeredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareBody = Link;
                String shareSub = Title;
                intent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                intent.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(intent,"Comparte nuestro post utilizando..."));

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
                    Content = respuesta.getContent().toString();
                    wvContent.loadData(Content, "text/html; charset=utf-8", "utf-8");
                    Log.d("Contenido", Content);
                    Log.e(TAG, "TodoBien" + respuesta.toString());

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
                if (comments.size()>0){
                    lvAdapter = new CommentsListAdapter(getApplicationContext(),comments);
                    lvComentario.setAdapter(lvAdapter);

                    //Configura el alto del Listview lvNews dinamicamente segun el numero de items en el listado
                    /*ListView.LayoutParams lp = (ListView.LayoutParams) lvComentario.getLayoutParams();
                    //dpToPx convierte el alto 110dp a la cantidad en pixeles para tener un renderizado correcto
                    lp.height = comments.size()*dpToPx(1000);
                    //Aplica el nuevo layout del Listview
                    lvComentario.setLayoutParams(lp);*/
                }else {
                    Toast.makeText(getApplicationContext(),"No hay comentarios para Mostrar",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Contenido> call, Throwable t) {
                Log.e(TAG3,"TodoMal: "+ t.getMessage().toString());

            }
        });
    }


    public int pxToDp(int px) {
        float density = DetalleNoticia.this.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) px / density);
    }
    public int dpToPx(int dp) {
        float density = DetalleNoticia.this.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }




}
