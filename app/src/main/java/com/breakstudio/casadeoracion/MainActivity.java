package com.breakstudio.casadeoracion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;


public class MainActivity extends AppCompatActivity {
    MediaPlayer mp = new MediaPlayer();
    private  final String Tag="Data";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ImageButton playBtn;
    private SeekBar Volumen;
    private boolean isPlaying=false;
    private AudioManager audioManager;
    //Lista de para las canciones y adaptador
    private ListView lvCancion;
    private CancionListAdapter adapter;
    private List<Cancion> listaCanciones;
    private ImageButton shareButton;
    private ProgressDialog pDialog;
    MediaMetadataRetriever data;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Configura el color del statusbar onCreate
        Window window = MainActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.parseColor("#1e8bb3"));
        ////////////////////////////

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null || (AccessToken.getCurrentAccessToken()!=null) ) {
                    Log.d("Conchudo","EL nombre es:"+user.getDisplayName());
                } else {
                    goLoginScreen();

                }
                // ...
            }
        };
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pDialog = new ProgressDialog(this);

        //Boton de compartir
        shareButton = (ImageButton)findViewById(R.id.share);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareIt();
            }


        });
        //Lista de canciones
        lvCancion = (ListView)findViewById(R.id.lvCanciones);
        listaCanciones = new ArrayList<>();
        //aqui se obtine la data de las canciones

        listaCanciones.add(new Cancion("Cancion 1","Artista 1"));
        listaCanciones.add(new Cancion("Cancion 2","Artista 2"));
        listaCanciones.add(new Cancion("Cancion 3","Artista 3"));
        listaCanciones.add(new Cancion("Cancion 4","Artista 4"));
        listaCanciones.add(new Cancion("Cancion 5","Artista 5"));


        //inicializamos el adaptador
        adapter = new CancionListAdapter(getApplicationContext(),listaCanciones);
        lvCancion.setAdapter(adapter);

        //instancia de Auth Firebase
        mAuth = FirebaseAuth.getInstance();
        //Seteo de tipo de stream del media player
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //Ajuste del SeekBar para el volumen
        Volumen = (SeekBar) findViewById(R.id.seekbarVolumen);
        Volumen.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        Volumen.setProgress(10);
        Volumen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress, 0);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        //Play a la radio
        playBtn = (ImageButton)findViewById(R.id.play);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               // Cambiar(playBtn);
            if(!mp.isPlaying() || isPlaying) {
                if(isPlaying){
                    mp.start();
                    isPlaying=false;
                    Cambiar(playBtn);
                }
                else{
                 AsyncTask<Void,Void,Void> task = new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected void onPreExecute() {
                        pDialog.setTitle("Processing...");
                        pDialog.setMessage("Please wait.");
                        pDialog.setCancelable(true);
                        pDialog.setIndeterminate(true);

                        pDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mp.pause();
                            isPlaying=true;
                            Cambiar(playBtn);
                            Toast.makeText(getApplication(),"Se cancelo el servicio",Toast.LENGTH_LONG).show();
                        }
                    });
                    pDialog.show();
                    Cambiar(playBtn);
                }
                @Override
                protected Void doInBackground(Void... voids) {
                    mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    String URL ="http://66.85.88.174/hot108";
                    //Stream Maniantal
                    //http://78.129.187.73:4138

                        try {
                            mp.setDataSource(URL);
                            mp.prepare();
                            mp.setVolume(50,50);
                            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mediaPlayer) {
                                    mp.start();
                                    pDialog.dismiss();
                                    isPlaying=false;
                                   /*String artist = data.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                                    Log.d(Tag,"Artist "+artist);*/
                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    return null;
                }
                    @Override
                    protected void onPostExecute(Void result) {


                    }
                };

                if(!mp.isPlaying())
                    task.execute((Void[])null);}
            }else{
                mp.pause();
                isPlaying=true;
                Cambiar(playBtn);
            }
            }
        });

    }

    @Override
    public void onBackPressed(){
        this.moveTaskToBack(true);
    }
    private void goLoginScreen() {
        Intent intent = new Intent(this,LoginActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void Cambiar(View view) {

        ImageButton btn = (ImageButton)view;
        //btn.setImageResource(R.drawable.pausebutton_1x);


        if(!mp.isPlaying()) {
           // LeventarRadio();
            btn.setImageResource(R.drawable.pausebutton_1x);
        }
        else if (mp.isPlaying()){
            //mp.pause();
            btn.setImageResource(R.drawable.playbutton_1x);
        }
    }


    private void shareIt(){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "URL de la aplicacion en la tienda.");
        shareIntent.setType("text/plain");
        startActivity(shareIntent);
    }

    public void goNews(View view) {
        Intent intent = new Intent(this,NewsActivity.class);
        startActivity(intent);
    }

    public void Logout(View view) {
        FirebaseAuth.getInstance().signOut();
        goLoginScreen();

    }
}
