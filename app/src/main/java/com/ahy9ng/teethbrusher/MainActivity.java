package com.ahy9ng.teethbrusher;

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

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

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

    public double getMean(ArrayList<Double> data)
    {
        double sum = 0.0;
        for(double a : data)
            sum += a;
        return sum/data.size();
    }

    public double getVariance(ArrayList<Double> data)
    {
        double mean = getMean(data);
        double temp = 0;
        for(double a :data)
            temp += (mean-a)*(mean-a);
        return temp/data.size();
    }

    public double getStdDev(ArrayList<Double> data)
    {
        return Math.sqrt(getVariance(data));
    }

    public void classify(ArrayList<Double> x,ArrayList<Double> y,ArrayList<Double> z) {
        double mean_x = getMean(x);
        double std_x = getStdDev(x);
        double mean_y = getMean(y);
        double std_y = getStdDev(y);
        double mean_z = getMean(z);
        double std_z = getStdDev(z);
        //Log.e("", (mean_x + " " + std_x + " " + mean_y + " " + std_y + " " + mean_z + " " + std_z));
        try {
            outputStream.write((mean_x + " " + std_x + " " + mean_y + " " + std_y + " " + mean_z + " " + std_z).getBytes());
        } catch (Exception e){
            e.printStackTrace();
        }

//        Log.e("",x.toString());
//        Log.e("",y.toString());
//        Log.e("",z.toString());
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
