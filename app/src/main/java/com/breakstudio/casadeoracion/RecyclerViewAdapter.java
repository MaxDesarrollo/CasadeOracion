package com.breakstudio.casadeoracion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;
import java.util.StringTokenizer;

import static com.breakstudio.casadeoracion.R.styleable.RecyclerView;

/**
 * Created by Jaime on 14/3/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Post> lista;
    ViewHolder vh;
    String fechaApi,fecha1Obtenida,hora,año,mes,dia,mesLiteral,fechaFinal;
    int cant_comentarios;


    public RecyclerViewAdapter(Context context, List<Post> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_destacado,parent,false);
        vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        vh.textView.setText(lista.get(position).getTitle());
        vh.destacadoComentarios.setText(String.valueOf(lista.get(position).getCant_comentarios()));
        //cant_comentarios = String.valueOf(lista.get(position).getCant_comentarios());


        fechaApi = lista.get(position).getFecha();
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

        vh.fecha.setText(fechaFinal);



        Glide.with(context) .load(lista.get(position).getThumbnail())
                .centerCrop()
                .crossFade()
                .dontAnimate()
                .fitCenter()
                .dontTransform()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(vh.imageView);

        vh.setItemCLickListener(new ItemCLickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                //Toast.makeText(context,"click"+ pos,Toast.LENGTH_SHORT).show();
                String Id = lista.get(pos).getId();
                String Conntent = lista.get(pos).getContent();

                Intent  intent = new Intent(context,DetalleNoticia.class);
                Bundle bundle=new Bundle();
                bundle.putString("Title", lista.get(pos).getTitle());
                bundle.putString("Thumbnail", lista.get(pos).getThumbnail());
                bundle.putString("Content", lista.get(pos).getContent());
                bundle.putString("Id",Id);
                bundle.putString("Fecha",fechaFinal);
                bundle.putString("Link",lista.get(pos).getLink());
                //bundle.putString("Content",Conntent);
                cant_comentarios = lista.get(pos).getCant_comentarios();
                bundle.putInt("CantComentario",cant_comentarios);
                //bundle.putString("CantComentario",cant_comentarios);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView;
        ImageView imageView;
        TextView  destacadoComentarios;
        TextView fecha;
        ItemCLickListener itemCLickListener;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tvDestacado);
            imageView = (ImageView) itemView.findViewById(R.id.ivDestacado);
            destacadoComentarios = (TextView) itemView.findViewById(R.id.destacadoComentarios);
            fecha = (TextView) itemView.findViewById(R.id.destacadoFecha);
            itemView.setOnClickListener(this);

        }


        public void setItemCLickListener(ItemCLickListener itemCLickListener){
            this.itemCLickListener = itemCLickListener;
        }
        @Override
        public void onClick(View view) {
            itemCLickListener.onItemClick(view,getAdapterPosition());
        }
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
