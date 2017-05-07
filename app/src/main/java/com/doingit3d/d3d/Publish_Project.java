package com.doingit3d.d3d;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.app.DialogFragment;
import android.widget.Button;

/**
 * Created by David M on 14/04/2017.
 */

public class Publish_Project extends Activity {

    //boton que al pulsarlo sale un calendario para elegir fecha
    private Button b_fecha;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_project);

        b_fecha =(Button) findViewById(R.id.b_fecha_hora);

    }

    //codigo de: https://android--examples.blogspot.com.es/2015/05/how-to-use-datepickerdialog-in-android.html
    public void abrirCalendario(View v){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(),"Date Picker");
    }

    public void onBackPressed(){
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }
}
