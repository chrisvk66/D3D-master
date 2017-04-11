package com.doingit3d.d3d;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by David M on 11/04/2017.
 */

public class How_does_it_works extends AppCompatActivity {
    private CustomSwipeAdapter csa;
    private ViewPager vp;
    //el array comentado es el bueno pero de error de outofmemoryerror y supongo que habria que redimensionarlas o algo de eso
    //private int[] fotos ={R.drawable.screen_00,R.drawable.screen_01,R.drawable.screen_02,R.drawable.screen_03,R.drawable.screen_04,R.drawable.screen_05};

    //fotos de prueba
    private int[] fotos ={R.drawable.history_black,R.drawable.dashboard_black};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.how_does_it_works);

        vp =(ViewPager)findViewById(R.id.viewpager);
        csa= new CustomSwipeAdapter(How_does_it_works.this,fotos);
        vp.setAdapter(csa);

    }
}
