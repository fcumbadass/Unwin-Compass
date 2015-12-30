package com.unwin.compass.compass;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.unwin.wearcompass.R;

public class WearActivity extends Activity implements SensorEventListener {

    private String TAG = "WearActivity";
    private ImageView image;
    private SensorManager mSensorManager;
    private float currentDegree = 0f;
    public TextView heading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wear);
        //final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);

        image = (ImageView) findViewById(R.id.imageViewCompass);
        heading = (TextView) findViewById(R.id.heading);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //disable to make it visable fam
        image.setVisibility(View.INVISIBLE);
    }

    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
    }

    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            getOrientation(sensorEvent);
        }
    }

    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void getOrientation(SensorEvent event){
        // get the angle around the z-axis rotated
        int degree = Math.round(event.values[0]);

        heading.setText("" + Integer.toString(degree) + "");

        //tvHeading.setText("Heading: " + Float.toString(degree) + " degrees");

        // create a rotation animation (reverse turn degree degrees)
        RotateAnimation ra = new RotateAnimation(
                currentDegree,
                -degree,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);

        // how long the animation will take place
        ra.setDuration(100);

        // set the animation after the end of the reservation status
        ra.setFillAfter(true);

        // Start the animation
        image.startAnimation(ra);
        currentDegree = -degree;

    }

    public void needleClicked(View view){
        Toast.makeText(this, "Shake watch to increase accuracy", Toast.LENGTH_SHORT).show();
    }
}
