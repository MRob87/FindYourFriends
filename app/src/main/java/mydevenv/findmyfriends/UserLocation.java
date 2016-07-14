package mydevenv.findmyfriends;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by MRob on 7/13/2016.
 */
public class UserLocation extends AppCompatActivity {

    private static final String TAG = "UserLocation";

    LocationListener locationListener;

    LocationManager locationManager;

    private float correctBearing = 0;

    ImageView bearingHand;

    TextView tvGo;

    TextView tvDistance;

    Compass compass;

    public float getCorrectBearing() {
        return correctBearing;
    }

    public UserLocation(Context context) {
        Log.d(TAG, "constructor");

        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                makeUseOfNewLocation(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        // Register the listener with the Location Manager to receive location updates
        if ( ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED ) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else {
            Log.d(TAG, "=============================");
            Log.d(TAG, "=============================");
            Log.d(TAG, "We do not have GPS permission");
            Log.d(TAG, "=============================");
            Log.d(TAG, "=============================");
        }
    }

    public void makeUseOfNewLocation(Location location) {
        // cool shit happens here
        // location.distanceTo
        Location dest = new Location("");

        // random place about 50mi away
        //dest.setLatitude(38.03389023);
        //dest.setLongitude(-90.01414604);

        dest.setLatitude(38.733755);
        dest.setLongitude(-90.613750);
        float distanceToM = location.distanceTo(dest);
        double distanceToFt = distanceToM * 3.28084;
        float distanceToKM = distanceToM / 1000;
        double distanceToMi = distanceToKM * 0.621371;

        if (distanceToMi < 1) {
            tvDistance.setText(Math.round(distanceToM) + " m/" + Math.round(distanceToFt) + " ft");
        } else {
            tvDistance.setText(Math.round(distanceToKM) + " km/" + Math.round(distanceToMi) + " mi");
        }

        float bearing = location.bearingTo(dest);
        float accuracy = location.getAccuracy();

        Animation an = new RotateAnimation(correctBearing, bearing,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        correctBearing = bearing;

        an.setDuration(500);
        an.setRepeatCount(0);
        an.setFillAfter(true);

        bearingHand.startAnimation(an);

        tvGo.setText("Go: " + Math.round(correctBearing) + "\u00b0 ");

        float guidingDirection = correctBearing - compass.getCorrectDirection();

        Log.d(TAG, "bearing: " + bearing + ", correctDirection: " + compass.getCorrectDirection() + ", arrow should point: " + guidingDirection + ", getLatitude: " + location.getLatitude() + ", getLongitude: " + location.getLongitude() + ", distanceToMi: " + distanceToMi );
    }
}
