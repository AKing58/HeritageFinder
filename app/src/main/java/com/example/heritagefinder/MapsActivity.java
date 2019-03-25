package com.example.heritagefinder;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.WeightedLatLng;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.Gradient;


import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ArrayList<Building> buildingsArrayList;
    private ArrayList<WeightedLatLng> weightedList;
    private String TAG = MainActivity.class.getSimpleName();
    private Context context;
    private View thisView;
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
            weightedList = new ArrayList<WeightedLatLng>();
            for(Building b: buildingsArrayList) {
                LatLng latlng = new LatLng(b.getLatitude(), b.getLongitude());
                WeightedLatLng weightedPoint = new WeightedLatLng(latlng, 150 / (b.getAge() - 1800));
                weightedList.add(weightedPoint);
            }
        }catch(NullPointerException e){
            Log.e(TAG, "Buildings not received in MapsActivity");
        }

        thisView = mapFragment.getView();
        context = getApplicationContext();

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
            mMap.addMarker(new MarkerOptions().position(new LatLng(buildingsArrayList.get(i).getLatitude(), buildingsArrayList.get(i).getLongitude()))
                    .title(buildingsArrayList.get(i).getName())
                    .snippet("Constructed in :" + buildingsArrayList.get(i).getAge() + "\n" + buildingsArrayList.get(i).getDesc()));
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {

                    LinearLayout popup = new LinearLayout(context);
                        popup.setOrientation(LinearLayout.VERTICAL);

                    TextView title = new TextView(context);
                    title.setTypeface(null, Typeface.BOLD);
                    title.setGravity(Gravity.CENTER);
                    title.setText(marker.getTitle());

                    TextView snippet = new TextView(context);
                    snippet.setText(marker.getSnippet());

                    popup.addView(title);
                    popup.addView(snippet);
                    return popup;
                }
            });
            Log.e(TAG, "" + buildingsArrayList.get(i).getLatitude() + " " + buildingsArrayList.get(i).getLongitude() + " " + buildingsArrayList.get(i).getName());
            HeatmapTileProvider tileProvider = new HeatmapTileProvider.Builder().weightedData(weightedList).radius(50).build();
            TileOverlay tileOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(tileProvider));
        }


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstBuilding, 15));
    }

    @Override
    public boolean onMarkerClick(Marker marker){
        marker.showInfoWindow();
        return true;
    }
}
