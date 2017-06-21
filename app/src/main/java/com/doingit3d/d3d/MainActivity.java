package com.doingit3d.d3d;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;

import br.com.goncalves.pugnotification.notification.PugNotification;
import cn.pedant.SweetAlert.SweetAlertDialog;
import jp.wasabeef.blurry.Blurry;


public class MainActivity extends AppCompatActivity {

    private FlowingDrawer mDrawer;
    private BBDD_Controller controller = new BBDD_Controller(this);
    private SQLiteDatabase db;
    private Toolbar toolbar;
    private Button mapa,proyecto,registro,login;
    private Rect r;
    private Intent i;
    private PendingIntent pi;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    private URL imagen_user=null;
    private Bitmap bitmap;
    private byte[] imagen_bbdd;
    private Random random = new Random();
    private InputStream input;
    private String num1=String.valueOf(random.nextInt(1000+10)+10);
    private String num2=String.valueOf(random.nextInt(1000+10)+10);
    private Shimmer shimmer;
    private ShimmerTextView shimmerTextView;
    private LinearLayout fondo;

    private String tabla_usuario="CREATE TABLE IF NOT EXISTS usuario (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, imagen BLOB, nombre TEXT, email TEXT," +
            "contrasena TEXT, impresor INTEGER, disenador INTEGER, scanner INTEGER, latitud REAL, longitud REAL, conectado INTEGER, tutorial INTEGER," +
            " valoracion REAL, veces_valorado INTEGER, lugar TEXT, id_face TEXT)";

    private String tabla_proyecto="CREATE TABLE IF NOT EXISTS proyecto (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, tipo_proyecto TEXT, titulo TEXT, descripcion TEXT," +
            "fecha TEXT, pais TEXT, moneda TEXT, fecha_creacion TEXT, \n" +
            "usuario_id INTEGER ,desplazamiento TEXT ,formato_archivo TEXT, privacidad TEXT, material TEXT, nombre_user TEXT)";

    private String tabla_evaluacion="CREATE TABLE IF NOT EXISTS evaluacion (id_usuario INTEGER,p_presentados INTEGER, p_finalizados INTEGER,p_nulos INTEGER, p_adjudicacion INTEGER,o_presentados INTEGER," +
            "o_pendientes INTEGER, t_adjudicacion INTEGER, t_no_adjudicacion INTEGER)";

   /* private String tabla_oferta ="CREATE TABLE IF NOT EXISTS oferta (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, importe INTEGER, tiempo_estimado INTEGER," +
            "FOREIGN KEY(usuario_id) REFERENCES usuario (id) , FOREIGN KEY (preyecto_id) REFERENCES preyecto (id), fecha_adjudicacion TEXT, fecha_envio TEXT," +
            "transporte INTEGER)";*/

    private String tabla_sms="CREATE TABLE IF NOT EXISTS mensaje_privado (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, from_user INTEGER, to_user INTEGER, fecha TEXT," +
            "texto TEXT, asunto INTEGER,leido INTEGER, foto BLOB)";

