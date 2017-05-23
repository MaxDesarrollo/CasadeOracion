package com.breakstudio.casadeoracion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by Marcelo on 22/05/2017.
 */

public class RecyclerViewAdapterPost extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Post> lista;
    ViewHolder vh;

    String fechaApi,fecha1Obtenida,hora,año,mes,dia,mesLiteral,fechaFinal,cant_comentarios;

    public RecyclerViewAdapterPost(Context context, List<Post> lista) {
        this.context = context;
        this.lista = lista;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.news_item,parent,false);
        vh = new RecyclerViewAdapterPost.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        /*vh.tvTitulo.setText(lista.get(position).getTitle());
        vh.tvFechaLista.setText(lista.get(position).getFecha());
        vh.comentarioLista.setText(lista.get(position).getCant_comentarios());*/

        vh.tvComentarios.setText(String.valueOf(lista.get(position).getCant_comentarios()));
        vh.tvTitulo.setText(lista.get(position).getTitle());
        vh.tvFecha.setText(lista.get(position).getFecha());


        Glide.with(context) .load(lista.get(position).getThumbnail())
                .centerCrop()
                .crossFade()
                .dontAnimate()
                .fitCenter()
                .dontTransform()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(vh.ivThumbnail);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvTitulo,tvFecha,tvTexto,tvComentarios;
        ImageView ivThumbnail;


        ItemCLickListener itemCLickListener;
        public ViewHolder(View itemView) {
            super(itemView);
             ivThumbnail = (ImageView) itemView.findViewById(R.id.ivThumbnail);
             tvTitulo = (TextView) itemView.findViewById(R.id.tvTitulo);
             tvFecha = (TextView) itemView.findViewById(R.id.fechaLista);
             tvTexto = (TextView) itemView.findViewById(R.id.tvTexto);
             tvComentarios = (TextView) itemView.findViewById(R.id.comentariosLista);
            itemView.setOnClickListener(this);

            /*vh.setItemCLickListener(new ItemCLickListener() {
                @Override
                public void onItemClick(View v, int pos) {
                    //Toast.makeText(context,"click"+ pos,Toast.LENGTH_SHORT).show();
                    String Id = lista.get(pos).getId();
                    String Conntent = lista.get(pos).getContent();
                    Intent intent = new Intent(context,DetalleNoticia.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("Title", lista.get(pos).getTitle());
                    bundle.putString("Thumbnail", lista.get(pos).getThumbnail());
                    bundle.putString("Content", lista.get(pos).getContent());
                    bundle.putString("Id",Id);
                    bundle.putString("Fecha",fechaFinal);
                    //bundle.putString("Content",Conntent);
                    bundle.putString("CantComentario",cant_comentarios);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);
                }
            });*/

        }


        public void setItemCLickListener(ItemCLickListener itemCLickListener){
            this.itemCLickListener = itemCLickListener;
        }
        @Override
        public void onClick(View view) {
            itemCLickListener.onItemClick(view,getAdapterPosition());
        }
    }
}
