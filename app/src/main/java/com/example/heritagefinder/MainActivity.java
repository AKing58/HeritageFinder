package com.example.heritagefinder;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    ArrayList<Building> hertiageBuildings = new ArrayList<>();
    ArrayList<Building> buildingsAgeBuildings = new ArrayList<>();
    int minAge;
    int maxAge;
    EditText minText;
    EditText maxText;
    boolean preventLoop = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //SeekBar seekBar = this.findViewById(R.id.seekBar3);
        //seekBar.setOnSeekBarChangeListener(this);
        new AsyncTest().execute();

        minText = findViewById(R.id.minEditText);
        minAge = Integer.parseInt(minText.getText().toString());
        maxText = findViewById(R.id.maxEditText);
        maxAge = Integer.parseInt(maxText.getText().toString());
        minText.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s){
                if(preventLoop)
                    return;
                if(minText.getText().toString().length() == 4 && Integer.parseInt(minText.getText().toString()) < 1860){
                    preventLoop = true;
                    minText.setText("1860");
                    preventLoop = false;
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() == 4){
                    if(Integer.parseInt(s.toString()) > 1860){
                        minAge = Integer.parseInt(s.toString());
                    }
                }
            }
        });

        maxText.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s){
                if(preventLoop)
                    return;
                if(maxText.getText().toString().length() == 4 && Integer.parseInt(maxText.getText().toString()) > 1976){
                    preventLoop = true;
                    maxText.setText("1976");
                    preventLoop = false;
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length() == 4){
                    if(Integer.parseInt(s.toString()) < 1976){
                        maxAge = Integer.parseInt(s.toString());
                    }
                }

            }
        });
    }

    public void getDetails(View view){
        Intent i = new Intent(this, DetailsActivity.class);
        startActivity(i);
    }
/*
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Intent i = new Intent(this, MapsActivity.class);
        Bundle bun = new Bundle();
        bun.putSerializable("buildings", hertiageBuildings);
        i.putExtras(bun);
        startActivity(i);
    }
    */

    public void minAgeOnChange(){
        minAge = Integer.parseInt(minText.getText().toString());
    }

    public void maxAgeOnChange(){
        maxAge = Integer.parseInt(maxText.getText().toString());
    }

    public void openMaps(View view){
        Intent i = new Intent(this, MapsActivity.class);
        Bundle bun = new Bundle();
        bun.putSerializable("buildings", hertiageBuildings);
        bun.putInt("min", minAge);
        bun.putInt("max", maxAge);
        i.putExtras(bun);
        startActivity(i);
    }
/*
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
*/
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
