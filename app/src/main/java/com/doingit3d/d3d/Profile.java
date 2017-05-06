package com.doingit3d.d3d;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RatingBar;

/**
 * Created by David M on 14/04/2017.
 */

public class Profile extends Activity {
    private RatingBar rb1,rb2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        rb1=(RatingBar) findViewById(R.id.ratingBar_contratista);
        rb2=(RatingBar) findViewById(R.id.ratingBar_trabajador);
    }

    public void onBackPressed(){
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }
}
