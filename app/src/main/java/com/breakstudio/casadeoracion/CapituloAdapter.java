package com.breakstudio.casadeoracion;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Marcelo on 5/9/2017.
 */

public class CapituloAdapter extends CursorAdapter{

    public CapituloAdapter(Context context, Cursor c, int flags) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_capitulo,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvCapitulo =(TextView) view.findViewById(R.id.libro_capitulo);
        String capitulo = cursor.getString(cursor.getColumnIndexOrThrow("chapter"));
        tvCapitulo.setText(capitulo);
    }
}
