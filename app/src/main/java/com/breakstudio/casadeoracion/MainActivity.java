package com.breakstudio.casadeoracion;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;


public class MainActivity extends AppCompatActivity {
    MediaPlayer mp = new MediaPlayer();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ImageButton playBtn;
    private SeekBar Volumen;
    private boolean isPaused=false;
    private AudioManager audioManager;
    //Lista de para las canciones y adaptador
    private ListView lvCancion;
    private CancionListAdapter adapter;
    private List<Cancion> listaCanciones;
    private ImageButton shareButton;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setStatusBarTranslucent(true);
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
        listaCanciones.add(new Cancion("El pollito pio -","Sin Nombre"));
        listaCanciones.add(new Cancion("highway to hell -","Sin Nombre"));

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

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null || (AccessToken.getCurrentAccessToken()!=null) ) {

                } else {
                    goLoginScreen();

                }
                // ...
            }
        };
        //Play a la radio
        playBtn = (ImageButton)findViewById(R.id.play);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Cambiar(playBtn);
            if(!mp.isPlaying() || isPaused) {
                if(isPaused){mp.start();isPaused=false; Cambiar(playBtn);}else{
             AsyncTask<Void,Void,Void> task = new AsyncTask<Void, Void, Void>() {
                @Override
                protected void onPreExecute() {
                    pDialog.setTitle("Processing...");
                    pDialog.setMessage("Please wait.");
                    pDialog.setCancelable(false);
                    pDialog.setIndeterminate(true);
                    pDialog.show();
                    Cambiar(playBtn);
                }
                @Override
                protected Void doInBackground(Void... voids) {
                    mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    String URL ="http://78.129.187.73:4138";

                        try {
                            mp.setDataSource(URL);
                            mp.prepare();
                            mp.setVolume(50,50);
                            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mediaPlayer) {
                                    mp.start();
                                    pDialog.dismiss();
                                    isPaused=false;

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
            }else{mp.pause(); isPaused=true; Cambiar(playBtn);}
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
        else {
            //mp.pause();
            btn.setImageResource(R.drawable.playbutton_1x);
        }
    }

    /*public  void LeventarRadio(){

        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        String URL ="http://78.129.187.73:4138";


        try {
            mp.setDataSource(URL);
            mp.prepare();

        } catch (IllegalArgumentException e) {
            //Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        } catch (SecurityException e) {
            //Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        } catch (IllegalStateException e) {
           // Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }


        mp.setVolume(50,50);
        mp.start();

    }*/
    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
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
