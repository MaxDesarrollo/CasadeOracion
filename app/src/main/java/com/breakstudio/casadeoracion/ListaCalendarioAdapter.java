package com.breakstudio.casadeoracion;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by Marcelo on 7/4/2017.
 */

public class ListaCalendarioAdapter extends BaseAdapter{

    private Context context;
    private List<Post> PostsLista;

    public ListaCalendarioAdapter(Context context, List<Post> postsLista) {
        this.context = context;
        PostsLista = postsLista;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context,R.layout.item_predica,null);
        ImageView ivThumbnail = (ImageView) v.findViewById(R.id.ivThumbnail);
        TextView tvTitulo = (TextView) v.findViewById(R.id.tvTitulo);
        TextView tvTexto = (TextView) v.findViewById(R.id.tvTexto);
        tvTitulo.setText(PostsLista.get(i).getTitle());
        Glide.with(context)
                .load(PostsLista.get(i).getThumbnail())
                .fitCenter()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivThumbnail);
        //tvTexto.setText(PostsLista.get(i).getThumbnail());
        v.setTag(PostsLista.get(i));
        return v;
    }
}
