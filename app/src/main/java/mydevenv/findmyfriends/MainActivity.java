package mydevenv.findmyfriends;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static Integer REQUEST_CODE = 0;

    private Compass compass;

    private UserLocation location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }

        // create our main activity which contains the compass n stuff
        setContentView(R.layout.activity_main);

        compass = new Compass(this);

        compass.image = (ImageView) findViewById(R.id.imageViewCompass);

        compass.tvHeading = (TextView) findViewById(R.id.tvHeading);

        compass.guidingDirectionHand = (ImageView) findViewById(R.id.guidingDirectionHand);

        location = new UserLocation(this);

        // share these objects, need to get at their values on updates
        compass.userLocation = location;
        location.compass = compass;

        location.bearingHand = (ImageView) findViewById(R.id.bearingHand);

        location.tvGo = (TextView) findViewById(R.id.tvGo);

        location.tvDistance = (TextView) findViewById(R.id.tvDistance);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "start compass");
        compass.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        compass.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        compass.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "stop compass");
        compass.stop();
    }

}
