package com.doingit3d.d3d;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by David M on 14/04/2017.
 */

public class Profile extends AppCompatActivity {
    private RatingBar perfil;
    private ImageView iv_d3d_logo;
    private TextView nombre,email,presentados;
    BBDD_Controller controller= new BBDD_Controller(this);
    private ImageView foto;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        iv_d3d_logo=(ImageView) findViewById(R.id.iv_logo_black_profile);
        iv_d3d_logo.setImageResource(R.drawable.logo_d3d_small_dark);

        //poned en todas las actividades que querais la toolbar este codigo
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        nombre =(TextView) findViewById(R.id.tv_nombre_info);
        email=(TextView) findViewById(R.id.tv_email_info);
        presentados=(TextView) findViewById(R.id.tv_presentados_info);
        nombre.setText(controller.username_conectado());
        email.setText(controller.useremail_conectado());
        presentados.setText(String.valueOf(controller.obtener_proyectos_presentados()));

        foto=(ImageView) findViewById(R.id.foto_info_personal);
        foto.setImageBitmap(controller.obtener_imagen());


        perfil=(RatingBar) findViewById(R.id.rating_perfil);
        perfil.setEnabled(false);
        perfil.setRating(controller.obtener_valoracion(controller.useremail_conectado())/controller.obtener_num_veces_valorado(controller.useremail_conectado()));
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


    public void ir_a_evaulacion(View v){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }
        startActivity(new Intent(this,Evaluation.class));
    }

    public void historial(View v){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }
        Intent i= new Intent(this,Project_Main.class);
        i.putExtra("origen",1);

        startActivity(i);
    }

    public void editar_perfil(View v){

        startActivity(new Intent(this,Editar_Perfil.class));
    }
}
