package com.doingit3d.d3d;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RatingBar;

/**
 * Created by David M on 14/04/2017.
 */

public class Profile extends AppCompatActivity {
    private RatingBar rb1,rb2;
    private ImageView iv_d3d_logo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        rb1=(RatingBar) findViewById(R.id.ratingBar_contratista);
        rb2=(RatingBar) findViewById(R.id.ratingBar_trabajador);
        iv_d3d_logo=(ImageView) findViewById(R.id.iv_logo_black_profile);
        iv_d3d_logo.setImageResource(R.drawable.logo_d3d_small_dark);

        //poned en todas las actividades que querais la toolbar este codigo
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    //metodo para que la flecha de la toolbar vaya hacia atras cuando se pulse
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();//llama a este metodo
        return true;
    }

    public void onBackPressed(){
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }
}