   /* private String tabla_comentario="CREATE TABLE IF NOT EXISTS comentario(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,FOREIGN KEY (preyecto_id) REFERENCES preyecto (id),FOREIGN KEY(usuario_id) REFERENCES usuario (id)," +
            "usuario_destino INTEGER, texto TEXT, fecha TEXT, leido INTEGER, respuesta TEXT )";*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.getApplicationContext();
        FacebookSdk.setApplicationId(getResources().getString(R.string.facebook_app_id));

        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);

        fondo = (LinearLayout)findViewById(R.id.layoutinicial);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        loginButton=(LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject jsonObject,
                                                    GraphResponse response) {

                                // Getting FB User Data
                                Bundle facebookData = getFacebookData(jsonObject);
                                String id =facebookData.getString("idFacebook");
                                String nombre = facebookData.getString("first_name");
                                String email= facebookData.getString("email");

                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        try{
                                            imagen_user=new URL("https://graph.facebook.com/"+id+"/picture?type=large");
                                            bitmap= BitmapFactory.decodeStream(imagen_user.openConnection().getInputStream());
                                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                            imagen_bbdd = stream.toByteArray();

                                        }catch(Exception e){
                                            e.printStackTrace();
                                        }

                                    }
                                });


                                try{


                                    if ((controller.comprobar_id_face(id))){
                                        //Toast.makeText(getApplicationContext(),"El id1 "+profile.getId(),Toast.LENGTH_SHORT).show();
                                        controller.actualizar_estado_conexion(controller.obtener_id_face(id),1);
                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));

                                    }else{


                                        controller.registrarse(0,0,0,nombre,email,num1+num2,imagen_bbdd,0,0,1,0,"",id);
                                        controller.insertar_id_facebook(id,email);
                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));

                                    }

                                }catch(Exception e){
                                    System.out.println("Esto es el catch");
                                    LoginManager.getInstance().logOut();
                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                    e.printStackTrace();
                                }


                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,first_name,last_name,email,gender");
                request.setParameters(parameters);
                request.executeAsync();


            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.toggleMenu();
            }
        });



        mapa=(Button) findViewById(R.id.b_mapa);
        proyecto=(Button) findViewById(R.id.b_proyectos);
        registro=(Button) findViewById(R.id.b_registrarse);
        login=(Button) findViewById(R.id.b_entrar);

        i = new Intent(this,Messages.class);

         pi = PendingIntent.getActivity( getBaseContext(), 0, i, PendingIntent.FLAG_UPDATE_CURRENT);


        //SE CREAN AQUI LAS TABLAS POR QUE SI SE METEN EN EL onCreate DE LA CLASE BBDD_CONTROLLER SOLO DEJA CREAR UNA PEOR SI LO HACEMOS FUERA DE LA CLASE PERO
        //LLAMANDO AL METODO onCreate, NS PERMITE CREAR LAS TABLAS QUE QUERAMOS
        db=controller.getWritableDatabase();

        db.execSQL(tabla_usuario);
        controller.onCreate(db);

        db.execSQL(tabla_proyecto);
        controller.onCreate(db);

        db.execSQL(tabla_evaluacion);
        controller.onCreate(db);

       /* db.execSQL(tabla_oferta);
        controller.onCreate(db);*/

        db.execSQL(tabla_sms);
        controller.onCreate(db);


        /*db.execSQL(tabla_comentario);
        controller.onCreate(db);*/

        db.close();


        if (controller.obtener_leido()){
            PugNotification.with(this)
                    .load()
                    .title("Proyectos")
                    .message("Tienes mensajes nuevos")
                    .smallIcon(R.drawable.ic_notification_icon)
                    .largeIcon(R.drawable.ic_launcher_icon)
                    .flags(Notification.DEFAULT_ALL)
                    .button(R.drawable.ic_menu_send,"Abrir",pi)
                    .simple()
                    .build();
        }



        mDrawer = (FlowingDrawer) findViewById(R.id.drawerlayout);
        shimmer= new Shimmer();
        shimmerTextView= (ShimmerTextView)findViewById(R.id.shimmer_tv);
        //si el usuario esta conectado el menu lateral se habilita y sale el icono en la tollbar
       if (controller.comprobar_conectado()){
           mapa.setVisibility(View.VISIBLE);
           proyecto.setVisibility(View.VISIBLE);
           loginButton.setVisibility(View.INVISIBLE);
           registro.setVisibility(View.INVISIBLE);
           login.setVisibility(View.INVISIBLE);
           /*shimmerTextView.setVisibility(View.VISIBLE);
           shimmerTextView.setText("¡Bienvenido!\n"+controller.username_conectado());
           shimmer.start(shimmerTextView);*/

           fondo.setBackgroundResource(R.drawable.blur);

           getSupportActionBar().setDisplayHomeAsUpEnabled(true);
           getSupportActionBar().setDisplayShowHomeEnabled(true);
           getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white);

           Context c = getApplicationContext();

           mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
           mDrawer.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
               @Override
               public void onDrawerStateChange(int oldState, int newState) {
                   if (newState == ElasticDrawer.STATE_CLOSED) {
                       Log.i("MainActivity", "Drawer STATE_CLOSED");
                   }else if (newState==ElasticDrawer.STATE_OPEN){
                       //cuando el menu lateral se abra, se iniciara el tutorial de los items del menu lateral
                       tuto();
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

           //si no hay usuarios conectados, no se podrá usar el menu lateral ni saldrá el icono en la toolbar
       }else{

           mapa.setVisibility(View.INVISIBLE);
           proyecto.setVisibility(View.INVISIBLE);
           loginButton.setVisibility(View.VISIBLE);
           registro.setVisibility(View.VISIBLE);
           login.setVisibility(View.VISIBLE);
           shimmerTextView.setVisibility(View.INVISIBLE);

           getSupportActionBar().setDisplayHomeAsUpEnabled(false);
           getSupportActionBar().setDisplayShowHomeEnabled(false);
           mDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_NONE);

       }


