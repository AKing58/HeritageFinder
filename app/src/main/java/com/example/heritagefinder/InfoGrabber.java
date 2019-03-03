package com.example.heritagefinder;


import android.util.Log;

import com.google.android.gms.common.util.IOUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class InfoGrabber {

    public static String AgeAddress = "http://opendata.newwestcity.ca/downloads/building-age/BUILDING_AGE.json";
    public static String HeritageAddress = "http://opendata.newwestcity.ca/downloads/heritage-register/HERITAGE_REGISTER.json";

    public String downloadedRaw;

    private InputStream getStream(String address) {
        Log.d("xyz","start getStream");
        try {
            URL url = new URL(address);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(10000);
            Log.d("xyz","getStream done");
            return urlConnection.getInputStream();
        } catch (Exception ex) {

            Log.d("xyz","getStream exception "+ex.toString());
            return null;
        }

    }

    public boolean download(String address){
        Log.d("xyz","start download");
        InputStream is = getStream(address);
        StringBuilder builder = new StringBuilder();
        String line;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, Charset.defaultCharset()))) {
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
        } catch (Exception e){
            return false;
        }

        downloadedRaw = builder.toString();
        Log.d("xyz","done download");
        return false;
    }
}
