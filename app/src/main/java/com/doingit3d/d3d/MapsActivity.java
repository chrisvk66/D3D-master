package com.doingit3d.d3d;

import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<Double> latitude, longitude;

    private  double lat = 0;
    private  double lon = 0;
    private BBDD_Controller controller = new BBDD_Controller(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        controller.obtener_todos_latitud(latitude);
        controller.obtener_todos_longitud(longitude);



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


                for (int i =0; i<latitude.size(); i++){
                    for (int j =0; j<longitude.size(); j++){
                        lat = latitude.get(i);
                        lon = longitude.get(i);

                        map.addMarker(new MarkerOptions()
                                .position(new LatLng(lat, lon))
                                .title("Hello world"));
                    }
                }




        }


}
