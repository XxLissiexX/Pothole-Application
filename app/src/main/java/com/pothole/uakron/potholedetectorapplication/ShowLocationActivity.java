package com.pothole.uakron.potholedetectorapplication;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ShowLocationActivity extends Activity implements LocationListener
{
    private TextView latituteField;
    private TextView longitudeField;
    private Button Potholebutton;
    private LocationManager locationManager;
    private String provider;
    File pothole_txt_file;
    public boolean TIMESTAMP_RECIEVED = false; //init the variable to false

    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_location);

        //////////////////////////////////// //file stuff///////////////////////////////////////
        //get the date
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        //grab the root directory as a string.
        String rootDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

        //create a new file to place the history text and Media into.
        File textDocumentDir= new File(rootDir+ File.separator + date);

        // Create the storage directory if it does not exist
        textDocumentDir.mkdirs();

        // Create a file name
        pothole_txt_file = new File(textDocumentDir.getPath() + File.separator +"Pothole_Locations"+ ".txt");

        /////////////////////////////////////////////////////////////////////////////////////////////////////

        latituteField = (TextView) findViewById(R.id.TextView02);
        longitudeField = (TextView) findViewById(R.id.TextView04);


        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null)
        {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        }
        else
        {
            latituteField.setText("Location not available");
            longitudeField.setText("Location not available");
        }



    }
    public void potholeFound(View view)
    {
        TIMESTAMP_RECIEVED=true;
    }


    /* Request updates at startup */
    @Override
    protected void onResume()
    {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause()
    {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location)
    {

        //listen to bluetooth for a signal.
        //right now we could use the press of a button.


        if(TIMESTAMP_RECIEVED)
        {

            //if we get a timestamp, we will log this location to the file.

            double lat = location.getLatitude();
            double lng = location.getLongitude();
            latituteField.setText(String.valueOf(lat));
            longitudeField.setText(String.valueOf(lng));

            try {
                Calendar c = Calendar.getInstance();
                int hours = c.get(Calendar.HOUR);
                int minute = c.get(Calendar.MINUTE);
                int seconds = c.get(Calendar.SECOND);
                BufferedWriter buf = new BufferedWriter(new FileWriter(pothole_txt_file, true));
                buf.append("Latitude: " + String.valueOf(lat) + " Longitude: " + String.valueOf(lng) + " Time: " + hours + ":" + minute + ":" + seconds);
                buf.newLine();
                buf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            TIMESTAMP_RECIEVED =false;
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider)
    {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider)
    {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }
}
