package com.doingit3d.d3d;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RatingBar;

/**
 * Created by David M on 14/04/2017.
 */

public class Register extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user);

    }

    public void onBackPressed(){
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }
}
