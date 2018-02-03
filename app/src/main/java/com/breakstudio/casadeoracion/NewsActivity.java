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
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import org.w3c.dom.Text;

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

    private static final String TAG = "RespuestaWP";
    private Retrofit retrofit;
    private Retrofit retrofit2;
    ListView lvPost,lvPredica;
    TextView tituloDestacados,tituloListado,bienvenido;
    //private List<Post> listaPosts;
    private PostListAdapter adapter;
    private RecyclerView rvDestacados,rvNews, rvPredicas, rvCalendario;
    private RecyclerView.LayoutManager layoutManager;
    // el layoutManager1 es del recycler que pagina, por eso se declara de tipo LinearLayoutManager
    private android.support.v7.widget.LinearLayoutManager layoutManager1,lmPredicas,lmCalendario;
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


    private DrawerLayout drawer;
    private NavigationView nav;
    private Button filterWhite;
    private ScrollView mainScrollView;

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
            tvNombreUsuario = (TextView) findViewById(R.id.NombreUsuariotv);
            tvNombreUsuario.setText(name);
        }
        rvDestacados = (RecyclerView) findViewById(R.id.rvDestacados);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        lmPredicas = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        lmCalendario = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);


        rvDestacados.setLayoutManager(layoutManager);

        rvNews = (RecyclerView) findViewById(R.id.rvNews);
        rvPredicas = (RecyclerView) findViewById(R.id.rvPredicas);
        rvPredicas.setLayoutManager(lmPredicas);
        rvCalendario = (RecyclerView) findViewById(R.id.rvCalendario);
        rvCalendario.setLayoutManager(lmCalendario);

        //MARK: DRAWER LAYOUT SETUP
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setScrimColor(Color.argb(165, 255, 255, 255));
        drawer.setDrawerElevation(0f);

        nav = (NavigationView) findViewById(R.id.nav_view);
        nav.setItemIconTintList(null);
        //DRAWER LAYOUT SETUP END

        //MARK: FILTER ICON AND SCROLL - Hide and show on header
        filterWhite = (Button) findViewById(R.id.btnFilter);
        mainScrollView = (ScrollView) findViewById(R.id.mainScrollView);
        mainScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = mainScrollView.getScrollY();
                if (scrollY > 60) {
                    filterWhite.setVisibility(View.VISIBLE);
                } else {
                    filterWhite.setVisibility(View.INVISIBLE);
                }
            }
        });
        //FILTER ICON AND SCROLL END

        rvNews.setLayoutManager(layoutManager1);
        rvNews.setNestedScrollingEnabled(false);
        rvNews.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (dy > 0) {
                        int VisibleItemCount = layoutManager1.getChildCount();
                        int totalItemCount = layoutManager1.getItemCount();
                        int pastVisibleItems = layoutManager1.findFirstVisibleItemPosition();

                        if (aptoParaCargar) {
                            if ((VisibleItemCount + pastVisibleItems) >= totalItemCount) {
                                Log.i(TAG, "Llegamos al final");
                                aptoParaCargar = false;
                                page2 += 1;
                                getDatos(page2);
                            }
                        }
                    }
                }
            });


            //lvPost = (ListView) findViewById(R.id.lvNews);
            //lvPredica = (ListView) findViewById(R.id.lvPredica1);
            tituloDestacados = (TextView) findViewById(R.id.tituloDestacados);
            tituloListado = (TextView) findViewById(R.id.tituloListado);
            rvPredicas.setVisibility(View.GONE);
            rvCalendario.setVisibility(View.GONE);
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
            page2 = 1;
            obtenerDatos();
            obtenerPredicas();
            obtenerCalendario();
            getDatos(page2);
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
                    rvDestacados.setAdapter(rvAdapter);
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
            Call<List<Post>> predicaRespuestaCall = service.obtenerPredicas();
            predicaRespuestaCall.enqueue(new Callback<List<Post>>() {
                @Override
                public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                    if(response.isSuccessful()) {
                        final List<Post> respuesta = response.body();
                        rvNewsAdapter = new RecyclerViewAdapterPost(getApplicationContext(), respuesta);
                        rvPredicas.setAdapter(rvNewsAdapter);
                    }
                    Log.e(TAG2,"Predicas Bien: "+ response.body().toString());
                }

                @Override
                public void onFailure(Call<List<Post>> call, Throwable t) {
                    Log.e(TAG2,"Predicas Mal: "+ t.getCause());
                }
            });
        }
    private  void obtenerCalendario(){
        PostService service = retrofit.create(PostService.class);
        Call<List<Post>> calendarioRespuestaCall = service.obtenerCalendario();
        calendarioRespuestaCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(response.isSuccessful()) {
                    final List<Post> respuesta = response.body();
                    rvNewsAdapter = new RecyclerViewAdapterPost(getApplicationContext(), respuesta);
                    rvCalendario.setAdapter(rvNewsAdapter);
                }
                Log.e(TAG2,"Predicas Bien: "+ response.body().toString());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.e(TAG2,"Predicas Mal: "+ t.getCause());
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
    //MARK: CERRAR LISTADO
    public void cerrarListado(View view){
        NewsActivity.super.onBackPressed();
        //Intent intent = new Intent(this,MainActivity.class);
        //startActivity(intent);
    }
    //CERRAR LISTADO END

    //MARK: NUEVO FILTRO MENU
    public void abrirFiltro(View view){
        drawer.openDrawer(GravityCompat.END);
    }
    public void cerrarFiltro(View view){
        drawer.closeDrawer(GravityCompat.END);
    }

    public void manejarFiltro(View view){
        TextView selectedFilter = (TextView) view;
        Boolean isSelected = selectedFilter.isSelected();
        String filterName = selectedFilter.getTag().toString();

        if (isSelected){
            selectedFilter.setSelected(false);
            selectedFilter.setBackgroundColor(Color.TRANSPARENT);
            triggerFiltros("default");
            cerrarFiltro(view);
        }else{
            selectedFilter.setSelected(true);
            selectedFilter.setBackgroundColor(getResources().getColor(R.color.backgroundRadio));
            toggleFiltros(filterName);
            triggerFiltros(filterName);
            cerrarFiltro(view);
        }
        Log.d("Selected", isSelected.toString());
    }
    private void toggleFiltros(String selectedTextView){
        RelativeLayout filterContainer = (RelativeLayout) findViewById(R.id.filterContainer);
        if (filterContainer != null){
            for (int x = 0; x < filterContainer.getChildCount(); x++){
                TextView currentTextView = (TextView) filterContainer.getChildAt(x);
                String currentTextViewTag = currentTextView.getTag().toString();
                Log.d("toggle",currentTextViewTag);
                if(!currentTextViewTag.equals(selectedTextView) && !currentTextViewTag.equals("filtroCerrar")){
                    currentTextView.setBackgroundColor(Color.TRANSPARENT);
                    currentTextView.setSelected(false);
                }
            }
        }
    }
    private void triggerFiltros(String selectedFilter){
        switch (selectedFilter) {
            case "filtroNoticias":
                tituloDestacados.setVisibility(View.GONE);
                rvDestacados.setVisibility(View.GONE);
                tituloListado.setText("NOTICIAS RECIENTES");
                break;
            case "filtroImagenes":
                tituloDestacados.setVisibility(View.GONE);

                rvDestacados.setVisibility(View.GONE);
                tituloListado.setText("GALERIA DE IMAGENES");
                break;
            case "filtroCalendario":
                tituloDestacados.setVisibility(View.GONE);

                rvDestacados.setVisibility(View.GONE);
                tituloListado.setText("CALENDARIO DE EVENTOS");
                break;
            case "filtroPredicas":
                tituloDestacados.setVisibility(View.GONE);

                rvDestacados.setVisibility(View.GONE);
                tituloListado.setText("PREDICAS DESCARGABLES");


                break;
            case "filtroVideos":
                tituloDestacados.setVisibility(View.GONE);

                rvDestacados.setVisibility(View.GONE);
                tituloListado.setText("GALERIA DE VIDEOS");
                break;
            case "default":
                tituloDestacados.setVisibility(View.VISIBLE);

                rvDestacados.setVisibility(View.VISIBLE);
                tituloListado.setText("ULTIMAS PUBLICACIONES");
                break;
        }
    }
    //NUEVO FILTRO MENU END

    //MARK: SETTINGS
    public void abrirSettings(View view){
        Intent intent = new Intent(this,SettingsActivity.class);
        startActivity(intent);
    }
    //SETTINGS END
}
