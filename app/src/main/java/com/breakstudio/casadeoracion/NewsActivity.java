package com.breakstudio.casadeoracion;

import android.app.ActionBar;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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
import android.webkit.WebView;
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


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.gson.Gson;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
    private Retrofit retrofit2;
    ListView lvPost,lvPredica;
    TextView textView4,bienvenido;
    //private List<Post> listaPosts;
    private PostListAdapter adapter;
    private RecyclerView recyclerView,rvNews;
    private RecyclerView.LayoutManager layoutManager;
    // el layoutManager1 es del recycler que pagina, por eso se declara de tipo LinearLayoutManager
    private android.support.v7.widget.LinearLayoutManager layoutManager1;
    private RecyclerView.Adapter rvAdapter;
    private TextView tvNombreUsuario;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    RecyclerViewAdapterPost rvNewsAdapter;
    //variables para la paginacion
    private int page2;
    private boolean aptoParaCargar;

    //private ListView lvpost;
    //private PredicaActivity predica;

    private  static  final String TAG2 = "POSTS";
    //private Retrofit retrofit;

    //ListView lvPredica;
    //private RecyclerView recyclerView;
    private ListaPredicaAdapter listaPredicaAdapter;




    //ListView lvPredica;
    //private RecyclerView recyclerView;
    //private ListaPredicaAdapter listaPredicaAdapter;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
        //SplashScreen

        //Obtener nombre de usuario de firebase
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            tvNombreUsuario = (TextView)findViewById(R.id.NombreUsuariotv);
            tvNombreUsuario.setText(name);
        }
        recyclerView = (RecyclerView) findViewById(R.id.rvDestacados);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        layoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);


        recyclerView.setLayoutManager(layoutManager);

        rvNews = (RecyclerView) findViewById(R.id.rvNews);
        rvNews.setLayoutManager(layoutManager1);





        rvNews.setLayoutManager(layoutManager1);
        rvNews.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy>0)
                {
                    int VisibleItemCount = layoutManager1.getChildCount();
                    int totalItemCount = layoutManager1.getItemCount();
                    int pastVisibleItems = layoutManager1.findFirstVisibleItemPosition();

                    if(aptoParaCargar)
                    {
                        if((VisibleItemCount + pastVisibleItems)>= totalItemCount)
                        {
                            Log.i(TAG,"Llegamos al final");
                            aptoParaCargar=false;
                            page2+=1;
                            getDatos(page2);
                        }
                    }
                }
            }
        });


        //lvPost = (ListView) findViewById(R.id.lvNews);
        lvPredica = (ListView) findViewById(R.id.lvPredica1);
        textView4 = (TextView) findViewById(R.id.textView4);
        lvPredica.setVisibility(View.GONE);
        bienvenido = (TextView) findViewById(R.id.bienvenido);
        //lvPost.setScrollContainer(false);
        obtenerHora();


        /*retrofit = new Retrofit.Builder()
                .baseUrl("http://hashtag.breakstudio.co/wp-json/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getArrayPost();*/

       //UrlBase
       //http://hashtag.breakstudio.co/api/

        retrofit = new Retrofit.Builder()
                .baseUrl("http://hashtag.breakstudio.co/wp-json/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ///getArrayPost();
        //aptoParaCargar=true;
        page2=1;
        obtenerDatos();
        obtenerPredicas();
        getDatos(page2);



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
                        if (lvPost.getVisibility() == View.VISIBLE ){
                            lvPost.setVisibility(View.GONE);
                            lvPredica.setVisibility(View.VISIBLE);
                            textView4.setText("PREDICAS");
                        }else if (lvPost.getVisibility() == View.GONE){
                            lvPost.setVisibility(View.VISIBLE);
                            lvPredica.setVisibility(View.GONE);
                            textView4.setText("ULTIMAS PUBLICACIONES");
                        }

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

        public void  loadNextDataFromApi(int offset) {

            PostService service = retrofit.create(PostService.class);
            Call<List<Post>> Call = service.getAllPost("true",page2);
            Call.enqueue(new Callback<List<Post>>() {
                @Override
                public void onResponse(Call<List<Post>> call, final Response<List<Post>> response) {
                    final List<Post> respuesta = response.body();
                    Log.e(TAG2,"TodoBien"+ respuesta.toString());
                    adapter = new PostListAdapter(getApplicationContext(),respuesta);
                    lvPost.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                    //Configura el alto del Listview lvNews dinamicamente segun el numero de items en el listado
                    LayoutParams lp = (LayoutParams) lvPost.getLayoutParams();
                    //dpToPx convierte el alto 110dp a la cantidad en pixeles para tener un renderizado correcto
                    lp.height = respuesta.size()*dpToPx(110);
                    //Aplica el nuevo layout del Listview
                    lvPost.setLayoutParams(lp);
                }

                @Override
                public void onFailure(Call<List<Post>> call, Throwable t) {
                    Log.e(TAG2,"TodoMal: "+ t.getCause());
                }
            });

        }


        private void obtenerDatos() {
      /*      PostService service = retrofit.create(PostService.class);
            Call<PostRespuesta> postRespuestaCall = service.getPost();
            postRespuestaCall.enqueue(new Callback<PostRespuesta>() {
                @Override
                public void onResponse(Call<PostRespuesta> call, retrofit2.Response<PostRespuesta> response) {
                    if(response.isSuccessful()){
                        lvPost = (ListView)findViewById(R.id.lvNews);
                        PostRespuesta postRespuesta = response.body();
                        PostRespuesta listaPosts = (PostRespuesta) postRespuesta.getPosts();
                        rvAdapter = new RecyclerViewAdapter(getApplicationContext(), (List<Post>) listaPosts);
                        recyclerView.setAdapter(rvAdapter);
                        adapter = new PostListAdapter(getApplicationContext(),(List<Post>) listaPosts);
                        lvPost.setAdapter(adapter);

                        //Configura el alto del Listview lvNews dinamicamente segun el numero de items en el listado
                        //LayoutParams lp = (LayoutParams) lvPost.getLayoutParams();
                        //dpToPx convierte el alto 110dp a la cantidad en pixeles para tener un renderizado correcto
                        //lp.height = listaPosts.size()*dpToPx(110);
                        //Aplica el nuevo layout del Listview
                        //lvPost.setLayoutParams(lp);

                    }else{
                        Log.e(TAG,"onResponse Error"+response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<PostRespuesta> call, Throwable t) {
                    Log.e(TAG,"onResponse Error"+t.getCause());
                }
            });*/
      PostService service = retrofit.create(PostService.class);
            Call<List<Post>> Call = service.getPost();
            Call.enqueue(new Callback<List<Post>>() {
                @Override
                public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                    List<Post> respuesta = response.body();
                    Log.e(TAG,"TodoBien"+ respuesta.toString());
                    rvAdapter = new RecyclerViewAdapter(getApplicationContext(), respuesta);
                    recyclerView.setAdapter(rvAdapter);
                    /*adapter = new PostListAdapter(getApplicationContext(),respuesta);
                    lvPost.setAdapter(adapter);*/

                    //Configura el alto del Listview lvNews dinamicamente segun el numero de items en el listado
                    /*LayoutParams lp = (LayoutParams) lvPost.getLayoutParams();
                    //dpToPx convierte el alto 110dp a la cantidad en pixeles para tener un renderizado correcto
                    lp.height = respuesta.size()*dpToPx(110);
                    //Aplica el nuevo layout del Listview
                    lvPost.setLayoutParams(lp);*/
                }

                @Override
                public void onFailure(Call<List<Post>> call, Throwable t) {
                    Log.e(TAG,"TodoMal: "+ t.getCause());
                }
            });

        }

    private void getDatos(final int page) {
        PostService service = retrofit.create(PostService.class);
        //Call<List<Post>> Call = service.getAllPost("true", page);
        Call<List<Post>> Call = service.obtenerListadePosts();
        Call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, final Response<List<Post>> response) {
                if(response.isSuccessful()) {

                    final List<Post> respuesta = response.body();

                    if (respuesta.size()>0){
                        aptoParaCargar = true;
                        Log.e(TAG2, "TodoBien" + respuesta.toString());
                        rvNewsAdapter = new RecyclerViewAdapterPost(getApplicationContext(), respuesta);
                        rvNews.setAdapter(rvNewsAdapter);
                    }else{
                        aptoParaCargar = false;
                        Toast.makeText(getApplicationContext(),"No hay mas contenido por mostrar",Toast.LENGTH_LONG).show();
                    }


                    //Configura el alto del Listview lvNews dinamicamente segun el numero de items en el listado
                    /*LayoutParams lp = (LayoutParams) lvPost.getLayoutParams();
                    //dpToPx convierte el alto 110dp a la cantidad en pixeles para tener un renderizado correcto
                    lp.height = respuesta.size() * dpToPx(110);
                    //Aplica el nuevo layout del Listview
                    lvPost.setLayoutParams(lp);*/
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.e(TAG2,"TodoMal: "+ t.getCause());
                aptoParaCargar=true;
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

    public  void obtenerHora(){
        Calendar calendar = new GregorianCalendar();
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        String hour = String.valueOf(hora);
        int tardeInicio = 13;
        //int min = 01;
        int tardeFin = 18;
        int nocheFin = 24;


        //bienvenido.setText(hour);
        if(hora >= tardeInicio  && hora < tardeFin ){
            bienvenido.setText("Buenas Tardes ");
            //Log.d("Saludo",bienvenido.getText().toString());

        }else  if (hora >= tardeFin && hora < nocheFin ){
            bienvenido.setText("Buenas Noches ");
        }else if (hora >= nocheFin  && hora < tardeInicio ){
            bienvenido.setText("Buenos Dias");
        }




    }
}
