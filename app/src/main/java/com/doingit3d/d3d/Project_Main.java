package com.doingit3d.d3d;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * Created by David M on 09/05/2017.
 */

public class Project_Main extends AppCompatActivity {

    private ListView lv;
    private ArrayList<Project> modelo;
    private ArrayAdapter<Project> adapter;
    private Project p;
    WaveSwipeRefreshLayout w;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        modelo=new ArrayList<Project>();
        lv=(ListView)findViewById(R.id.listview);
        adapter= new Project_adapter(this,modelo);
        lv.setAdapter(adapter);

        adapter.add(new Project("hola","adios","prueba"));//insercion de prueba
        adapter.add(new Project("modelo","autor","material"));//insercion de prueba

        adapter.notifyDataSetChanged();



        //AQUI SE ACTUALIZA LA LISTA
        w= (WaveSwipeRefreshLayout) findViewById(R.id.main_swipe);
        w.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                // Poner aqui el codigo que refresque la lista

                adapter.add(new Project("refresco",":)","funciona"));//insercion de prueba


                //llamar a la line de abajo cuando se complete el refresco de la lista
                new Task().execute();

            }
        });



    }

    private class Task extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... voids) {
            return new String[0];
        }

        @Override protected void onPostExecute(String[] result) {
            // Call setRefreshing(false) when the list has been refreshed.
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    w.setRefreshing(false);
                }
            }, 3000);
           // w.setRefreshing(false);
            super.onPostExecute(result);
        }
    }

    //metodo para que la flecha de la toolbar vaya hacia atras cuando se pulse
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();//llama a este metodo
        return true;
    }

    public void onBackPressed(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }
        startActivity(new Intent(this,MainActivity.class));
    }
}
