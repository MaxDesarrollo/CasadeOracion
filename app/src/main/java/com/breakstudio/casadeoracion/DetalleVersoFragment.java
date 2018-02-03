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
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleVersoFragment extends Fragment {

    Button btnCerrarDetalleVerso;
    ListView lvVersos;
    Cursor c;
    Integer libroChapter,Libroid,id_verso;

    public DetalleVersoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detalle_verso, container, false);

        GetArg();

        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        try {
            databaseHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        c = databaseHelper.queryVersos(String.valueOf(libroChapter),String.valueOf(Libroid));

        BindUI(rootView);
        return  rootView;
    }

    public void GetArg(){

        Bundle arg = getArguments();


        Libroid = arg.getInt("libroId");
        libroChapter = arg.getInt("libroChapter");
        id_verso = arg.getInt("idVerso");
    }

    public void BindUI(View v){

        btnCerrarDetalleVerso = (Button)  v.findViewById(R.id.btnCerrarDetalleVerso);

        btnCerrarDetalleVerso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        lvVersos = (ListView) v.findViewById(R.id.lisview_versos);

        VersoAdapter versosAdapter = new VersoAdapter(getContext(),c,0);
        lvVersos.setAdapter(versosAdapter);
        //se posiciona el lisview en con el vesiculo seleccionado como primero
        lvVersos.setSelection(id_verso-1);

        /*lvVersos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intent intent = new Intent(getApplicationContext(), VersosActivity.class);

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });*/

    }

}
