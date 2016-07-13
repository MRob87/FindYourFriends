package mydevenv.findmyfriends;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class Compass implements SensorEventListener {

    private static final String TAG = "Compass";

    // define the display assembly compass picture
    public ImageView image = null;

    // device sensor manager
    private SensorManager mSensorManager;
    private Sensor gsensor;
    private Sensor msensor;
    private float[] mGravity = new float[3];
    private float[] mGeomagnetic = new float[3];
    private float direction = 0f;
    private float correctDirection = 0;

    TextView tvHeading;

    public Compass(Context context) {

        // initialize your android device sensor capabilities
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        gsensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        msensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    public void start() {
        //setContentView(R.layout.activity_main);

        // TextView that will tell the user what degree is he heading
        mSensorManager.registerListener(this, gsensor,
                SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, msensor,
                SensorManager.SENSOR_DELAY_GAME);
    }

    public void stop() {
        mSensorManager.unregisterListener(this);
    }

    private void adjustArrow() {
        if (image == null) {
            Log.i(TAG, "arrow view is not set");
            return;
        }

        Log.i(TAG, "will set rotation from " + correctDirection + " to " + direction);

        String cardinality = "";

        if (direction > 330 || direction < 30) {
            cardinality = "N";
        } else if (direction >= 30 && direction <= 60) {
            cardinality = "NE";
        } else if (direction > 60 && direction < 120) {
            cardinality = "E";
        } else if (direction >= 120 && direction <= 150) {
            cardinality = "SE";
        } else if (direction > 150 && direction < 210) {
            cardinality = "S";
        } else if (direction >= 210 && direction <= 240) {
            cardinality = "SW";
        } else if (direction > 240 && direction < 300) {
            cardinality = "W";
        } else if (direction >= 300 && direction <= 330) {
            cardinality = "NW";
        }



        tvHeading.setText("Heading: " + Math.round(direction) + "\u00b0 " + cardinality);

        Animation an = new RotateAnimation(-correctDirection, -direction,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        correctDirection = direction;

        an.setDuration(500);
        an.setRepeatCount(0);
        an.setFillAfter(true);

        image.startAnimation(an);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        final float alpha = 0.97f;

        synchronized (this) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

                mGravity[0] = alpha * mGravity[0] + (1 - alpha)
                        * event.values[0];
                mGravity[1] = alpha * mGravity[1] + (1 - alpha)
                        * event.values[1];
                mGravity[2] = alpha * mGravity[2] + (1 - alpha)
                        * event.values[2];

                // mGravity = event.values;

                // Log.e(TAG, Float.toString(mGravity[0]));
            }

            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                // mGeomagnetic = event.values;

                mGeomagnetic[0] = alpha * mGeomagnetic[0] + (1 - alpha)
                        * event.values[0];
                mGeomagnetic[1] = alpha * mGeomagnetic[1] + (1 - alpha)
                        * event.values[1];
                mGeomagnetic[2] = alpha * mGeomagnetic[2] + (1 - alpha)
                        * event.values[2];
                // Log.e(TAG, Float.toString(event.values[0]));

            }

            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity,
                    mGeomagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                // Log.d(TAG, "direction (rad): " + direction);
                direction = (float) Math.toDegrees(orientation[0]); // orientation
                direction = (direction + 360) % 360;
                // Log.d(TAG, "direction (deg): " + direction);
                adjustArrow();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }
}

