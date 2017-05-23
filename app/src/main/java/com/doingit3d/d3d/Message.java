package com.doingit3d.d3d;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by chris on 08/02/2017.
 */
/*
public class Message extends AppCompatActivity implements AdapterView.OnItemClickListener{

    public static final int INSERT_CODE=0;
    public static final int EDIT_CODE=1;
    public static final int RESUMEN_CODE=2;
    public static final String EMAIL="email";
    public static final String ASUNTO="asunto";
    public static final String GENERO="genero";
    public static final String DESCRIPCION="descripcion";
    public static final String IMAGEN="imagen";
    public static final String  POSICION="pos";
    public int pos;
    public ArrayList<MensajeConstructores> modelo;
    public ListView lista;
    public ArrayAdapter<MensajeConstructores> adapter;
    public MensajeConstructores juego;
    public Intent iMain;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recibidos);

        iMain = getIntent();

        if (savedInstanceState!=null){
            pos=savedInstanceState.getInt(POSICION);
        }else{
            pos=-1;
        }

        //modelo=new ArrayList<MensajeConstructores>();

        modelo.add(new MensajeConstructores(1, "realmadrid@hotmail.com", "Sin asunto bro", "XXX", "Holaaaaa", null));

        /*String usuariot = iMain.getStringExtra("usuario");

        Login login = new Login(this);

        login.lista_juegos(modelo, usuariot);

        lista=(ListView)findViewById(R.id.lista);

        adapter= new ListAdapter(this,modelo);

        lista.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        registerForContextMenu(lista);

        lista.setOnItemClickListener(this);

    }
    public void onActivityResult(int requestCode, int resultCode, Intent i) {

        if (requestCode == 0 && requestCode == 0 && i == null) {

        } else if (requestCode == RESUMEN_CODE) {

        } else if (requestCode != RESUMEN_CODE) {

            if (requestCode == EDIT_CODE) {

                refrescar();
            } else {

                refrescar();
            }
        }
    }

    public void refrescar(){

        adapter.clear();

        String usuariot = iMain.getStringExtra("usuario");

        Login login = new Login(this);

        login.lista_juegos(modelo, usuariot);

        adapter= new MiAdapter(this,modelo);

        lista.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    public void agregar(View vista){

        String intentmain = iMain.getStringExtra("usuario");

        Intent i = new Intent(this, AnadirJuego.class);

        i.putExtra("usuariot", intentmain);

        startActivityForResult(i,INSERT_CODE);

    }

    public void desconectar (View vista){

        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Cerrar sesión");
        dialogo.setMessage("¿Deseas cerrar sesión?");
        dialogo.setCancelable(false);
        dialogo.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo, int id) {

                Toast.makeText(getApplicationContext(), "Has cerrado sesión", Toast.LENGTH_LONG).show();

                Intent i = new Intent(getApplicationContext(), MainActivity.class);

                finish();

                startActivity(i);
            }
        });
        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo, int id) {
            }
        });
        dialogo.show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo mi){

        menu.setHeaderTitle("Opciones");
        getMenuInflater().inflate(R.menu.menu, menu);

    }

    public boolean onContextItemSelected(MenuItem item){

        AdapterView.AdapterContextMenuInfo info =(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        pos=info.position;
        int id = item.getItemId();

        switch(id){

            case R.id.editar:

                String intentmain = iMain.getStringExtra("usuario");

                Intent i = new Intent(this, EditarJuego.class);
                juego= modelo.get(info.position);

                i.putExtra("usuarioe", intentmain);
                i.putExtra("posicione", juego.njuego);
                i.putExtra(TITULO, juego.titulo);
                i.putExtra(DESARROLLADOR, juego.desarrollador);
                i.putExtra(GENERO, juego.genero);
                i.putExtra(DESCRIPCION, juego.descripcion);
                i.putExtra(IMAGEN, juego.imagen);

                startActivityForResult(i,EDIT_CODE);

                break;

            case R.id.borrar:

                String usuariob = iMain.getStringExtra("usuario");

                juego= modelo.get(pos);

                int posicion = juego.njuego;

                Login login = new Login(this);

                login.borrar(usuariob, posicion);

                refrescar();

                break;

        }
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(getApplicationContext(), "Funciona!!", Toast.LENGTH_SHORT).show();

        pos=position;
        Intent i = new Intent(this,VerJuego.class);
        juego= modelo.get(pos);

        i.putExtra(TITULO, juego.titulo);
        i.putExtra(DESARROLLADOR, juego.desarrollador);
        i.putExtra(GENERO, juego.genero);
        i.putExtra(DESCRIPCION, juego.descripcion);
        i.putExtra(IMAGEN, juego.imagen);


        startActivityForResult(i,RESUMEN_CODE);



    }

    public void onBackPressed(){

        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Salir");
        dialogo.setMessage("¿Deseas salir de la aplicación?");
        dialogo.setCancelable(false);
        dialogo.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo, int id) {
                finish();
            }
        });
        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo, int id) {
            }
        });
        dialogo.show();
    }

}



*/