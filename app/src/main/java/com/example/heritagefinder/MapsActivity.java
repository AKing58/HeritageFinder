package com.example.heritagefinder;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Building> buildingsArrayList;
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        try{
            Bundle outBun = getIntent().getExtras();
            buildingsArrayList = (ArrayList<Building>) outBun.getSerializable("buildings");
        }catch(NullPointerException e){
            Log.e(TAG, "Buildings not received in MapsActivity");
        }


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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(49.250115, -123.000978);
        //LatLng metrotown = new LatLng(49.226767, -122.999108);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("BCIT"));
        //mMap.addMarker(new MarkerOptions().position(metrotown).title("Metrotown"));
        LatLng firstBuilding = new LatLng(buildingsArrayList.get(0).getLatitude(), buildingsArrayList.get(0).getLongitude());
        for(int i=0; i<buildingsArrayList.size();i++){
            //Log.e(TAG, buildingsArrayList.get(i).getName() + ": " + buildingsArrayList.get(i).getLatitude() + ", " + buildingsArrayList.get(i).getLongitude());
            mMap.addMarker(new MarkerOptions().position(new LatLng(buildingsArrayList.get(i).getLatitude(), buildingsArrayList.get(i).getLongitude())).title(buildingsArrayList.get(i).getName()));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstBuilding, 15));
    }
}
