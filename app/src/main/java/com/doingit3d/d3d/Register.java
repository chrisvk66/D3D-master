package com.doingit3d.d3d;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.ByteArrayOutputStream;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by David M on 14/04/2017.
 */

public class Register extends AppCompatActivity {

    private TextInputLayout til_nombre, til_email, til_pass, til_repetirpass;
    private EditText nombre, email, pass, repetirpass;
    private CheckBox scanner, disenador, impresor, ubicacion;
    private Intent camara;
    private static final int RESULT_LOAD_IMAGE = 10;
    private static final int REQUEST_IMAGE_CAPTURE = 20;
    private Bitmap bm,bitmap;
    private byte[] imagen_bbdd;//aqui te almacena los bytes de la imagen para guardarlos en la base de datos
    private Bundle b;
    private SQLiteDatabase db;
    private BBDD_Controller controller = new BBDD_Controller(this);
    private CircleImageView civ;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        civ=(CircleImageView) findViewById(R.id.foto_registro);

        nombre = (EditText) findViewById(R.id.et_nombre_registro);
        email = (EditText) findViewById(R.id.et_email_registro);
        pass = (EditText) findViewById(R.id.et_pass_registro);
        repetirpass = (EditText) findViewById(R.id.et_repetirpass_registro);

        til_nombre = (TextInputLayout) findViewById(R.id.til_nombre_registro);
        til_nombre.setErrorEnabled(true);
        til_email = (TextInputLayout) findViewById(R.id.til_email_registro);
        til_email.setErrorEnabled(true);
        til_pass = (TextInputLayout) findViewById(R.id.til_pass_registro);
        til_pass.setErrorEnabled(true);
        til_pass.setPasswordVisibilityToggleEnabled(true);
        til_repetirpass = (TextInputLayout) findViewById(R.id.til_repetirpass_registro);
        til_repetirpass.setErrorEnabled(true);
        til_repetirpass.setPasswordVisibilityToggleEnabled(true);

        scanner = (CheckBox) findViewById(R.id.check_scanner);
        disenador = (CheckBox) findViewById(R.id.check_diseñador);
        impresor = (CheckBox) findViewById(R.id.check_impresor);
        impresor = (CheckBox) findViewById(R.id.check_impresor);
        //ubicacion = (CheckBox) findViewById(R.id.posicionGPS);

        civ = (CircleImageView) findViewById(R.id.foto_registro);

