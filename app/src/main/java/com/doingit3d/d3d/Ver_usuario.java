package com.doingit3d.d3d;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;
import com.sdsmdg.tastytoast.TastyToast;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by David M on 25/05/2017.
 */

public class Ver_usuario extends AppCompatActivity{

    private Intent i;
    private String email_usuario;
    private TextView nombre,email,proyectos;
    private CircleImageView civ;
    private BBDD_Controller controller = new BBDD_Controller(this);
    private RatingBar rb ;
    private CheckBox design,impresora,scanner;
    private RatingBar valorar_usuario,rating_verusuario;
    private Button b_valorar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_usuario);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        i=getIntent();
        email_usuario=i.getExtras().getString("nom_usuario");

        nombre=(TextView) findViewById(R.id.tv_nombre_verusuario);
        email=(TextView) findViewById(R.id.tv_email_versusuario);
        proyectos=(TextView) findViewById(R.id.tv_proyectos_versusuario);
        civ=(CircleImageView) findViewById(R.id.civ_verusuario);
        rb=(RatingBar)findViewById(R.id.rating_veusuario);
        rb.setEnabled(false);

        valorar_usuario =(RatingBar) findViewById(R.id.valorar_verusuario);
        rating_verusuario=(RatingBar) findViewById(R.id.rating_veusuario);
        rating_verusuario.setRating(controller.obtener_valoracion(email_usuario)/controller.obtener_num_veces_valorado(email_usuario));

        b_valorar=(Button)findViewById(R.id.b_valorar);

        design=(CheckBox) findViewById(R.id.check_design_verusuario);
        impresora=(CheckBox) findViewById(R.id.check_impresora_verusuario);
        scanner=(CheckBox) findViewById(R.id.check_scanner_verusuario);

        design.setClickable(false);
        impresora.setClickable(false);
        scanner.setClickable(false);

        if (controller.comprobar_design(email_usuario)){
            design.setChecked(true);
        }else{
            design.setChecked(false);
        }

        if (controller.comprobar_impresor(email_usuario)){
            impresora.setChecked(true);
        }else{
            impresora.setChecked(false);
        }

        if (controller.comprobar_scanner(email_usuario)){
            scanner.setChecked(true);
        }else{
            scanner.setChecked(false);
        }

        nombre.setText(controller.obtener_nombre_con_email(email_usuario));
        email.setText(email_usuario);
        proyectos.setText(String.valueOf(controller.obtener_proyectos_presentados_con_email(email_usuario)));
        civ.setImageBitmap(controller.obtener_imagen_con_email(email_usuario));
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();//llama a este metodo
        return true;
    }

    public void onBackPressed(){
        //finish();
        startActivity(new Intent(this,Buscar_profesionales.class));
    }

    public void valorar_usuario(View v){
        if (controller.useremail_conectado().equals(email_usuario)){
            TastyToast.makeText(this,"No puedes valorarte a ti mismo", TastyToast.LENGTH_SHORT,TastyToast.ERROR);

        }else{

            if (controller.obtener_num_veces_valorado(email_usuario)>0){

                controller.aumentar_valoracion(valorar_usuario.getRating(),email_usuario);
                controller.aumentar_num_veces_valorado(email_usuario);
                valorar_usuario.setEnabled(false);
                b_valorar.setEnabled(false);
                TastyToast.makeText(this,"Gracias por puntuar", TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);

            }else{

                controller.poner_valoracion(valorar_usuario.getRating(),email_usuario);
                controller.num_veces_valorado(1,email_usuario);
                valorar_usuario.setEnabled(false);
                b_valorar.setEnabled(false);
                TastyToast.makeText(this,"Gracias por puntuar", TastyToast.LENGTH_SHORT,TastyToast.SUCCESS);
            }
        }
    }
}