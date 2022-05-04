package com.example.earthquakeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    private static String LOCATION_SEPARATOR = " of ";

    public EarthquakeAdapter(@NonNull Context context, @NonNull List<Earthquake> objects) {
        super(context, 0, objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        Earthquake currentEarthquake = getItem(position);

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item, parent, false);
        }
        String originalLocation = currentEarthquake.getmLocation();
        String primaryLocation;
        String locationOffset;

        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        }else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation =originalLocation;
        }
        //MagnitudeView
        TextView magnitude = listItemView.findViewById(R.id.magnitude);
        String formattedMagnitude = formatMagnitude(currentEarthquake.mMagnitude);
        magnitude.setText(formattedMagnitude);
        //LocationView
        TextView primaryLocationView = listItemView.findViewById(R.id.primaryLocation);
        primaryLocationView.setText(primaryLocation);

        TextView secondaryLocationView = listItemView.findViewById(R.id.secondaryLocation);
        secondaryLocationView.setText(locationOffset);
        //Date
        Date dateObject = new Date(currentEarthquake.mTimeInMilliSecond);

        TextView date = listItemView.findViewById(R.id.date);
        String formattedData = getFormateDate(dateObject);
        date.setText(formattedData);

        TextView time= listItemView.findViewById(R.id.time);
        String formattedTime = getFormateTime(dateObject);
        time.setText(formattedTime);

        return listItemView;
    }

    private String getFormateTime(Date dateObject) {
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm a");
        return simpleTimeFormat.format(dateObject);
    }

    private String getFormateDate(Date dateObject) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd,mm yyyy");
        return simpleDateFormat.format(dateObject);
    }


    private String formatMagnitude(double mMagnitude) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(mMagnitude);
    }
}
