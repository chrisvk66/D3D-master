package com.doingit3d.d3d;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.app.DialogFragment;
import cn.pedant.SweetAlert.SweetAlertDialog;
import info.hoang8f.widget.FButton;


/**
 * Created by David M on 14/04/2017.
 */

public class Publish_Project extends AppCompatActivity {

    //boton que al pulsarlo sale un calendario para elegir fecha
   // private Button b_fecha, b_publicar;

    //FButton se llaman asi los botones de la libreria
    private FButton  b_fecha, b_publicar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_project);

       // b_fecha =(Button) findViewById(R.id.b_fecha_hora);
       // b_publicar=(Button) findViewById(R.id.b_publicar_proyecto);

        b_fecha =(FButton) findViewById(R.id.b_fecha_hora);
        b_publicar=(FButton) findViewById(R.id.b_publicar_proyecto);

        //poned en todas las actividades que querais la toolbar este codigo
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    //codigo de: https://android--examples.blogspot.com.es/2015/05/how-to-use-datepickerdialog-in-android.html
    public void abrirCalendario(View v){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(),"Date Picker");
    }

    public void publicar_proyecto(View v){
        //cuando el proyecto se publique bien sin que haya ningun error, saldra un mensaje, finalizará la actividad y volverá al home :
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(getString(R.string.enhorabuena))
                .setContentText("Proyecto publicado con éxito")
                .setConfirmText(getString(R.string.aceptar))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            finishAffinity();
                        }
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                })
                .show();

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
