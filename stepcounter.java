package com.example.doctormate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class stepcounter extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager = null;
    private Sensor stepsensor;
    Button start,reset;
    int totalsteps = 0;
    int previewtotalsteps = 0;
    ProgressBar progressBar;
    TextView textView1,steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_stepcounter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        progressBar = findViewById(R.id.progressbar);
        steps = findViewById(R.id.stepscontent);

        start = findViewById(R.id.startcounter);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaddata();
            }
        });

        reset = findViewById(R.id.resetcounter);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        resetsteps();
            }
        });
                sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
                stepsensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        ImageView gotohomepage;
        gotohomepage = findViewById(R.id.gotohomepage);
        gotohomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redir = new Intent(stepcounter.this,MainActivity.class);
                startActivity(redir);
                finish();
            }
        });
    }

    protected void onResume() {
        super.onResume();
        if (stepsensor == null) {
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
        } else {
            sensorManager.registerListener(this, stepsensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType() == Sensor.TYPE_STEP_COUNTER)
        {
            totalsteps = (int) event.values[0];
            int currentsteps = totalsteps-previewtotalsteps;
                    if (currentsteps >= 0) {
                        steps.setText(String.valueOf(currentsteps));
                        progressBar.setProgress(currentsteps);
                    }

        }
    }

    private void resetsteps() {
        int currentSteps = totalsteps - previewtotalsteps;
        previewtotalsteps = totalsteps;
        steps.setText("0");
        progressBar.setProgress(0);
        savedata();
    }

    private void savedata() {
        SharedPreferences sharedPreferences = getSharedPreferences("savesteps", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();
        editor.putString("stepkey",String.valueOf(previewtotalsteps));
        editor.apply();
    }

    private void loaddata() {
        SharedPreferences sharedPreferences = getSharedPreferences("savesteps", Context.MODE_PRIVATE);
        previewtotalsteps = Integer.parseInt(sharedPreferences.getString("stepkey", "0"));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    protected void onDestroy(){
        super.onDestroy();
        savedata();
    }
}
