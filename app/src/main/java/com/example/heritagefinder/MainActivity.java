package com.example.heritagefinder;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    ArrayList<Building> hertiageBuildings = new ArrayList<>();
    ArrayList<Building> buildingsAgeBuildings = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hertiageBuildings.add(new Building("BCIT",  49.250115, -123.000978));
        hertiageBuildings.add(new Building("Metrotown", 49.226767, -122.999108));
        SeekBar seekBar = this.findViewById(R.id.seekBar3);
        seekBar.setOnSeekBarChangeListener(this);
        new AsyncTest().execute();
    }

    public void getDetails(View view){
        Intent i = new Intent(this, DetailsActivity.class);
        startActivity(i);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Intent i = new Intent(this, MapsActivity.class);
        Bundle bun = new Bundle();
        bun.putSerializable("buildings", hertiageBuildings);
        i.putExtras(bun);
        startActivity(i);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private class AsyncTest extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            Log.d("xyz","message xyz");
            InfoGrabber heritageGrabber = new InfoGrabber();
            heritageGrabber.download(InfoGrabber.HeritageAddress);
            Log.d("xyz","xyz heritageGrabber raw: "+heritageGrabber.downloadedRaw.length());
            hertiageBuildings = JsonReader.readerHeritageRegistry(heritageGrabber.downloadedRaw);
            Log.d("xyz","xyz heritageGrabber download: "+hertiageBuildings.size());

//            InfoGrabber buildingAgeGrabber = new InfoGrabber();
//            buildingAgeGrabber.download(InfoGrabber.AgeAddress);
//            Log.d("xyz","xyz buildingAgeGrabber raw: "+buildingAgeGrabber.downloadedRaw.length());
//            buildingsAgeBuildings = JsonReader.readerBuildingAge(buildingAgeGrabber.downloadedRaw);
//            Log.d("xyz","xyz buildingAgeGrabber download: "+buildingsAgeBuildings.size());

            return null;
        }

        @Override
        protected void onPostExecute(Void results){

        }
    }
}
