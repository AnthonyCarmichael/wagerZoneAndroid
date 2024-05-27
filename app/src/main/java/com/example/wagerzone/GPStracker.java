package com.example.wagerzone;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

/**
 * Cette classe permet d'avoir la géolocalisation de l'utilisateur pour adapter le form d'inscription
 * et de mofification de l'utilisateur automatiquement.
 *  @author Anthony Carmichael
 *  @version 1.0
 */
public class GPStracker implements LocationListener {
    Context context;
    public  GPStracker(Context c){
        context = c;
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {}

    public Location getLocation() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(context, "Permission not granted", Toast.LENGTH_SHORT).show();
            return null;
        }
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(isGPSEnabled){
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,6000,10,this);
            return lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } else {
            Toast.makeText(context,"Please enable GPS.",Toast.LENGTH_SHORT).show();

        }
        return null;
    }
}
