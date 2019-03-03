package com.example.heritagefinder;

import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    ArrayList<Building> buildings = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buildings.add(new Building("BCIT",  49.250115, -123.000978));
        buildings.add(new Building("Metrotown", 49.226767, -122.999108));
        SeekBar seekBar = this.findViewById(R.id.seekBar3);
        seekBar.setOnSeekBarChangeListener(this);
    }

    public void getDetails(View view){
        Intent i = new Intent(this, DetailsActivity.class);
        startActivity(i);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Intent i = new Intent(this, MapsActivity.class);
        Bundle bun = new Bundle();
        bun.putSerializable("buildings", buildings);
        i.putExtras(bun);
        startActivity(i);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
