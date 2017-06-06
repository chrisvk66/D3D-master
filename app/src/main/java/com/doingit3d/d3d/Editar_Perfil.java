package com.doingit3d.d3d;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.sdsmdg.tastytoast.TastyToast;
import com.zys.brokenview.BrokenCallback;
import com.zys.brokenview.BrokenTouchListener;
import com.zys.brokenview.BrokenView;

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
    private BrokenView brokenView;
    private BrokenTouchListener listener;
    private PlaceAutocompleteFragment autocompleteFragment;
    private double latitud=0, longitud=0;
    private String lugar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_perfil);

        civ=(CircleImageView) findViewById(R.id.foto_editar);
        civ.setImageBitmap(controller.obtener_imagen());
        //mExplosionField = ExplosionField.attach2Window(this);
        brokenView = BrokenView.add2Window(this);
        brokenView.setCallback(new BrokenCallback() {
            @Override
            public void onStart(View v) {
                super.onStart(v);
            }

            @Override
            public void onCancel(View v) {
                super.onCancel(v);
            }

            @Override
            public void onRestart(View v) {
                super.onRestart(v);
            }

            @Override
            public void onFalling(View v) {
                super.onFalling(v);
            }

            @Override
            public void onFallingEnd(View v) {
                super.onFallingEnd(v);
                editar_foto();
            }

            @Override
            public void onCancelEnd(View v) {
                super.onCancelEnd(v);
            }
        });
        listener = new BrokenTouchListener.Builder(brokenView).build();
        civ.setOnTouchListener(listener);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        img=(ImageView) findViewById(R.id.foto_editar);
       // img.setImageResource(R.drawable.img_perfil);

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


        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                LatLng coordeadas=place.getLatLng();
                latitud=coordeadas.latitude;
                longitud=coordeadas.longitude;

                lugar=place.getName().toString();


            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                System.out.print("An error occurred: " + status);
            }
        });

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

    public void eliminar_usuario(View v){


        new MaterialDialog.Builder(Editar_Perfil.this)
                .title("Borrar mensaje")
                .content("¿Desea borrar el usuario?")
                .positiveText("si")
                .negativeText("no")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        new MaterialDialog.Builder(Editar_Perfil.this)
                                .title("¿Estas seguro?")
                                .content("No se podrá deshacer")
                                .positiveText("si")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        controller.eliminar_usuario(controller.obtener_id_conectado());
                                        controller.eliminar_proyecto(controller.obtener_id_conectado());
                                        //controller.actualizar_estado_conexion(controller.obtener_id_conectado(),0);
                                        TastyToast.makeText(Editar_Perfil.this,"Usuario eliminado",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);

                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                            finishAffinity();
                                        }
                                        startActivity(new Intent(Editar_Perfil.this,MainActivity.class));
                                    }
                                })
                                .negativeText("no")
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                        dialog.dismiss();
                                    }
                                })

                                .show();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //TastyToast.makeText(getContext(),"Has pulsado no",TastyToast.LENGTH_SHORT,TastyToast.INFO);

                        dialog.dismiss();
                    }
                })
                .show();
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
               // civ.setImageResource(R.drawable.nofoto);
               // bitmap = ((BitmapDrawable)civ.getDrawable()).getBitmap();
                bitmap=controller.obtener_imagen();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                fotoBytes = stream.toByteArray();

                controller.actualizar_perfil(nombre.getText().toString(),email.getText().toString(),design(),scanner(),impresion(),pass.getText().toString(),fotoBytes,latitud,longitud,lugar);

            }else{

                controller.actualizar_perfil(nombre.getText().toString(),email.getText().toString(),design(),scanner(),impresion(),pass.getText().toString(),fotoBytes,latitud,longitud,lugar);
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



    public void editar_foto(){

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

                }).canceledOnTouchOutside(true)
                .cancelable(true)
                .cancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        civ.setScaleX(1);
                        civ.setScaleY(1);
                        civ.setAlpha(1.0F);
                        civ.setVisibility(View.VISIBLE);

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
               // mExplosionField.clear();
                civ.setScaleX(1);
                civ.setScaleY(1);
                civ.setAlpha(1.0F);
                civ.setVisibility(View.VISIBLE);
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
           // mExplosionField.clear();
            civ.setScaleX(1);
            civ.setScaleY(1);
            civ.setAlpha(1.0F);
            civ.setVisibility(View.VISIBLE);
            civ.setImageBitmap(bm);

            //en imagen_bbdd te almacena los bytes de la imagen para guardarlos en la base de datos
            fotoBytes=arraybytes.toByteArray();
        }
    }
}
