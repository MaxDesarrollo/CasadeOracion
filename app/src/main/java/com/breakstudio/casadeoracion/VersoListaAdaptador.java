package com.breakstudio.casadeoracion;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Marcelo on 6/9/2017.
 */

public class VersoListaAdaptador extends CursorAdapter {

    public VersoListaAdaptador(Context context, Cursor c, int flags) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.item_verso_lista,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvvlista = (TextView)view.findViewById(R.id.tvVerso);
        String verso = cursor.getString(cursor.getColumnIndexOrThrow("v"));
        tvvlista.setText(verso);

    }
}
