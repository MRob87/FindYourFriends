//package mydevenv.findmyfriends;
//
//import android.hardware.SensorEvent;
//import android.os.AsyncTask;
//import android.view.animation.Animation;
//import android.view.animation.RotateAnimation;
//
//public class RotateAsyncTask extends AsyncTask<SensorEvent, Float, String> {
//
//    @Override
//    protected String doInBackground(SensorEvent... sensorEvents) {
//        // create a rotation animation (reverse turn degree degrees)
//        RotateAnimation ra = new RotateAnimation(
//                currentDegree,
//                -degree,
//                Animation.RELATIVE_TO_SELF, 0.5f,
//                Animation.RELATIVE_TO_SELF,
//                0.5f);
//
//        // how long the animation will take place
//        ra.setDuration(210);
//
//        // set the animation after the end of the reservation status
//        ra.setFillAfter(true);
//
//        // Start the animation
//        image.startAnimation(ra);
//        currentDegree = -degree;
//    }
//}