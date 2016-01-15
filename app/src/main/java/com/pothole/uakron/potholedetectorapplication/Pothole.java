package com.pothole.uakron.potholedetectorapplication;

/**
 * Created by Alissa McGill on 11/6/2015.
 */
public class Pothole
{
    private double latitude;
    private double longitude;
    private String timestamp;


    //Constructor 1
    public Pothole()
    {
        latitude =0;
        longitude=0;
        timestamp="";
    }

    //Constructor 2
    public Pothole(double lati, double longi, String time)
    {
        latitude =lati;
        longitude=longi;
        timestamp=time;
    }

    //////////////////////////SETTERS AND GETTERS/////////////////////////
    public void setLatitude(double lati)
    {
        this.latitude =lati;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLongitude(double longi)
    {
        this.longitude = longi;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setTimestamp(String time)
    {
        this.timestamp = time;
    }
}
