package com.ahy9ng.teethbrusher;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.util.Log;

import java.io.FileOutputStream;
import java.util.ArrayList;

public class MainActivity extends Activity implements SensorEventListener{
    public boolean init = false;
    SensorManager sMngr;
    Sensor snsr;
    Long prevTime;
    Long currentTime;
    public static LinearLayout ll;

    ArrayList<Double> x,y,z;
    String outfile = "data";
    FileOutputStream outputStream;

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
        try {
            outputStream = openFileOutput(outfile, CONTEXT_IGNORE_SECURITY);

        }catch (Exception e){
            e.printStackTrace();
        }



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

    public void classify(ArrayList<Double> x,ArrayList<Double> y,ArrayList<Double> z) {
        Log.e("", getApplicationContext().getFilesDir().getAbsolutePath());
        try {
            outputStream.write((x.toString() + " " + y.toString() + " " + z.toString()).getBytes());
        } catch (Exception e){
            e.printStackTrace();
        }

//        Log.e("",x.toString());
//        Log.e("",y.toString());
//        Log.e("",z.toString());
    }


}
