package com.doingit3d.d3d;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private View view;

    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Muestra el layout contenedor segun la seleccion hecha
        if (mPage==1){

            view = inflater.inflate(R.layout.recibidos, container, false);

            //startActivity( new Intent(PageFragment.this, Message.class));

        }else{

            view = inflater.inflate(R.layout.enviados, container, false);
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        if(mPage==1){

            /*TextView texto = (TextView) getView().findViewById(R.id.textView2);

            texto.setText("Croqueta ojala supieras lo que siento");*/

            ArrayList<MensajeConstructores> modelo=new ArrayList<MensajeConstructores>();

            modelo.add(new MensajeConstructores(1, "realmadrid@hotmail.com", "Sin asunto bro", "XXX", "Holaaaaa"));

            ListView lista=(ListView)getView().findViewById(R.id.lista);

            ArrayAdapter<MensajeConstructores> adapter= new ListAdapter(getActivity(),modelo);

            lista.setAdapter(adapter);

            adapter.notifyDataSetChanged();

            registerForContextMenu(lista);
        }
        /*

        */
        //lista.setOnItemClickListener(this);



    }
}