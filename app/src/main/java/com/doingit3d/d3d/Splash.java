package com.doingit3d.d3d;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

/**
 * Created by chris on 08/06/2017.
 */

public class Splash extends Activity {

    private final int DURACION_SPLASH = 5000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);

       /* img.setBackgroundResource(R.drawable.splash_loading);

        AnimationDrawable animation = (AnimationDrawable) img.getBackground();

        animation.start(); */

        //animation.stop();

        new Handler().postDelayed(new Runnable(){
            public void run(){

                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
                finish();
            };
        }, DURACION_SPLASH);
    }
}