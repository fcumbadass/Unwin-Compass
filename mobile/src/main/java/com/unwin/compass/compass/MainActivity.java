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


public class MainActivity extends Activity implements SensorEventListener {

    public ImageView image;

    private float currentDegree = 0f;

    private SensorManager mSensorManager;

    public TextView tvHeading;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = (ImageView) findViewById(R.id.imageViewCompass);
        //image = (ImageView) findViewById(R.id.compass);
        tvHeading = (TextView) findViewById(R.id.tvHeading);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
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

    public void getOrientation(SensorEvent event)    {
        // get the angle around the z-axis rotated
        int degree = Math.round(event.values[0]);

        tvHeading.setText("Heading: " + Integer.toString(degree) + " degrees");

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

    public void showDialog(View v){
        Toast.makeText(this, "If compass is inaccurate, shaking helps to calibrate", Toast.LENGTH_LONG).show();
    }
}
