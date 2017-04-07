package com.breakstudio.casadeoracion;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jaime on 7/3/2017.
 */

public class CancionListAdapter extends BaseAdapter {
    private Context context;
    private List<Cancion> CancionesLista;

    //Constructor

    public CancionListAdapter(Context context, List<Cancion> cancionesLista) {
        this.context = context;
        CancionesLista = cancionesLista;
    }

    @Override
    public int getCount() {
        return CancionesLista.size();
    }

    @Override
    public Object getItem(int i) {
        return CancionesLista.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context,R.layout.lista_canciones,null);
        TextView tvCancion = (TextView)v.findViewById(R.id.tvCancion);
        TextView tvArtista = (TextView)v.findViewById(R.id.tvArtista);
        tvCancion.setText(CancionesLista.get(i).getNombre());
        tvArtista.setText(CancionesLista.get(i).getArtista());
        v.setTag(CancionesLista.get(i));
        return v;
    }
}
