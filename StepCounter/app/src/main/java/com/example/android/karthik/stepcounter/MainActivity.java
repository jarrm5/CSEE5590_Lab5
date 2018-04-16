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

        TvSteps = findViewById(R.id.tv_steps);
        BtnStart = findViewById(R.id.btn_start);
        BtnStop = findViewById(R.id.btn_stop);

        //TODO: 1. Get an instance of the SensorManager and assign it to the variable 'sensorManager'
                    //Also get an instance of approriate sensor and assign it the variable 'sensor'
        //TODO: 2. Create an instance of StepDetector &assign it to variable 'simpleStepDetector' and register a listener on it

        //TODO: 3. Get the views from the activity_main.xml and assign to variables 'TvSteps','BtnStart','BtnStop' variables suitably




        BtnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                numSteps = 0;
                sensorManager.registerListener(MainActivity.this, sensor, SensorManager.SENSOR_DELAY_FASTEST);

            }
        });


        BtnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                sensorManager.unregisterListener(MainActivity.this);

            }
        });



    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
//        TextView TvSteps = (TextView) findViewById(R.id.tv_steps);
        numSteps++;
        TvSteps.setText(TEXT_NUM_STEPS + numSteps);
    }

}
