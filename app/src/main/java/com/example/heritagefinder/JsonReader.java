package com.example.heritagefinder;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonReader {
    public static ArrayList<Building> readerBuildingAge(String s){
        ArrayList<Building> buildings = new ArrayList<>();
        try{
            JSONObject obj = new JSONObject(s);
            JSONArray jArray = obj.getJSONArray("features");
            for(int i = 0; i < jArray.length();i++){
                JSONObject jFeature = jArray.getJSONObject(i);
                JSONObject properties = jFeature.getJSONObject("properties");
                String buildingName = properties.getString("BLDGNAM");

                // can't seem to properly grab geometry

                //JSONObject geometry = jFeature.getJSONObject("geometry");
                //JSONArray geo = jFeature.getJSONArray("geometry");
                //JSONArray lines  = jFeature.getJSONObject("geometry").getJSONArray("coordinates");
//                double[][] coords = new double[lines.length()][];
//                for (int n = 0; n < lines.length(); n++) {
//                    JSONArray xyJson = lines.getJSONArray(n);
//                    coords[n] = new double[xyJson.length()];   // length is always 2
//                    for (int j = 0; j < xyJson.length(); j++) {
//                        coords[n][j] = xyJson.getDouble(j);
//                    }
//                }
                //Building b = new Building(buildingName,coords[0][0],coords[0][1]);

                Building b = new Building(buildingName,0.1,0.1, 0, "");
                b.setName(buildingName);
                buildings.add(b);
            }

        } catch (JSONException e){
            Log.d("xyz","json reader BuildingAge error");
            e.printStackTrace();
        }
        return buildings;
    }

    public static ArrayList<Building> readerHeritageRegistry(String s){
        ArrayList<Building> buildings = new ArrayList<>();
        try{
            JSONObject obj = new JSONObject(s);
            JSONArray jArray = obj.getJSONArray("features");
            for(int i = 0; i < jArray.length();i++){
                JSONObject jFeature = jArray.getJSONObject(i);
                JSONObject properties = jFeature.getJSONObject("properties");
                String buildingName = properties.getString("NAME");
                double x = properties.getDouble("x");
                double y = properties.getDouble("y");
                int age = properties.getInt("AGE");
                String desc = properties.getString("DESC2");
                Building b = new Building(buildingName,y,x,age,desc);
                b.setName(buildingName);
                buildings.add(b);
            }

        } catch (JSONException e){
            Log.d("xyz","json reader HeritageRegistry error");
            e.printStackTrace();
        }
        return buildings;
    }
}