        //poned en todas las actividades que querais la toolbar este codigo
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    //comprueba que es un email valido
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //metodo para que la flecha de la toolbar vaya hacia atras cuando se pulse
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();//llama a este metodo
        return true;
    }

    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }
        startActivity(new Intent(this, MainActivity.class));
    }

    //si el checkbox esta marcado se pondra un 1 y si no esta marcado pues un 0
    public int comprobar_scanner() {
        if (scanner.isChecked() == true) {
            return 1;
        } else {
            return 0;
        }
    }

    public int comprobar_disenador() {
        if (disenador.isChecked() == true) {
            return 1;
        } else {
            return 0;
        }
    }

    public int comprobar_impresor() {
        if (impresor.isChecked() == true) {
            return 1;
        } else {
            return 0;
        }
    }



    public void guardar_Perfil(View v) {

        //en estos condicionales se comprueba que se rellenen todos los campos y si no sale un mensaje de error
        if (nombre.getText().toString().trim().isEmpty()) {
            til_nombre.setError(getString(R.string.campo_requerido));
            til_email.setError("");
            til_pass.setError("");
            til_repetirpass.setError("");

        } else if (email.getText().toString().trim().isEmpty()) {
            til_nombre.setError("");
            til_email.setError(getString(R.string.campo_requerido));
            til_pass.setError("");
            til_repetirpass.setError("");

        } else if (pass.getText().toString().trim().isEmpty()) {
            til_nombre.setError("");
            til_email.setError("");
            til_pass.setError(getString(R.string.campo_requerido));
            til_repetirpass.setError("");


        } else if (!(pass.getText().toString().equals(repetirpass.getText().toString()))) {
            til_nombre.setError("");
            til_email.setError("");
            til_pass.setError("");
            til_repetirpass.setError(getString(R.string.diferente_pass));

        } /*else if (imagen_bbdd == null) {
            til_nombre.setError("");
            til_email.setError("");
            til_pass.setError("");
            til_repetirpass.setError("");
            Toast.makeText(getApplicationContext(), getString(R.string.elegir_img), Toast.LENGTH_SHORT).show();

        } */else if ((controller.comprobar_email_repetido(email.getText().toString()) == true)) {
            til_nombre.setError("");
            til_email.setError(getString(R.string.email_ya_existe));
            til_pass.setError("");
            til_repetirpass.setError("");

        } else if (isEmailValid(email.getText().toString()) == false) {
            til_nombre.setError("");
            til_email.setError(getString(R.string.email_verificar));
            til_pass.setError("");
            til_repetirpass.setError("");
        } else {

            try {

                if (imagen_bbdd == null) {
                    civ.setImageResource(R.drawable.nofoto);
                    bitmap = ((BitmapDrawable)civ.getDrawable()).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    imagen_bbdd = stream.toByteArray();

                    controller.registrarse(comprobar_scanner(),comprobar_impresor(),comprobar_disenador(), nombre.getText().toString(),email.getText().toString(),pass.getText().toString(),imagen_bbdd,0,0,0,0);
                    //Toast.makeText(this,"No hay imagen",Toast.LENGTH_SHORT).show();

                }else{
                   // Toast.makeText(this,"SI hay imagen",Toast.LENGTH_SHORT).show();
                    controller.registrarse(comprobar_scanner(),comprobar_impresor(),comprobar_disenador(), nombre.getText().toString(),email.getText().toString(),pass.getText().toString(),imagen_bbdd,0,0,0,0);
                }




               //mensaje de que ha funcionado
               new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                       .setTitleText(getString(R.string.enhorabuena))
                       .setContentText(getString(R.string.registro_exito))
                       .setConfirmText(getString(R.string.aceptar))
                       .setConfirmClickListener(sDialog -> {
                           sDialog.dismissWithAnimation();
                           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                               finishAffinity();
                           }
                           startActivity(new Intent(getApplicationContext(),MainActivity.class));
                       })
                       .show();
           }catch(Exception e){

               //mensaje de error
               new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                       .setTitleText(getString(R.string.error))
                       .setConfirmText(getString(R.string.aceptar))
                       .setConfirmClickListener(sDialog -> sDialog.dismissWithAnimation())
                       .show();

               //pruebas de errores por consola
               e.printStackTrace();
               System.out.println("---FALLO BBDD CONTROLLER----");
               System.out.println(nombre.getText().toString()+" "+email.getText().toString()+" "+pass.getText().toString()+" "+repetirpass.getText().toString()+" "+comprobar_scanner()+" "+comprobar_disenador()
                       +" "+comprobar_impresor());
           }


        }

    }

    //cuando se pulsa el icono de la camara te permite elegir una imagen desde la galeria o desde la camara
    public void ponerFoto(View v){

        new MaterialDialog.Builder(this)
                .title(R.string.elegir_modo)
                .items(R.array.camara_galeria)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int pos, CharSequence text) {

                        //GALERIA
                        if (pos==0){
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);

                            //CAMARA
                        }else if (pos==1){
                            camara= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (camara.resolveActivity(getPackageManager()) != null) {
                                startActivityForResult(camara, REQUEST_IMAGE_CAPTURE);
                            }
                        }
                    }

                })

                .show();
    }


    //comprueba si viene de la galeria o desde la camara y recoge los datos
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {

        super.onActivityResult(requestCode, resultCode, i);

        //IMAGEN DE LA GALERIA
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != i) {
            Uri uri = i.getData();
           try{
               Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
               ByteArrayOutputStream arraybytes = new ByteArrayOutputStream();
               bitmap.compress(Bitmap.CompressFormat.JPEG, 70, arraybytes);

               civ.setImageBitmap(bitmap);

               //en imagen_bbdd te almacena los bytes de la imagen para guardarlos en la base de datos
               imagen_bbdd=arraybytes.toByteArray();
           }catch(Exception e){
                e.printStackTrace();
           }

           //IMAGEN DE LA CAMARA
        }else  if(resultCode== RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE){
            b = i.getExtras();
            bm=(Bitmap)b.get("data");
            ByteArrayOutputStream arraybytes = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.WEBP, 100,arraybytes);
            civ.setImageBitmap(bm);

            //en imagen_bbdd te almacena los bytes de la imagen para guardarlos en la base de datos
            imagen_bbdd=arraybytes.toByteArray();
        }
    }

}
