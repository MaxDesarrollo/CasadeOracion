package com.breakstudio.casadeoracion;


import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class CapitulosFragment extends Fragment implements View.OnClickListener {

    Integer LibroId;
    Button btnCerrarCapitulos;
    DatabaseHelper databaseHelper;
    Cursor c;
    GridView lvCapitulos;

    public CapitulosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_capitulos, container, false);

        GetArg();

        //Toast.makeText(getContext(),"Libro ID" + LibroId, Toast.LENGTH_LONG).show();


        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        try {
            databaseHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        c = databaseHelper.queryCapitulos(String.valueOf(LibroId));


        BindUI(rootView);
        onClickList();

        return  rootView;
    }

    public  void BindUI(View view){

        btnCerrarCapitulos = (Button) view.findViewById(R.id.btnCerrarCapitulos);
        btnCerrarCapitulos.setOnClickListener(this);

        lvCapitulos = (GridView) view.findViewById(R.id.lvCapitulos);
        CapituloAdapter capituloAdapter = new CapituloAdapter(getContext(),c,0);
        lvCapitulos.setAdapter(capituloAdapter);


    }

    public void GetArg(){
        Bundle arg = getArguments();
        LibroId = arg.getInt("IdLibro");
    }

    public void onClickList(){

        lvCapitulos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Integer libroChapter = c.getInt(1);
                Bundle arg = new Bundle();
                arg.putInt("libroId",LibroId);
                arg.putInt("libroChapter",libroChapter);
                //arg.putString("idUserP",idUsuario);

                VersoFragment fragment = new VersoFragment();
                fragment.setArguments(arg);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.FrameBiblia, fragment, "fragment_verso");
                transaction.commit();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCerrarCapitulos:

                LibrosBibliaFragment fragment = new LibrosBibliaFragment();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.FrameBiblia, fragment, "fragment_libros_biblia");
                transaction.commit();
        }
    }


}
