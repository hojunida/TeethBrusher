package com.ahy9ng.teethbrusher;
import java.util.ArrayList;
import java.util.Timer;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.util.Log;

public class MainActivity extends Activity implements SensorEventListener{
    public boolean init = false;
    SensorManager sMngr;
    Sensor snsr;
    Long prevTime;
    Long currentTime;
    public static LinearLayout ll;

    ArrayList<Double> x,y,z;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sMngr = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        snsr = (Sensor)sMngr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        prevTime = System.currentTimeMillis();
        x = new ArrayList<Double>();
        y = new ArrayList<Double>();
        z = new ArrayList<Double>();



    }
    protected void onResume(){
        super.onResume();
        sMngr.registerListener(this, snsr, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {

    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        x.add((double) event.values[0]);
        y.add((double) event.values[1]);
        z.add((double) event.values[2]);
        currentTime = System.currentTimeMillis();
        if(currentTime - prevTime > 5000){
            prevTime = currentTime;
            classify(x,y,z);
        }

    }

    public void classify(ArrayList<Double> x,ArrayList<Double> y,ArrayList<Double> z){
        Log.e("",x.toString());
        Log.e("",y.toString());
        Log.e("",z.toString());
    }
    public void getInternalTextFileString(){
        try {
            FileInputStream inStream = this.openFileInput("myStringFile");
            InputStreamReader inputStreamReader = new InputStreamReader(inStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder finalString = new StringBuilder(); String oneLine;
            while ((oneLine = bufferedReader.readLine()) != null) {
                finalString.append(oneLine);
            }
            bufferedReader. close();
            inStream. close();
            inputStreamReader. close();
            //textView. setVisibility(View.VISIBLE); textView.setText(finalString.toString());
    } catch (Exception e) { e.printStackTrace(); } }


}
