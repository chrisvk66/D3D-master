package com.doingit3d.d3d;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by david.martin on 24/05/2017.
 */

public class Ver_mensajes extends AppCompatActivity {

    private CircleImageView civ;
    private TextView from,to,asunto,mensaje,fecha;
    private BBDD_Controller controller= new BBDD_Controller(this);
    private int id_sms,sms;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_mensajes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Intent i=getIntent();
       id_sms= i.getExtras().getInt("id_sms");
       // sms=i.getExtras().getInt("sms");

        from=(TextView) findViewById(R.id.email_de);
        to=(TextView) findViewById(R.id.email_para);
        asunto=(TextView) findViewById(R.id.email_asunto);
        mensaje=(TextView) findViewById(R.id.email_mensaje);
        fecha=(TextView) findViewById(R.id.email_fecha);

        controller.ver_mensaje(id_sms,from,to,asunto,mensaje,fecha);

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

}
