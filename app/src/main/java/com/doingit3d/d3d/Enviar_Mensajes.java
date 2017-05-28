package com.doingit3d.d3d;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by david.martin on 24/05/2017.
 */

public class Enviar_Mensajes extends AppCompatActivity{

    private BBDD_Controller controller = new BBDD_Controller(this);
    private TextInputLayout til_destinatario,til_asunto,til_mensaje;
    private Calendar calendario;
    private SimpleDateFormat formato;
    private String fecha;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enviar_mensajes);

        //poned en todas las actividades que querais la toolbar este codigo
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        til_destinatario=(TextInputLayout) findViewById(R.id.til_destinatario);
        til_asunto=(TextInputLayout) findViewById(R.id.til_asunto);
        til_mensaje=(TextInputLayout) findViewById(R.id.til_mensaje);

        calendario= Calendar.getInstance();


        formato= new SimpleDateFormat("dd-MMM-yyyy");
        fecha = formato.format(calendario.getTime());

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
        startActivity(new Intent(this,Messages.class));
    }


    public void enviar_mensaje(View v){

        if ((til_destinatario.getEditText().getText().toString().trim().isEmpty()) ||
                (controller.comprobarusuarios(til_destinatario.getEditText().getText().toString()))==false){
            til_destinatario.setError("Revise este campo");
            til_asunto.setError("");
            til_mensaje.setError("");

        }else if (til_asunto.getEditText().getText().toString().trim().isEmpty()){
            til_destinatario.setError("");
            til_asunto.setError(getString(R.string.campo_requerido));
            til_mensaje.setError("");

        }else if (til_mensaje.getEditText().getText().toString().trim().isEmpty()){
            til_destinatario.setError("");
            til_asunto.setError("");
            til_mensaje.setError(getString(R.string.campo_requerido));

        }else{
            controller.enviar_mensaje(controller.useremail_conectado(),til_destinatario.getEditText().getText().toString().trim(),
                    til_mensaje.getEditText().getText().toString(),0,til_asunto.getEditText().getText().toString(),fecha);

            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText(getString(R.string.enhorabuena))
                    .setContentText("Mensaje enviado con éxito")
                    .setConfirmText(getString(R.string.aceptar))
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();

                            startActivity(new Intent(getApplicationContext(),Messages.class));
                        }
                    })
                    .show();
        }


    }
}
