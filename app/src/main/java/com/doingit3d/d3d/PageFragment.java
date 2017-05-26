package com.doingit3d.d3d;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;

public class PageFragment extends Fragment{
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private View view;
    private ListView lista;
    private BBDD_Controller controller;
    private MensajeConstructores m;
    private ArrayList<MensajeConstructores> modelo;
    private  ArrayAdapter<MensajeConstructores> adapter;
    private Intent i;


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

        }else{

            view = inflater.inflate(R.layout.enviados, container, false);
        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        if(mPage==1){



            modelo=new ArrayList<MensajeConstructores>();

            adapter= new ListAdapter(getActivity(),modelo);
            lista=(ListView)getView().findViewById(R.id.lista);
            lista.setAdapter(adapter);

            cargar_mensajes_recibidos ();
            adapter.notifyDataSetChanged();


            registerForContextMenu(lista);
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override public void onItemClick(AdapterView<?> arg0, View v,int position, long id){
                    controller.actualizar_leido(1,modelo.get(position).id);

                   i= new Intent(getContext(),Ver_mensajes.class);
                   // i.putExtra("sms",0);
                    i.putExtra("id_sms",modelo.get(position).id);
                    startActivity(i);

                }
            });


        }else if (mPage==2){


            modelo=new ArrayList<MensajeConstructores>();

            adapter= new ListAdapter(getActivity(),modelo);
            lista=(ListView)getView().findViewById(R.id.lista2);
            lista.setAdapter(adapter);
            lista.setAdapter(adapter);

           cargar_mensajes_enviados();
            adapter.notifyDataSetChanged();

            registerForContextMenu(lista);

            lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override public void onItemClick(AdapterView<?> arg0, View v,int position, long id){
                    controller.actualizar_leido(1,modelo.get(position).id);

                    i= new Intent(getContext(),Ver_mensajes.class);
                   // i.putExtra("sms",1);
                    i.putExtra("id_sms",modelo.get(position).id);
                    startActivity(i);

                }
            });
        }



    }


    public void cargar_mensajes_enviados(){

        controller = new BBDD_Controller(getContext());
        SQLiteDatabase db = controller.getReadableDatabase();

        try{
            if(db!=null){

                Cursor cursor = db.rawQuery("SELECT * FROM mensaje_privado WHERE from_user= "+"'"+controller.useremail_conectado()+"'", null);

                if(cursor.moveToFirst()){
                    do{

                        m = new MensajeConstructores(cursor.getString(cursor.getColumnIndex("to_user")),cursor.getString(cursor.getColumnIndex("asunto")),cursor.getInt(cursor.getColumnIndex("id")));

                        adapter.add(m);


                    }while(cursor.moveToNext());
                }
                db.close();
            }
        }catch (Exception e){

            e.printStackTrace();
        }
    }

    public void cargar_mensajes_recibidos(){

        controller = new BBDD_Controller(getContext());
        SQLiteDatabase db = controller.getReadableDatabase();

        try{
            if(db!=null){

                Cursor cursor = db.rawQuery("SELECT * FROM mensaje_privado WHERE to_user= "+"'"+controller.useremail_conectado()+"'", null);

                if(cursor.moveToFirst()){
                    do{

                        m = new MensajeConstructores(cursor.getString(cursor.getColumnIndex("from_user")),cursor.getString(cursor.getColumnIndex("asunto")),cursor.getInt(cursor.getColumnIndex("id")));

                        adapter.add(m);


                    }while(cursor.moveToNext());
                }
                db.close();
            }
        }catch (Exception e){

            e.printStackTrace();
        }
    }


}