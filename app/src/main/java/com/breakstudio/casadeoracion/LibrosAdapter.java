package com.breakstudio.casadeoracion;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Marcelo on 4/9/2017.
 */

public class LibrosAdapter extends CursorAdapter {

    public LibrosAdapter(Context context, Cursor c ,int flags) {super(context, c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_biblia,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvLibro =(TextView) view.findViewById(R.id.txtNombreLibro);
       TextView tvCantidad = (TextView) view.findViewById(R.id.txtCantidadLibro);


        String nombre = cursor.getString(cursor.getColumnIndexOrThrow("name"));


        String cantidad = cursor.getString(cursor.getColumnIndexOrThrow("cant_chaps"));



        tvLibro.setText(nombre);
        tvCantidad.setText(cantidad);
    }
}
