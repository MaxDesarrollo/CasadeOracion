package com.breakstudio.casadeoracion;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import android.graphics.Color;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Jaime on 10/3/2017.
 */

public class PostListAdapter extends BaseAdapter {
    private Context context;
    private List<Post> PostsLista;
    String fechaApi,fecha1Obtenida,hora,año,mes,dia,mesLiteral,fechaFinal;

    //Constructor
    public PostListAdapter(Context context, List<Post> postsLista) {
        this.context = context;
        PostsLista = postsLista;
    }

    @Override
    public int getCount() {
        return PostsLista.size();
    }

    @Override
    public Object getItem(int i) {
        return PostsLista.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context,R.layout.news_item,null);
        ImageView ivThumbnail = (ImageView) v.findViewById(R.id.ivThumbnail);
        TextView tvTitulo = (TextView) v.findViewById(R.id.tvTitulo);
        TextView tvFecha = (TextView) v.findViewById(R.id.fechaLista);
        TextView tvTexto = (TextView) v.findViewById(R.id.tvTexto);
        TextView tvComentarios = (TextView) v.findViewById(R.id.comentariosLista);
        tvComentarios.setText(String.valueOf(PostsLista.get(i).getCant_comentarios()));
        tvTitulo.setText(PostsLista.get(i).getTitle());

        //formatear fecha

        fechaApi = PostsLista.get(i).getFecha();
        StringTokenizer st = new StringTokenizer(fechaApi);
        while (st.hasMoreElements()){
            fecha1Obtenida = st.nextToken();
            Log.d("Fecha", fecha1Obtenida);
            hora = st.nextToken();
            Log.d("Hora",hora);

        }
        String[] parts = fecha1Obtenida.split("-");
        año = parts[0];
        mes = parts[1];
        dia = parts[2];
        ReemplazarMes();

        fechaFinal = dia + "-"+ mesLiteral + "-"+año;
        Log.d("Obtenido",fechaFinal);

        tvFecha.setText(fechaFinal);


        //Intercalar el color de fondo de los items
        if (i % 2 == 1) {
            v.setBackgroundColor(Color.TRANSPARENT);
        } else {
            v.setBackgroundColor(Color.parseColor("#efefef"));
        }
        Glide.with(context)
                .load(PostsLista.get(i).getThumbnail())
                .fitCenter()
                .crossFade()
                .dontAnimate()
                .dontTransform()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivThumbnail);
        //tvTexto.setText(PostsLista.get(i).getThumbnail());
        v.setTag(PostsLista.get(i));
        return v;
    }

    public  void ReemplazarMes(){
        switch (mes){
            case "01":
                mesLiteral = "Ene";
                break;

            case  "02":
                mesLiteral = "Feb";
                break;

            case  "03":
                mesLiteral = "Mar";
                break;

            case  "04":
                mesLiteral = "Abr";
                break;
            case  "05":
                mesLiteral = "May";
                break;
            case  "06":
                mesLiteral = "Jun";
                break;
            case  "07":
                mesLiteral = "Jul";
                break;
            case  "08":
                mesLiteral = "Ago";
                break;
            case  "09":
                mesLiteral = "Sep";
                break;
            case  "10":
                mesLiteral = "Oct";
                break;
            case  "11":
                mesLiteral = "Nov";
                break;
            case  "12":
                mesLiteral = "Dic";
                break;


        }

    }
}
