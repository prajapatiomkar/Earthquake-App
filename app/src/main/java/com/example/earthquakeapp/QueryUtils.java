package com.example.earthquakeapp;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    public static final String LOG_TAG = "Message";

    public static ArrayList<Earthquake> fetchEarthquakeData(String requestUrl) {
        URL url = createURL(requestUrl);

        String jsonRespone = null;
        try {
            jsonRespone = makeHttpRequest(url);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        ArrayList<Earthquake> earthquakes = extractFeatureFromJson(jsonRespone);

        return earthquakes;

    }

    private static ArrayList<Earthquake> extractFeatureFromJson(String jsonRespone) {
        if (TextUtils.isEmpty(jsonRespone)) {
            return null;
        }

        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        try {
            JSONObject baseJsonObject = new JSONObject(jsonRespone);
            JSONArray earthquakeArray = baseJsonObject.getJSONArray("features");
            for (int i = 0; i < earthquakeArray.length(); i++) {
                JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);
                JSONObject properties = currentEarthquake.getJSONObject("properties");
                double magnitude = properties.getDouble("mag");
                String location = properties.getString("place");
                long time = properties.getLong("time");
                String url = properties.getString("url");
                Earthquake earthquake = new Earthquake(magnitude, location, time, url);
                earthquakes.add(earthquake);
            }
        } catch (Exception e) {

        }

        return earthquakes;

    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection con = null;
        InputStream inputStream = null;

        con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(10000);
        con.setReadTimeout(15000);
        con.connect();

        if (con.getResponseCode() == 200) {
            inputStream = con.getInputStream();
            jsonResponse = readFromStream(inputStream);
        }
        return jsonResponse;

    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createURL(String requestUrl) {
        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }
}
