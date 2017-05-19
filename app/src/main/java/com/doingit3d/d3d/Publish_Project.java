package com.doingit3d.d3d;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by David M on 14/04/2017.
 */

public class Publish_Project extends AppCompatActivity {


    //FButton se llaman asi los botones de la libreria
    private BBDD_Controller controller = new BBDD_Controller(this);
    private TextInputLayout titulo, descripcion,pais;
    private Spinner tipo,formato,material;
    private RadioGroup moneda,privacidad,desplazamiento;
    private String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    private TextView fecha;
    private String moneda_text, privacidad_text, desplazamiento_text;
    private CheckBox terminos;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_project);

        //poned en todas las actividades que querais la toolbar este codigo
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        titulo=(TextInputLayout) findViewById(R.id.til_titulo_proyecto);
        descripcion=(TextInputLayout) findViewById(R.id.til_descripcion_proyecto);
        pais=(TextInputLayout) findViewById(R.id.til_pais);

        tipo=(Spinner)findViewById(R.id.spinner_tipologia);
        formato=(Spinner)findViewById(R.id.spinner_formato_archivo);
        material=(Spinner) findViewById(R.id.spinner_material);

        moneda=(RadioGroup) findViewById(R.id.radioGroup_moneda);
        privacidad=(RadioGroup) findViewById(R.id.radioGroup_privacidad);
        desplazamiento=(RadioGroup) findViewById(R.id.radioGroup_desplazamiento);

        fecha=(TextView) findViewById(R.id.tv_fecha_seleccionada);

        terminos=(CheckBox) findViewById(R.id.checkBox_terminos);

        moneda.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioButton_dolar:
                        moneda_text="dolar";
                        break;
                    case R.id.radioButton_euro:
                        moneda_text="euro";
                        break;
                }
            }
        });

        privacidad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioButton_publico:
                        privacidad_text="publico";
                        break;
                    case R.id.radioButton_privado:
                        privacidad_text="privado";
                        break;
                }
            }
        });

        desplazamiento.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioButton_sidespl:
                        desplazamiento_text="si";
                        break;
                    case R.id.radioButton_nodespl:
                        desplazamiento_text="no";
                        break;
                }
            }
        });

    }

    //codigo de: https://android--examples.blogspot.com.es/2015/05/how-to-use-datepickerdialog-in-android.html
    public void abrirCalendario(View v){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(),"Date Picker");
    }


    public void publicar_proyecto(View v){

        if ((titulo.getEditText().getText().toString().trim().isEmpty())  || (descripcion.getEditText().getText().toString().trim().isEmpty())
                || ( pais.getEditText().getText().toString().trim().isEmpty()) || (fecha.getText().toString().isEmpty())){

            titulo.setError(getString(R.string.campo_requerido));
            descripcion.setError(getString(R.string.campo_requerido));
            pais.setError(getString(R.string.campo_requerido));
            fecha.setError(getString(R.string.campo_requerido));

        }else if (terminos.isChecked()==false){
            Toast.makeText(getApplicationContext(),getString(R.string.aceptar_terminos),Toast.LENGTH_SHORT).show();
        }else if ((moneda_text == null) || (desplazamiento_text == null) || (privacidad_text == null)) {

            Toast.makeText(getApplicationContext(),getString(R.string.marcar_radio),Toast.LENGTH_SHORT).show();
        }else {

            //cuando el proyecto se publique bien sin que haya ningun error, saldra un mensaje, finalizará la actividad y volverá al home :
            controller.publicar_proyecto(tipo.getSelectedItem().toString(),titulo.getEditText().getText().toString(),descripcion.getEditText().getText().toString(),fecha.getText().toString(),
                    pais.getEditText().getText().toString(),moneda_text,date,controller.obtener_id_conectado(),desplazamiento_text,formato.getSelectedItem().toString(),privacidad_text,material.getSelectedItem().toString(),controller.username_conectado());

            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText(getString(R.string.enhorabuena))
                    .setContentText(getString(R.string.publicacion_exito))
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
