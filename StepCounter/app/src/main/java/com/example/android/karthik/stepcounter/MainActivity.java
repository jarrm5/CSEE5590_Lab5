package com.example.android.karthik.stepcounter;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener, StepListener {
    private TextView textView;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor sensor;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private int numSteps;
    TextView TvSteps;
    Button BtnStart;
    Button BtnStop;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: 1. Get an instance of the SensorManager and assign it to the variable 'sensorManager'
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        //Also get an instance of approriate sensor and assign it the variable 'sensor'
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //TODO: 2. Create an instance of StepDetector &assign it to variable 'simpleStepDetector' and register a listener on it
        //The simpleStepDetector uses a method to calculate x,y,z vectors that more accurately detects a step than the native Sensor.TYPE_STEP_DETECTOR
        simpleStepDetector = new StepDetector();
        //Register the main activity as a listener to the simpleStepDetector
        simpleStepDetector.registerListener(this);

        //TODO: 3. Get the views from the activity_main.xml and assign to variables 'TvSteps','BtnStart','BtnStop' variables suitably
        TvSteps = findViewById(R.id.tv_steps);
        BtnStart = findViewById(R.id.btn_start);
        BtnStop = findViewById(R.id.btn_stop);

        BtnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                numSteps = 0;
                TvSteps.setText(TEXT_NUM_STEPS + numSteps);
                sensorManager.registerListener(MainActivity.this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
            }
        });


        BtnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                TvSteps.setText("");
                sensorManager.unregisterListener(MainActivity.this);
            }
        });



    }



    /*
        Provides reference to the Sensor object that changed and the new accuracy of the sensor
    */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /*
        Provides a sensorEvent object that contains information about the new sensor data, including
        the accuracy of the data, the sensor that generated the data, the timestamp at which the data
        was generated, and the new data that the sensor recorded.

        The event associated with Sensor.TYPE_ACCELOREMETER provides x,y,z values when fired.
     */

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    /*
        When simpleStepDetector detects a step, update the step counter and UI in the activity
     */
    @Override
    public void step(long timeNs) {
        numSteps++;
        TvSteps.setText(TEXT_NUM_STEPS + numSteps);
    }

}
