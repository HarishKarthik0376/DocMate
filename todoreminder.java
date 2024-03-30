package com.example.doctormate;

import android.Manifest;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctormate.models.remindermodel;
import com.example.doctormate.adapters.reminderadapter;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

public class todoreminder extends AppCompatActivity {
    String typeofreminder = "";
    String contenttaskdes = "";
    String time;
    int drinkwatercount = 0;
    int exercisecount = 0;
    int wakeupcount = 0;
    int currenthr;
    String randomUUIDString;
    String Uuid;
    int currentmins;
    Intent intent;
    LinearLayoutManager linearLayoutManager;
    reminderadapter reminderadapter;
    ArrayList<remindermodel> arrayList = new ArrayList<>();
    RecyclerView recyclerView;
    Dialog dialog;
    Calendar calendar = Calendar.getInstance();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final int RC_NOTIFICATION = 81;
    EditText contenttaskdescription;
    Gson gson;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_todoreminder);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sharedPreferences = getSharedPreferences("reminderhistory", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        arrayList.clear();
        MaterialButton drinkwater, exercise, wakeup, addremindder;
        TextView timetextview;
        ImageView addtaskicon,goback;
        addtaskicon = findViewById(R.id.addtask);
        dialog = new Dialog(todoreminder.this);
        dialog.setContentView(R.layout.addtask);
        drinkwater = dialog.findViewById(R.id.drinkwater);
        exercise = dialog.findViewById(R.id.exercise);
        wakeup = dialog.findViewById(R.id.wakeup);
        addremindder = dialog.findViewById(R.id.submittask);
        contenttaskdescription = dialog.findViewById(R.id.taskdescription);
        timetextview = dialog.findViewById(R.id.selecttime);
        recyclerView = findViewById(R.id.reminderrecyclerview);
        loadDatanew();
        goback = findViewById(R.id.gobacktodoreminder);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gobackintent = new Intent(todoreminder.this, MainActivity.class);
                startActivity(gobackintent);
                finishAffinity();
                finish();
            }
        });

        drinkwater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drinkwatercount == 0) {
                    typeofreminder = "Drink Water";
                    drinkwater.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                    drinkwater.setTextColor(getResources().getColorStateList(R.color.black));
                    drinkwater.setStrokeColor(getResources().getColorStateList(R.color.lightgreen));
                    exercise.setBackgroundTintList(getResources().getColorStateList(R.color.black));
                    exercise.setTextColor(getResources().getColorStateList(R.color.white));
                    exercise.setStrokeColor(getResources().getColorStateList(R.color.red));
                    wakeup.setBackgroundTintList(getResources().getColorStateList(R.color.black));
                    wakeup.setTextColor(getResources().getColorStateList(R.color.white));
                    wakeup.setStrokeColor(getResources().getColorStateList(R.color.orange));
                    drinkwatercount = 1;
                    exercisecount = 0;
                    wakeupcount = 0;
                    contenttaskdescription.setText("Time To Drink Water Buddy!!");

                }
            }
        });
        exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exercisecount == 0) {
                    typeofreminder = "Exercise Reminder";
                    exercise.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                    exercise.setTextColor(getResources().getColorStateList(R.color.black));
                    exercise.setStrokeColor(getResources().getColorStateList(R.color.lightgreen));
                    drinkwater.setBackgroundTintList(getResources().getColorStateList(R.color.black));
                    drinkwater.setTextColor(getResources().getColorStateList(R.color.white));
                    drinkwater.setStrokeColor(getResources().getColorStateList(R.color.lightblue));
                    wakeup.setBackgroundTintList(getResources().getColorStateList(R.color.black));
                    wakeup.setTextColor(getResources().getColorStateList(R.color.white));
                    wakeup.setStrokeColor(getResources().getColorStateList(R.color.orange));
                    exercisecount = 1;
                    drinkwatercount = 0;
                    wakeupcount = 0;
                    contenttaskdescription.setText("Its Time To Hit The Gym Buddy!!");

                }
            }
        });
        wakeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wakeupcount == 0) {
                    typeofreminder = "Sleep Reminder";
                    wakeup.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                    wakeup.setTextColor(getResources().getColorStateList(R.color.black));
                    wakeup.setStrokeColor(getResources().getColorStateList(R.color.lightgreen));
                    exercise.setBackgroundTintList(getResources().getColorStateList(R.color.black));
                    exercise.setTextColor(getResources().getColorStateList(R.color.white));
                    exercise.setStrokeColor(getResources().getColorStateList(R.color.red));
                    drinkwater.setBackgroundTintList(getResources().getColorStateList(R.color.black));
                    drinkwater.setTextColor(getResources().getColorStateList(R.color.white));
                    drinkwater.setStrokeColor(getResources().getColorStateList(R.color.lightblue));
                    wakeupcount = 1;
                    drinkwatercount = 0;
                    exercisecount = 0;
                    contenttaskdescription.setText("Time To Sleep Buddy!!");

                }
            }
        });
        timetextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currenthr = calendar.get(Calendar.HOUR_OF_DAY);
                currentmins = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(todoreminder.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String modifiedtime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                        time = modifiedtime;
                        timetextview.setText(modifiedtime);
                    }
                }, currenthr, currentmins, false);
                timePickerDialog.setTitle("Choose Time");
                timePickerDialog.show();
            }
        });

        addtaskicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        linearLayoutManager = new LinearLayoutManager(todoreminder.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        reminderadapter = new reminderadapter(todoreminder.this, arrayList);
        addremindder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!typeofreminder.equals("") && !timetextview.getText().toString().equals("") && !contenttaskdescription.getText().toString().equals("")) {
                    gson = new Gson();
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU) {
                        requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS},RC_NOTIFICATION);
                    }
                    else
                    {
                        contenttaskdes = contenttaskdescription.getText().toString();
                        remindermodel remindermodel;
                        UUID uuid = UUID.randomUUID();
                        randomUUIDString = uuid.toString();
                        remindermodel = new remindermodel(typeofreminder, contenttaskdes, time,randomUUIDString);
                        arrayList.add(remindermodel);
                        recyclerView.setAdapter(reminderadapter);
                        sharedPreferences = getSharedPreferences("reminderhistory", Context.MODE_PRIVATE);
                        editor.putString(randomUUIDString + "_actualkey", randomUUIDString);
                        String updatedJson = gson.toJson(arrayList);
                        editor.putString("reminderdata", updatedJson);
                        editor.apply();
                        dialog.dismiss();
                    }

                }
                else if(typeofreminder.equals("") || timetextview.getText().toString().equals("") || contenttaskdescription.getText().toString().equals("")){
                    Toast.makeText(todoreminder.this, "All Fields Are Mandatory", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                else
                {
                    Toast.makeText(todoreminder.this, "All Fields Are Mandatory", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
        recyclerView.setAdapter(reminderadapter);

    }

    public void removedata(remindermodel item) {
        if (item!=null) {
            arrayList.remove(item);
            Gson gson = new Gson();
            String json = gson.toJson(arrayList);
            editor.putString("reminderdata", json);
            editor.commit();
        } else {
            Toast.makeText(this, "Empty!!", Toast.LENGTH_SHORT).show();
        }
        if(arrayList.size()==0)
        {
        }
    }
    private void loadDatanew() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("reminderdata", null);
        Type type = new TypeToken<ArrayList<remindermodel>>() {
        }.getType();
        arrayList = gson.fromJson(json, type);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == RC_NOTIFICATION)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                contenttaskdes = contenttaskdescription.getText().toString();
                remindermodel remindermodel;
                UUID uuid = UUID.randomUUID();
                randomUUIDString = uuid.toString();
                remindermodel = new remindermodel(typeofreminder, contenttaskdes, time,randomUUIDString);
                arrayList.add(remindermodel);
                sharedPreferences = getSharedPreferences("reminderhistory", Context.MODE_PRIVATE);
                editor.putString(randomUUIDString + "_actualkey", randomUUIDString);
                String updatedJson = gson.toJson(arrayList);
                editor.putString("reminderdata", updatedJson);
                editor.apply();
                dialog.dismiss();
            }
            else
            {
                dialog.dismiss();

            }
        }
    }
}