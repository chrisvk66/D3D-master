package com.doingit3d.d3d;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.afollestad.materialdialogs.MaterialDialog;
import java.io.ByteArrayOutputStream;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by david.martin on 22/05/2017.
 */

public class Editar_Perfil extends AppCompatActivity {
    private BBDD_Controller controller = new BBDD_Controller(this);
    private ImageView img;
    private EditText nombre,email,pass;
    private CheckBox design,scanner,impresion;
    private TextInputLayout til_nombre,til_email,til_pass;
    private byte[] fotoBytes;
    private Bitmap bitmap,bm;
    private CircleImageView civ;
    private Intent camara;
    private static final int RESULT_LOAD_IMAGE = 22;
    private static final int REQUEST_IMAGE_CAPTURE = 33;
    private Bundle b;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_perfil);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        civ=(CircleImageView) findViewById(R.id.foto_editar);

        img=(ImageView) findViewById(R.id.foto_editar);
        img.setImageResource(R.drawable.img_perfil);

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



            if (fotoBytes == null) {
                civ.setImageResource(R.drawable.nofoto);
                bitmap = ((BitmapDrawable)civ.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                fotoBytes = stream.toByteArray();

                controller.actualizar_perfil(nombre.getText().toString(),email.getText().toString(),design(),scanner(),impresion(),pass.getText().toString(),fotoBytes);
               // Toast.makeText(this,"No hay imagen",Toast.LENGTH_SHORT).show();

            }else{
                //Toast.makeText(this,"SI hay imagen",Toast.LENGTH_SHORT).show();
                controller.actualizar_perfil(nombre.getText().toString(),email.getText().toString(),design(),scanner(),impresion(),pass.getText().toString(),fotoBytes);
            }

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

    public void editar_foto(View v){

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
                fotoBytes=arraybytes.toByteArray();
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
            fotoBytes=arraybytes.toByteArray();
        }
    }
}
