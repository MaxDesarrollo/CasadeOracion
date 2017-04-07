package com.breakstudio.casadeoracion;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import static android.support.v7.appcompat.R.id.image;

public class DetalleNoticia extends AppCompatActivity {
private WebView wvContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_noticia);



        Bundle bundle =getIntent().getExtras();
        String Title = bundle.getString("Title");
        String Thumbnail = bundle.getString("Thumbnail");
        String Content = bundle.getString("Content");
        Log.i("Llega: ",Title);
        Log.i("Llega: ",Thumbnail);
        Log.i("Llega: ",Content);
        ImageView imageView = (ImageView)findViewById(R.id.image);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(Title);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        collapsingToolbarLayout.setContentScrimColor(Color.TRANSPARENT);
        collapsingToolbarLayout.setStatusBarScrimColor(Color.GREEN);
        wvContent = (WebView)findViewById(R.id.wvContent);
        wvContent.loadData(Content,"text/html; charset=utf-8","utf-8");
        Glide.with(this)
                .load(Thumbnail)
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);


    }
}
