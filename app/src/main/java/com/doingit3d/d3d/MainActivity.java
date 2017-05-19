package com.doingit3d.d3d;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    private FlowingDrawer mDrawer;
    private BBDD_Controller controller = new BBDD_Controller(this);
    private SQLiteDatabase db;

    private String tabla_usuario="CREATE TABLE IF NOT EXISTS usuario (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, imagen BLOB, nombre TEXT, email TEXT," +
            "contrasena TEXT, impresor INTEGER, disenador INTEGER, scanner INTEGER, latitud INTEGER, longitud INTEGER, conectado INTEGER)";

    private String tabla_proyecto="CREATE TABLE IF NOT EXISTS proyecto (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, tipo_proyecto TEXT, titulo TEXT, descripcion TEXT," +
            "fecha TEXT, pais TEXT, moneda TEXT, fecha_creacion TEXT, \n" +
            "usuario_id INTEGER ,desplazamiento TEXT ,formato_archivo TEXT, privacidad TEXT, material TEXT, nombre_user TEXT)";

   /* private String tabla_oferta ="CREATE TABLE IF NOT EXISTS oferta (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, importe INTEGER, tiempo_estimado INTEGER," +
            "FOREIGN KEY(usuario_id) REFERENCES usuario (id) , FOREIGN KEY (preyecto_id) REFERENCES preyecto (id), fecha_adjudicacion TEXT, fecha_envio TEXT," +
            "transporte INTEGER)";

    private String tabla_sms="CREATE TABLE IF NOT EXISTS mensaje_privado (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, from_user INTEGER), to_user INTEGER, fecha TEXT," +
            "texto TEXT, leido INTEGER";

    private String tabla_comentario="CREATE TABLE IF NOT EXISTS comentario(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,FOREIGN KEY (preyecto_id) REFERENCES preyecto (id),FOREIGN KEY(usuario_id) REFERENCES usuario (id)," +
            "usuario_destino INTEGER, texto TEXT, fecha TEXT, leido INTEGER, respuesta TEXT )";*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.toggleMenu();
            }
        });


        //SE CREAN AQUI LAS TABLAS POR QUE SI SE METEN EN EL onCreate DE LA CLASE BBDD_CONTROLLER SOLO DEJA CREAR UNA PEOR SI LO HACEMOS FUERA DE LA CLASE PERO
        //LLAMANDO AL METODO onCreate, NS PERMITE CREAR LAS TABLAS QUE QUERAMOS
        db=controller.getWritableDatabase();

        db.execSQL(tabla_usuario);
        controller.onCreate(db);

        db.execSQL(tabla_proyecto);
        controller.onCreate(db);

       /* db.execSQL(tabla_oferta);
        controller.onCreate(db);

        db.execSQL(tabla_sms);
        controller.onCreate(db);

        db.execSQL(tabla_comentario);
        controller.onCreate(db);*/

        db.close();

        mDrawer = (FlowingDrawer) findViewById(R.id.drawerlayout);
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        mDrawer.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
            @Override
            public void onDrawerStateChange(int oldState, int newState) {
                if (newState == ElasticDrawer.STATE_CLOSED) {
                    Log.i("MainActivity", "Drawer STATE_CLOSED");
                }
            }

            @Override
            public void onDrawerSlide(float openRatio, int offsetPixels) {
                Log.i("MainActivity", "openRatio=" + openRatio + " ,offsetPixels=" + offsetPixels);
            }
        });


        FragmentManager fm = getSupportFragmentManager();
        MenuListFragment mMenuFragment = (MenuListFragment) fm.findFragmentById(R.id.id_container_menu);
        if (mMenuFragment == null) {
            mMenuFragment = new MenuListFragment();
            fm.beginTransaction().add(R.id.id_container_menu, mMenuFragment).commit();
        }
    }


    public void registrarse(View v){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }
        startActivity(new Intent(this,Register.class));
    }

    @Override
    public void onBackPressed() {

            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(getString(R.string.salir_app))
                    .setCancelText(getString(R.string.permanecer))
                    .setConfirmText(getString(R.string.si))
                    .showCancelButton(true)
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.cancel();
                        }
                    })
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                finishAffinity();
                             }
                                System.exit(0);
                        }
                    })
                    .show();


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            //startActivity(new Intent(this,How_does_it_works.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void loginDialog(View v){

        Context context=this;

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View prompt = li.inflate(R.layout.login_dialog, null);

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(prompt);

        final TextInputLayout til_email=(TextInputLayout)prompt.findViewById(R.id.til_login_email);
        final TextInputLayout til_pass=(TextInputLayout)prompt.findViewById(R.id.til_login_pass);

        // set dialog message
        alert.setCancelable(false)
                .setPositiveButton(R.string.entrar, null)
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) { {
                                //cuando pulsa aceptar
                                dialog.cancel();

                            }}

                        });

        // create alert dialog
        AlertDialog alertDialog = alert.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {

                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    //CUANDO SE PULSA A "ENTRAR" EN LA VENTANA DE LOGIN
                    @Override
                    public void onClick(View view) {
                        //comprueba que exista el email Y la contraseña; y que la contraseña se corresponda con ese email
                        if ((controller.comprobarusuarios(til_email.getEditText().getText().toString())==true) && (controller.comprobarpass(til_pass.getEditText().getText().toString())==true) && (controller.comprobar_email_pass(til_email.getEditText().getText().toString(),til_pass.getEditText().getText().toString()))==true){

                            if (controller.comprobar_conectado()==true){
                                alertDialog.dismiss();
                                Toast.makeText(getApplicationContext(),getString(R.string.logout_first),Toast.LENGTH_SHORT).show();
                            }else{
                                //en esta linea actualiza el estado (mirar la clase BBDD_Controller para mas informacion)
                                controller.actualizar_estado_conexion(controller.obtener_id_login(til_email.getEditText().getText().toString()),1);
                                Toast.makeText(getApplicationContext(),getString(R.string.enhorabuena),Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    finishAffinity();
                                }
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            }


                        }else{

                            til_email.setError(getString(R.string.user_pass_incorrectos));
                            til_pass.setError(getString(R.string.user_pass_incorrectos));
                        }


                    }
                });
            }
        });

        // show it
        alertDialog.show();


    }
}