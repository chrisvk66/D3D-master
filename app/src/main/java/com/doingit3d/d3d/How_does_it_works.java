package com.doingit3d.d3d;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by David M on 11/04/2017.
 */

public class How_does_it_works extends MainActivity {
    private CustomSwipeAdapter csa;
    private ViewPager vp;

    //array de imagenes
    private int[] fotos ={R.drawable.s0,R.drawable.s1,R.drawable.s2,R.drawable.s3,R.drawable.s4,R.drawable.s5};


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.how_does_it_works);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        vp =(ViewPager)findViewById(R.id.viewpager);
        csa= new CustomSwipeAdapter(How_does_it_works.this,fotos);
        vp.setAdapter(csa);


    }

    @Override
    public void onBackPressed() {
        finish();

        startActivity(new Intent(this,MainActivity.class));
    }


}
