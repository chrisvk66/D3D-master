package com.doingit3d.d3d;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * Created by David M on 09/05/2017.
 */

public class Project_Main extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView lv;
    private ArrayList<Project> modelo;
    private ArrayAdapter<Project> adapter;
    private Project p;
    private WaveSwipeRefreshLayout w;
    private BBDD_Controller controller;
    private  final int RESUMEN=55;


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

       /* adapter.clear();
        cargar_lista();*/
        adapter.notifyDataSetChanged();

        lv.setOnItemClickListener(this);




        w= (WaveSwipeRefreshLayout) findViewById(R.id.main_swipe);
        w.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            //AQUI SE ACTUALIZA LA LISTA
            @Override public void onRefresh() {
                lv.setEnabled(false);
                adapter.clear();
                adapter.notifyDataSetChanged();

                cargar_lista();


                //llama a la linea de abajo cuando se complete el refresco de la lista
                new Task().execute();

            }
        });
    }

    //el método recoge lo que hay en la base de datos para poder llenar la lista
    public void cargar_lista(){
        controller = new BBDD_Controller(this);
        SQLiteDatabase db = controller.getReadableDatabase();

        try{
            if(db!=null){

                Cursor cursor = db.rawQuery("SELECT * FROM proyecto WHERE usuario_id ="+controller.obtener_id_conectado(), null);

                if(cursor.moveToFirst()){
                    do{

                        p = new Project(cursor.getString(cursor.getColumnIndex("titulo")),controller.autor_conectado(),cursor.getString(cursor.getColumnIndex("material")),cursor.getInt(cursor.getColumnIndex("id")));

                        adapter.add(p);


                    }while(cursor.moveToNext());
                }
                db.close();
            }
        }catch (Exception e){
            // Toast.makeText(this,"Error en la base de datos", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
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
                    lv.setEnabled(true);
                    w.setRefreshing(false);
                }
            }, 2500);
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



    //cuando se pulsa un elemento de la lista
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(this,ResumenProyecto.class);

        //le paso la posicion de la lista y la posicion en la base de datos del proyecto a la actividad ResumenProyecto.java para que muestre el proyecto correspondiente
        i.putExtra("pos",modelo.get(position).id_proyecto);
        startActivityForResult(i,RESUMEN);
        //p=modelo.get(position);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent i) {

        super.onActivityResult(requestCode, resultCode, i);

        if (requestCode == RESUMEN && resultCode == RESULT_OK ) {

          // System.out.println("----VIENE DE AQUI------------------");
            adapter.clear();
            cargar_lista();
            adapter.notifyDataSetChanged();

        }else{
            //System.out.println("----NO VIENE DE AQUI----------------------");
        }
    }
}
