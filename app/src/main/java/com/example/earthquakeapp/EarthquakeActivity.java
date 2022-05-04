package com.example.earthquakeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {
    String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=2&limit=10";
    private EarthquakeAdapter earthquakeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);
        getSupportActionBar().hide();
        //AsyncTask
        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        task.execute(USGS_REQUEST_URL);

        //ListView
        ListView earthquakeListView = findViewById(R.id.list);

        earthquakeAdapter = new EarthquakeAdapter(getApplicationContext(), new ArrayList<Earthquake>());
        earthquakeListView.setAdapter(earthquakeAdapter);

        //onClick
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Earthquake currentEarthquake = earthquakeAdapter.getItem(i);

                Uri earthquakeUri = Uri.parse(currentEarthquake.getmUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                startActivity(intent);
            }
        });


    }

    //AsyncTask
    private class EarthquakeAsyncTask extends AsyncTask<String, Void, ArrayList<Earthquake>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Earthquake> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            ArrayList<Earthquake> result = QueryUtils.fetchEarthquakeData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<Earthquake> earthquake) {

            earthquakeAdapter.clear();
            if (earthquake != null && !earthquake.isEmpty()) {
                earthquakeAdapter.addAll(earthquake);
            }
        }
    }
}