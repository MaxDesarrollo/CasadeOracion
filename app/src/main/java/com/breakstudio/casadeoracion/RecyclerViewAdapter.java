package com.breakstudio.casadeoracion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import static com.breakstudio.casadeoracion.R.styleable.RecyclerView;

/**
 * Created by Jaime on 14/3/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Post> lista;
    ViewHolder vh;


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
        Glide.with(context)
                .load(lista.get(position).getThumbnail())
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
                Intent  intent = new Intent(context,DetalleNoticia.class);
                Bundle bundle=new Bundle();
                bundle.putString("Title", lista.get(pos).getTitle());
                bundle.putString("Thumbnail", lista.get(pos).getThumbnail());
                bundle.putString("Content", lista.get(pos).getContent());
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
        ItemCLickListener itemCLickListener;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tvDestacado);
            imageView = (ImageView) itemView.findViewById(R.id.ivDestacado);
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
}
