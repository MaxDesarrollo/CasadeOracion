package com.breakstudio.casadeoracion;


import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class LibrosBibliaFragment extends Fragment implements View.OnClickListener {

    LibrosAdapter librosAdapter;
    ListView rcLibrosBiblia;
    Cursor c;
    Button btnCerrar;

    public LibrosBibliaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_libros_biblia, container, false);


        //Toast.makeText(getContext(),"Fragment Biblia",Toast.LENGTH_LONG).show();

        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        try {
            databaseHelper.createDataBase();
        }catch (IOException ioe){
            throw new Error("No se pudo crear");
        } try {
            databaseHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        c = databaseHelper.queryBooks();



        //int LibroId = c.getInt(0);
       // c = databaseHelper.queryCantCapitulos(String.valueOf(LibroId));

        Log.d("Cursor",c.getColumnName(1));
        BindUI(rootView);
        onCLikList();
        //OnClick();
        return rootView;

    }

    public void BindUI(View view){
        rcLibrosBiblia = (ListView) view.findViewById(R.id.rcLibrosBiblia);
        librosAdapter = new LibrosAdapter(getContext(),c,0);
        rcLibrosBiblia.setAdapter(librosAdapter);

        btnCerrar = (Button) view.findViewById(R.id.btnCerrarBiblia);
        btnCerrar.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCerrarBiblia:
                //Toast.makeText(getContext(),"Click en cerrar",Toast.LENGTH_LONG).show();
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
        }
    }

    public  void onCLikList(){

        rcLibrosBiblia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Integer libroId = c.getInt(0);
                Bundle arg = new Bundle();
                arg.putInt("IdLibro",libroId);
                //arg.putString("idUserP",idUsuario);

                CapitulosFragment fragment = new CapitulosFragment();
                fragment.setArguments(arg);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.FrameBiblia, fragment, "fragment_capitulos");
                transaction.commit();

            }
        });

    }



}