        r = new Rect(0,0,100,130);
        //tutorial de la toolbar
       if (controller.obtener_tutorial()==0 && controller.comprobar_conectado()){
           TapTargetView.showFor(this,
                   TapTarget.forBounds(r,"Menu Lateral","Pulsa aquí para ver las opciones")
                           // All options below are optional
                           .dimColor(android.R.color.holo_red_dark)
                           .transparentTarget(true)
                           //.outerCircleColor(R.color.transparente)
                           .targetCircleColor(R.color.verde)
                           .cancelable(false)
                           .textColor(android.R.color.black),
                   new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                       @Override
                       public void onTargetClick(TapTargetView view) {
                           super.onTargetClick(view);      // This call is optional

                       }
                   });
       }





       }//onCreate

    private Bundle getFacebookData(JSONObject object) {
        Bundle bundle = new Bundle();

        try {
            String id = object.getString("id");
            URL profile_pic;
            try {
                profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?type=large");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));


          /*  prefUtil.saveFacebookUserInfo(object.getString("first_name"),
                    object.getString("last_name"),object.getString("email"),
                    object.getString("gender"), profile_pic.toString());*/

        } catch (Exception e) {
            //Log.d(TAG, "BUNDLE Exception : "+e.toString());
        }

        return bundle;
    }



    /*private void nextActivity(Profile profile){
        if(profile != null){
            Intent main = new Intent(MainActivity.this, MainActivity.class);
            main.putExtra("name", profile.getFirstName());
            main.putExtra("surname", profile.getLastName());
            main.putExtra("imageUrl", profile.getProfilePictureUri(200,200).toString());
            startActivity(main);
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       callbackManager.onActivityResult(requestCode, resultCode, data);

    }


    @Override
    protected void onResume() {
        super.onResume();
        //Facebook login
        //Profile profile = Profile.getCurrentProfile();
        //startActivity(new Intent(getApplicationContext(),MainActivity.class));
        //nextActivity(profile);
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    protected void onStop() {
        super.onStop();
        //Facebook login
       // accessTokenTracker.stopTracking();
        //profileTracker.stopTracking();
    }




    //metodo que inicia el tutorial
    public void tuto() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int alto = metrics.heightPixels;
        //float ancho = metrics.widthPixels;

        Rect r1, r2, r3, r4, r5, r6, r7, r8, r9;

        //diferentes resoluciones de pantalla para que el tutorial salga bien

        if(alto >= 870 && alto <= 970){

            alto = 960;
        }

        //pantalla 1280x720
        if (alto == 1280) {

            r1 = new Rect(105, 850, 0, 0);
            r2 = new Rect(105, 1070, 0, 0);
            r3 = new Rect(105, 1260, 0, 0);
            r4 = new Rect(105, 1450, 0, 0);
            r5 = new Rect(105, 1620, 0, 0);
            r6 = new Rect(105, 1810, 0, 0);
            r7 = new Rect(105, 2020, 0, 0);
            r8 = new Rect(105, 2230, 0, 0);
            r9 = new Rect(105, 2420, 0, 0);

            //pantalla 1920x1080
        } else if (alto == 1920) {

            r1 = new Rect(158, 1275, 0, 0);
            r2 = new Rect(158, 1605, 0, 0);
            r3 = new Rect(158, 1890, 0, 0);
            r4 = new Rect(158, 2175, 0, 0);
            r5 = new Rect(158, 2430, 0, 0);
            r6 = new Rect(158, 2715, 0, 0);
            r7 = new Rect(158, 3030, 0, 0);
            r8 = new Rect(158, 3345, 0, 0);
            r9 = new Rect(158, 3630, 0, 0);

            //800x480
        }else if(alto == 800){
            r1= new Rect(66,638,0,0);
            r2= new Rect(66,670,0,0);
            r3= new Rect(66,787,0,0);
            r4= new Rect(66,906,0,0);
            r5= new Rect(66,1013,0,0);
            r6= new Rect(66,1132,0,0);
            r7= new Rect(66,1263,0,0);
            r8= new Rect(66,1394,0,0);
            r9= new Rect(66,1513,0,0);

            //1024x768
        }else if(alto == 1024){
            r1= new Rect(35,680,0,0);
            r2= new Rect(35,856,0,0);
            r3= new Rect(35,1008,0,0);
            r4= new Rect(35,1160,0,0);
            r5= new Rect(35,1296,0,0);
            r6= new Rect(35,1448,0,0);
            r7= new Rect(35,1616,0,0);
            r8= new Rect(35,1784,0,0);
            r9= new Rect(35,1936,0,0);

            //pantalla de 960 x 540
        }else if(alto == 960){
            r1 = new Rect(79, 638, 0, 0);
            r2 = new Rect(79, 803, 0, 0);
            r3 = new Rect(79, 945, 0, 0);
            r4 = new Rect(79, 1088, 0, 0);
            r5 = new Rect(79, 1215, 0, 0);
            r6 = new Rect(79, 1358, 0, 0);
            r7 = new Rect(79, 1515, 0, 0);
            r8 = new Rect(79, 1673, 0, 0);
            r9 = new Rect(79, 1815, 0, 0);

            //por defecto pantalla de 1920x1080
        }else{
            r1 = new Rect(158, 1275, 0, 0);
            r2 = new Rect(158, 1605, 0, 0);
            r3 = new Rect(158, 1890, 0, 0);
            r4 = new Rect(158, 2175, 0, 0);
            r5 = new Rect(158, 2430, 0, 0);
            r6 = new Rect(158, 2715, 0, 0);
            r7 = new Rect(158, 3030, 0, 0);
            r8 = new Rect(158, 3345, 0, 0);
            r9 = new Rect(158, 3630, 0, 0);

        }


        //si nunca ha visto el tutorial se iguala a 0
        if (controller.obtener_tutorial()==0){

            new TapTargetSequence(this)
                    .targets(
                            TapTarget.forBounds(r1, "Perfil","Entra y mira tu perfil")
                                    .dimColor(android.R.color.holo_red_dark)
                                    .transparentTarget(true)
                                    .targetRadius(30)
                                    .targetCircleColor(R.color.azul_facebook)
                                    .cancelable(false)
                                    .textColor(android.R.color.black),

                            TapTarget.forBounds(r2, "Mapas", "Localiza a otros usuarios usando el mapa")
                                    .dimColor(android.R.color.holo_red_dark)
                                    .transparentTarget(true)
                                    .cancelable(false)
                                    .targetRadius(30)
                                    .targetCircleColor(R.color.azul_facebook)
                                    .textColor(android.R.color.black),

                            TapTarget.forBounds(r3,"Buscar profesionales", "Puedes buscar a otros profesionales según su email")
                                    .dimColor(android.R.color.holo_red_dark)
                                    .transparentTarget(true)
                                    .targetRadius(30)
                                    .targetCircleColor(R.color.azul_facebook)
                                    .cancelable(false)
                                    .textColor(android.R.color.black),

                            TapTarget.forBounds(r4,"Publicar Proyecto","Publica tus proyectos profesionales")
                                    .dimColor(android.R.color.holo_red_dark)
                                    .transparentTarget(true)
                                    .cancelable(false)
                                    .targetRadius(30)
                                    .targetCircleColor(R.color.azul_facebook)
                                    .textColor(android.R.color.black),

                            TapTarget.forBounds(r5, "Proyectos Publicados", "Mira los proyectos de los demás usuarios")
                                    .transparentTarget(true)
                                    .cancelable(false)
                                    .targetRadius(30)
                                    .targetCircleColor(R.color.azul_facebook)
                                    .textColor(android.R.color.black),

                            TapTarget.forBounds(r6,"Mis Proyectos", "Mira tus proyectos")
                                    .dimColor(android.R.color.holo_red_dark)
                                    .transparentTarget(true)
                                    .cancelable(false)
                                    .targetRadius(30)
                                    .targetCircleColor(R.color.azul_facebook)
                                    .textColor(android.R.color.black),

                            TapTarget.forBounds(r7, "Evaluación", "Aquí encontraras las estadisticas de tus proyectos")
                                    .dimColor(android.R.color.holo_red_dark)
                                    .transparentTarget(true)
                                    .cancelable(false)
                                    .targetRadius(30)
                                    .targetCircleColor(R.color.azul_facebook)
                                    .textColor(android.R.color.black),

                            TapTarget.forBounds(r8, "Mensajes", "Envia y recibe mensajes de otros usuarios")
                                    .dimColor(android.R.color.holo_red_dark)
                                    .transparentTarget(true)
                                    .cancelable(false)
                                    .targetRadius(30)
                                    .targetCircleColor(R.color.azul_facebook)
                                    .textColor(android.R.color.black),

                            TapTarget.forBounds(r9, "Chat", "Habla con otros usuarios en tiempo real")
                                    .dimColor(android.R.color.holo_red_dark)
                                    .transparentTarget(true)
                                    .cancelable(false)
                                    .targetRadius(30)
                                    .targetCircleColor(R.color.azul_facebook)
                                    .textColor(android.R.color.black)

                           /* TapTarget.forBounds(r10, "Cerrar Sesión", "Cierra la sesión actual")
                                    .dimColor(android.R.color.holo_red_dark)
                                    .transparentTarget(true)
                                    .cancelable(false)
                                    .targetRadius(30)
                                    .targetCircleColor(R.color.azul_facebook)
                                    .textColor(android.R.color.black)*/)

                    .listener(new TapTargetSequence.Listener() {

                        @Override
                        public void onSequenceFinish() {
                            //cuando termine la secuencia del tutorial, se iguala a 1 para que no vuelva a salir
                            controller.actualizar_tutorial(1);
                        }

                        @Override
                        public void onSequenceStep(TapTarget ultimo, boolean algo) {

                        }

                        @Override
                        public void onSequenceCanceled(TapTarget lastTarget) {

                        }
                    }).start();
        }else{

        }
    }

    public void registrarse(View v){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }
        startActivity(new Intent(this,Register.class));
    }

    @Override
    public void onBackPressed() {

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


    }


    public void loginDialog(View v){

        Context context=this;

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View prompt = li.inflate(R.layout.login_dialog, null);

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(prompt);

        final TextInputLayout til_email=(TextInputLayout)prompt.findViewById(R.id.til_login_email);
        final TextInputLayout til_pass=(TextInputLayout)prompt.findViewById(R.id.til_login_pass);
        til_pass.setPasswordVisibilityToggleEnabled(true);

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
        alertDialog.setIcon(R.drawable.ic_launcher_icon);
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {

                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    //CUANDO SE PULSA A "ENTRAR" EN LA VENTANA DE LOGIN
                    @Override
                    public void onClick(View view) {
                        //comprueba que exista el email Y la contraseña; y que la contraseña se corresponda con ese email
                        if ((controller.comprobarusuarios(til_email.getEditText().getText().toString())) && (controller.comprobarpass(til_pass.getEditText().getText().toString())) && (controller.comprobar_email_pass(til_email.getEditText().getText().toString(),til_pass.getEditText().getText().toString()))){

                            if (controller.comprobar_conectado()){
                                alertDialog.dismiss();
                                Toast.makeText(getApplicationContext(),getString(R.string.logout_first),Toast.LENGTH_SHORT).show();
                            }else{
                                //en esta linea actualiza el estado (mirar la clase BBDD_Controller para mas informacion)
                                controller.actualizar_estado_conexion(controller.obtener_id_login(til_email.getEditText().getText().toString()),1);
                                //Toast.makeText(getApplicationContext(),getString(R.string.sesion_iniciada),Toast.LENGTH_SHORT).show();
                                TastyToast.makeText(getApplicationContext(), getString(R.string.sesion_iniciada), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                alertDialog.dismiss();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    finishAffinity();
                                }
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }


                        }else{

                            til_email.setError(getString(R.string.user_pass_incorrectos));
                            til_pass.setError(getString(R.string.user_pass_incorrectos));
                        }


                    }
                });
            }
        });

        // show it
        alertDialog.show();
    }

    public void ir_a_perfil(View v){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }
        startActivity(new Intent(getApplicationContext(), com.doingit3d.d3d.Profile.class));
    }

    public void ir_a_mapa(View v){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }
        startActivity(new Intent(getApplicationContext(),MapsActivity.class));
    }

    public void ir_a_proyectos(View v){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }
       i= new Intent(this,Project_Main.class);
        i.putExtra("origen",0);
        startActivity(i);
    }
}