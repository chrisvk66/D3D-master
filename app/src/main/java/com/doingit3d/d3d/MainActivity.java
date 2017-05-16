package com.doingit3d.d3d;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    private Button b_registrarse, b_login;
    private NavigationView nav;
    //private Menu nav_menu;
    private FlowingDrawer mDrawer;
    //private NavigationView nv;
    private Bundle b= new Bundle();
    private BBDD_Controller controller = new BBDD_Controller(this);;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //controller.onCreate(db);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white);

        b_registrarse=(Button) findViewById(R.id.b_registrarse);
        b_login=(Button) findViewById(R.id.b_entrar) ;


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.toggleMenu();
            }
        });

        mDrawer = (FlowingDrawer) findViewById(R.id.drawerlayout);
        mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        mDrawer.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
            @Override
            public void onDrawerStateChange(int oldState, int newState) {
                if (newState == ElasticDrawer.STATE_CLOSED) {
                    Log.i("MainActivity", "Drawer STATE_CLOSED");
                }
            }

            @Override
            public void onDrawerSlide(float openRatio, int offsetPixels) {
                Log.i("MainActivity", "openRatio=" + openRatio + " ,offsetPixels=" + offsetPixels);
            }
        });


        FragmentManager fm = getSupportFragmentManager();
        MenuListFragment mMenuFragment = (MenuListFragment) fm.findFragmentById(R.id.id_container_menu);
        if (mMenuFragment == null) {
            mMenuFragment = new MenuListFragment();
            fm.beginTransaction().add(R.id.id_container_menu, mMenuFragment).commit();
        }

        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/
    }


    public void registrarse(View v){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }
        startActivity(new Intent(this,Register.class));
    }

    @Override
    public void onBackPressed() {
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerlayout);
         //if (mDrawer.isMenuVisible()) {
          //  mDrawer.closeMenu();
       // }
        //if (drawer.isDrawerOpen(GravityCompat.START)) {
          //  drawer.closeDrawer(GravityCompat.START);

       // }else {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(getString(R.string.salir_app))
                    .setCancelText(getString(R.string.permanecer))
                    .setConfirmText(getString(R.string.si))
                    .showCancelButton(true)
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.cancel();
                        }
                    })
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }
                            System.exit(0);
                        }
                    })
                    .show();
      //  }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            //startActivity(new Intent(this,How_does_it_works.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void loginDialog(View v){

        Context context=this;

        LayoutInflater li2 = LayoutInflater.from(context);
        View lay = li2.inflate(R.layout.nav_header_main, null);
        final ImageView img=(ImageView)lay.findViewById(R.id.imagen_cabecera);

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View prompt = li.inflate(R.layout.login_dialog, null);

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(prompt);

        final TextInputLayout til_email=(TextInputLayout)prompt.findViewById(R.id.til_login_email);
        final TextInputLayout til_pass=(TextInputLayout)prompt.findViewById(R.id.til_login_pass);

        // set dialog message
        alert.setCancelable(false)
                .setPositiveButton(R.string.entrar, null)
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) { {
                                //cuando pulsa aceptar
                                dialog.cancel();

                            }}

                        });

        // create alert dialog
        AlertDialog alertDialog = alert.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {

                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    //CUANDO SE PULSA A "ENTRAR" EN LA VENTANA DE LOGIN
                    @Override
                    public void onClick(View view) {
                        //comprueba que exista el email Y la contraseña; y que la contraseña se corresponda con ese email
                        if ((controller.comprobarusuarios(til_email.getEditText().getText().toString())==true) && (controller.comprobarpass(til_pass.getEditText().getText().toString())==true) && (controller.comprobar_email_pass(til_email.getEditText().getText().toString(),til_pass.getEditText().getText().toString()))==true){


                            Toast.makeText(getApplicationContext(),getString(R.string.enhorabuena),Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();

                        }else{

                            til_email.setError(getString(R.string.user_pass_incorrectos));
                            til_pass.setError(getString(R.string.user_pass_incorrectos));
                        }


                        //Dismiss once everything is OK.

                    }
                });
            }
        });

        // show it
        alertDialog.show();


    }
}
