package com.doingit3d.d3d;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

/**
 * Created by david.martin on 18/05/2017.
 */

public class ResumenProyecto extends AppCompatActivity{

    private TextView tipo_proyecto,titulo,descripcion,fecha,pais,moneda,
    desplazamiento,formato_archivo,privacidad,material;
    private BBDD_Controller controller = new BBDD_Controller(this);
    private int id_proyecto;
    private  final int RESUMEN=55;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resumen_proyecto);

        Intent i=getIntent();
        id_proyecto= i.getExtras().getInt("pos");

        //poned en todas las actividades que querais la toolbar este codigo
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tipo_proyecto=(TextView) findViewById(R.id.tv_tipologia_resumen);
        titulo=(TextView) findViewById(R.id.tv_titulo_resumen);
        descripcion=(TextView) findViewById(R.id.tv_descripcion_resumen);
        fecha=(TextView) findViewById(R.id.tv_fecha_seleccionada_resumen);
        pais=(TextView) findViewById(R.id.tv_pais_resumen);
        moneda=(TextView) findViewById(R.id.tv_moneda_resumen);
        desplazamiento=(TextView) findViewById(R.id.tv_desplazamiento_resumen);
        formato_archivo=(TextView) findViewById(R.id.tv_formato_resumen);
        privacidad=(TextView) findViewById(R.id.tv_privacidad_resumen);
        material=(TextView) findViewById(R.id.tv_material_resumen);

        controller.resumen_list(id_proyecto,tipo_proyecto,titulo,descripcion,fecha,pais,moneda,desplazamiento,formato_archivo,privacidad,material);

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();//llama a este metodo
        return true;
    }

    public void onBackPressed() {
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }
        startActivity(new Intent(this,Project_Main.class));*/

        Intent i = new Intent();
        setResult(RESULT_OK, i);
        finish();
    }
}
