package com.breakstudio.casadeoracion;

import android.app.ActionBar;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.util.DisplayMetrics;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NewsActivity extends AppCompatActivity{

    FloatingActionButton fab_menu,fab_noticias,fab_imagenes,fab_videos,fab_calendario,fab_predicas;
    Animation FabOpen,FabClose,FabantiClockWise,FabClockWise;
    boolean isOpen = false;

    private static final String TAG = "RespuestaWP";
    private Retrofit retrofit;
    ListView lvPost,lvPredica;
    TextView textView4;
    //private List<Post> listaPosts;
    private PostListAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter rvAdapter;
    //private ListView lvpost;
    //private PredicaActivity predica;

    private  static  final String TAG2 = "PREDICA";
    //private Retrofit retrofit;

    //ListView lvPredica;
    //private RecyclerView recyclerView;
    private ListaPredicaAdapter listaPredicaAdapter;

    //ListView lvPredica;
    //private RecyclerView recyclerView;
    //private ListaPredicaAdapter listaPredicaAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarTranslucent(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        //Configura el color del statusbar onCreate
        Window window = NewsActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.parseColor("#1e8bb3"));
        ////////////////////

        recyclerView = (RecyclerView) findViewById(R.id.rvDestacados);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        lvPost = (ListView) findViewById(R.id.lvNews);
        lvPredica = (ListView) findViewById(R.id.lvPredica1);
        textView4 = (TextView) findViewById(R.id.textView4);
        lvPredica.setVisibility(View.GONE);
        //lvPost.setScrollContainer(false);




        retrofit = new Retrofit.Builder()
                .baseUrl("http://hashtag.breakstudio.co/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        obtenerDatos();
        obtenerPredicas();




        fab_menu = (FloatingActionButton)findViewById(R.id.fab_menu);
        fab_imagenes = (FloatingActionButton) findViewById(R.id.fab_imagenes);
        fab_noticias = (FloatingActionButton) findViewById(R.id.fab_noticias);
        fab_videos = (FloatingActionButton) findViewById(R.id.fab_videos);
        fab_calendario = (FloatingActionButton) findViewById(R.id.fab_calendario);
        fab_predicas = (FloatingActionButton) findViewById(R.id.fab_predicas);
        FabOpen = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        FabClose =  AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        FabantiClockWise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_anticlockwise);
        FabClockWise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);

        fab_menu.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void  onClick(View v){


                if(isOpen)
                {
                    fab_predicas.startAnimation(FabClose);
                    fab_imagenes.startAnimation(FabClose);
                    fab_videos.startAnimation(FabClose);
                    fab_calendario.startAnimation(FabClose);
                    fab_noticias.startAnimation(FabClose);
                    fab_predicas.setClickable(false);
                    fab_imagenes.setClickable(false);
                    fab_videos.setClickable(false);
                    fab_calendario.setClickable(false);
                    fab_noticias.setClickable(false);
                    fab_menu.setVisibility(View.VISIBLE);
                    isOpen = false;

                }
                else
                {
                    fab_predicas.startAnimation(FabOpen);
                    fab_imagenes.startAnimation(FabOpen);
                    fab_videos.startAnimation(FabOpen);
                    fab_calendario.startAnimation(FabOpen);
                    fab_noticias.startAnimation(FabOpen);
                    fab_predicas.setClickable(true);
                    fab_imagenes.setClickable(true);
                    fab_videos.setClickable(true);
                    fab_calendario.setClickable(true);
                    fab_noticias.setClickable(true);
                    fab_menu.setVisibility(View.VISIBLE);

                    isOpen = true;
                }


                fab_noticias.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //here
                        startActivity(new Intent(NewsActivity.this,newsFloatActivity.class));
                        // or:
                        //startActivity(new Intent(v.getContext(),newsFloatActivity.class));

                    }
                });

                fab_predicas.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //here
                        //startActivity(new Intent(NewsActivity.this,PredicaActivity.class));
                        // or:
                        //startActivity(new Intent(v.getContext(),PredicaActivity.class));
                       lvPost.setVisibility(View.GONE);
                        lvPredica.setVisibility(View.VISIBLE);
                        textView4.setText("PREDICAS");
                        //predica.obtenerDatos();

                    }
                });

                fab_videos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //here
                        //startActivity(new Intent(NewsActivity.this,Video2Activity.class));
                        // or:
                        //startActivity(new Intent(v.getContext(),VideosActivity.class));
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/user/casadeoracionbolivia/videos")));
                    }
                });

                fab_imagenes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //here
                        startActivity(new Intent(NewsActivity.this,ImagenesActivity.class));
                        // or:
                        //startActivity(new Intent(v.getContext(),ImagenesActivity.class));
                    }
                });

                fab_calendario.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //here
                        startActivity(new Intent(NewsActivity.this,CalendarioActivity.class));
                        // or:
                        //startActivity(new Intent(v.getContext(),CalendarioActivity.class));
                    }
                });

            }
        });


        }





        private void obtenerDatos() {
            PostService service = retrofit.create(PostService.class);
            Call<PostRespuesta> postRespuestaCall = service.obtenerListadePosts();
            postRespuestaCall.enqueue(new Callback<PostRespuesta>() {
                @Override
                public void onResponse(Call<PostRespuesta> call, retrofit2.Response<PostRespuesta> response) {
                    if(response.isSuccessful()){
                        lvPost = (ListView)findViewById(R.id.lvNews);
                        PostRespuesta postRespuesta = response.body();
                        ArrayList<Post> listaPosts = postRespuesta.getPosts();
                        rvAdapter = new RecyclerViewAdapter(getApplicationContext(),listaPosts);
                        recyclerView.setAdapter(rvAdapter);
                        adapter = new PostListAdapter(getApplicationContext(),listaPosts);
                        lvPost.setAdapter(adapter);

                        //Configura el alto del Listview lvNews dinamicamente segun el numero de items en el listado
                        LayoutParams lp = (LayoutParams) lvPost.getLayoutParams();
                        //dpToPx convierte el alto 110dp a la cantidad en pixeles para tener un renderizado correcto
                        lp.height = listaPosts.size()*dpToPx(110);
                        //Aplica el nuevo layout del Listview
                        lvPost.setLayoutParams(lp);

                    }else{
                        Log.e(TAG,"onResponse "+response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<PostRespuesta> call, Throwable t) {

                }
            });
        }

        private  void obtenerPredicas(){
            PostService service = retrofit.create(PostService.class);
            Call<PredicaRespuesta> predicaRespuestaCall = service.obtenerPredicas();
            predicaRespuestaCall.enqueue(new Callback<PredicaRespuesta>() {
                @Override
                public void onResponse(Call<PredicaRespuesta> call, Response<PredicaRespuesta> response) {
                    if(response.isSuccessful()){
                        Log.e(TAG2,"onResponse : Entra Predica");
                        PredicaRespuesta predicaRespuesta = response.body();
                        lvPredica = (ListView) findViewById(R.id.lvPredica1);
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


        public int pxToDp(int px) {
            float density = NewsActivity.this.getResources()
                    .getDisplayMetrics()
                    .density;
            return Math.round((float) px / density);
        }
        public int dpToPx(int dp) {
            float density = NewsActivity.this.getResources()
                    .getDisplayMetrics()
                    .density;
            return Math.round((float) dp * density);
        }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
