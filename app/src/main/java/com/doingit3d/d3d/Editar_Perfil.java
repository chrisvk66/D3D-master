package com.doingit3d.d3d;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by david.martin on 22/05/2017.
 */

public class Editar_Perfil extends AppCompatActivity {
    private BBDD_Controller controller = new BBDD_Controller(this);
    private ImageView img;
    private EditText nombre,email,pass;
    private CheckBox design,scanner,impresion;
    private TextInputLayout til_nombre,til_email,til_pass;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_perfil);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        img=(ImageView) findViewById(R.id.foto_editar);
        img.setImageBitmap(controller.obtener_imagen());

        nombre=(EditText) findViewById(R.id.et_editar_nombre);
        email=(EditText) findViewById(R.id.et_editar_email);
        pass=(EditText) findViewById(R.id.et_editar_pass);

        design=(CheckBox) findViewById(R.id.activar_design);
        scanner=(CheckBox) findViewById(R.id.activar_scanner);
        impresion=(CheckBox) findViewById(R.id.activar_impresion);

        til_nombre=(TextInputLayout) findViewById(R.id.til_editar_nombre);
        til_email=(TextInputLayout) findViewById(R.id.til_editar_email);
        til_pass=(TextInputLayout) findViewById(R.id.til_editar_pass);
        til_pass.setPasswordVisibilityToggleEnabled(true);

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
        startActivity(new Intent(this,Profile.class));
    }

    public void cancelar(View v){
        onBackPressed();
    }

    public  int design(){
        if (design.isChecked()==true){
            return 1;
        }else{
            return 0;
        }
    }

    public  int scanner(){
        if (scanner.isChecked()==true){
            return 1;
        }else{
            return 0;
        }
    }

    public  int impresion(){
        if (impresion.isChecked()==true){
            return 1;
        }else{
            return 0;
        }
    }

    //comprueba que es un email valido
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void actualizar_perfil(View v){

        if (nombre.getText().toString().trim().isEmpty()){
            til_nombre.setError(getString(R.string.campo_requerido));
            til_email.setError("");
            til_pass.setError("");

        }else if ((isEmailValid(email.getText().toString())==false) || (email.getText().toString().trim().isEmpty())){
            Toast.makeText(this,getString(R.string.email_verificar),Toast.LENGTH_SHORT).show();
            til_email.setError(getString(R.string.email_verificar));
            til_nombre.setError("");
            til_pass.setError("");

        }else if ((controller.comprobar_email_repetido(email.getText().toString().trim())==true) && !(controller.useremail_conectado().equals(email.getText().toString().trim()))){
            til_email.setError(getString(R.string.email_ya_existe));
            til_nombre.setError("");
            til_pass.setError("");

        } else if (pass.getText().toString().trim().isEmpty()){
            til_pass.setError(getString(R.string.campo_requerido));
            til_email.setError("");
            til_nombre.setError("");

        }else{
            controller.actualizar_perfil(nombre.getText().toString(),email.getText().toString(),design(),scanner(),impresion(),pass.getText().toString());

            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText(getString(R.string.enhorabuena))
                    .setContentText(getString(R.string.cambios_guardados))
                    .setConfirmText(getString(R.string.aceptar))
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                finishAffinity();
                            }
                            startActivity(new Intent(getApplicationContext(),Profile.class));
                        }
                    })
                    .show();
        }


    }
}
