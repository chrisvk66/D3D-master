package com.doingit3d.d3d;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Double> latitude, longitude;
    private ArrayList<String> emails,nombres;
    private ArrayList<Bitmap> bit;
    private Bitmap bm;
    private String email,nombre;
    private  double lat = 0;
    private  double lon = 0;
    private BBDD_Controller controller = new BBDD_Controller(this);
    private  Bitmap escala;
    private Intent i;
    private SupportMapFragment mapFragment;
    private MapView mapView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
         mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        latitude= new ArrayList<>();
        longitude= new ArrayList<>();
        emails = new ArrayList<>();
        nombres = new ArrayList<>();
        bit = new ArrayList<>();

        controller.obtener_todos_latitud(latitude);
        controller.obtener_todos_longitud(longitude);
        controller.obtener_todos_email(emails);
        controller.obtener_todos_nombres(nombres);

        i= new Intent(this,Ver_usuario.class);



    }

    public void onBackPressed(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }

        startActivity(new Intent(this,MainActivity.class));

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */



    @Override
        public void onMapReady(GoogleMap map) {

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                i.putExtra("nom_usuario",marker.getTitle().toString());
                i.putExtra("origen2",0);
                startActivity(i);
            }
        });





                for (int i =0; i<latitude.size(); i++){

                    for (int j =0; j<longitude.size(); j++){

                        for (int k =0; k<emails.size(); k++){

                            for (int l =0;l<nombres.size(); l++){

                                lat = latitude.get(i);
                                lon = longitude.get(i);
                                email=emails.get(i);
                               // controller.obtener_imagen_todos(bit,email);
                                nombre=nombres.get(i);
                               // bm=bit.get(i);
                               // escala =controller.obtener_imagen_con_email(email).createScaledBitmap(controller.obtener_imagen_con_email(email), 75, 75, true);

                                map.addMarker(new MarkerOptions()
                                        .position(new LatLng(lat, lon))
                                        //.icon(BitmapDescriptorFactory.fromBitmap(escala))
                                        .title(email)
                                        .snippet(nombre));


                                map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                                    @Override
                                    public View getInfoWindow(Marker marker) {
                                        return null;
                                    }

                                    @Override
                                    public View getInfoContents(Marker marker) {

                                        View v = getLayoutInflater().inflate(R.layout.map_info, null);
                                        TextView tv =(TextView)v.findViewById(R.id.map_nombre);
                                        tv.setText("Nombre: "+ marker.getSnippet());
                                        TextView tv2=(TextView)v.findViewById(R.id.map_email);
                                        tv2.setText("Email: "+marker.getTitle());
                                        CircleImageView civ=(CircleImageView)v.findViewById(R.id.imagen_map);
                                        civ.setImageBitmap(controller.obtener_imagen_con_email(marker.getTitle()));

                                        return v;
                                    }
                                });

                            }
                        }

                    }
                }



        }

}
