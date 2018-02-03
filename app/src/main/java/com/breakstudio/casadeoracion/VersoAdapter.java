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

public class VersoAdapter extends CursorAdapter {

    public VersoAdapter(Context context, Cursor c, int flags) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_verso,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvverso =(TextView) view.findViewById(R.id.tvverso);
        TextView tvnroVerso =(TextView) view.findViewById(R.id.tvnroVerso);
        String verso = cursor.getString(cursor.getColumnIndexOrThrow("v"));
        String texto = cursor.getString(cursor.getColumnIndexOrThrow("t"));
        tvnroVerso.setText(verso);
        tvverso.setText(texto);
    }
}
