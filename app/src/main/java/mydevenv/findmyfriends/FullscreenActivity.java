package mydevenv.findmyfriends;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {

    private static final String TAG = "FullscreenActivity";

    private MainActivity compass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create our main activity which contains the compass n stuff
        setContentView(R.layout.activity_main);
//        Thread timer = new Thread() {
//            public void run() {
//                try{
//                    sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } finally {
//                    Intent openMain = new Intent("mydevenv.findmyfriends.MAINACTIVITY");
//                    startActivity(openMain);
//                }
//            }
//        };
//        timer.start();
        compass = new MainActivity(this);

        //
        compass.image = (ImageView) findViewById(R.id.imageViewCompass);

        compass.tvHeading = (TextView) findViewById(R.id.tvHeading);
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
