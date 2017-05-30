package com.doingit3d.d3d;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

/**
 * Created by David M on 24/05/2017.
 */

public class Buscar_profesionales extends AppCompatActivity{
    private ListView lista;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> array= new ArrayList<>();
    private BBDD_Controller controller = new BBDD_Controller(this);
    private String itemText;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscar_profesionales);

        lista=(ListView) findViewById(R.id.lista_profesionales);
        i= new Intent(this,Ver_usuario.class);
        //controller.obtener_todos_email(array);
        adapter= new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,controller.obtener_todos_email(array));
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
              //  Toast.makeText(getApplicationContext(),"pos: "+pos,Toast.LENGTH_SHORT).show();
                itemText= lista.getItemAtPosition(pos).toString();
                i.putExtra("nom_usuario",itemText);
                i.putExtra("origen2",1);
                startActivity(i);
                //Toast.makeText(getApplicationContext(),"nom: "+itemText,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_busqueda,menu);
        MenuItem item = menu.findItem(R.id.menuBuscar);
        SearchView sv =(SearchView) item.getActionView();

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void onBackPressed(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }

        startActivity(new Intent(this,MainActivity.class));
    }

    public void volver(View v){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }

        startActivity(new Intent(this,MainActivity.class));
    }
}
