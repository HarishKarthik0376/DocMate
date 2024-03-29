package com.example.doctormate;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctormate.adapters.heartrateadapter;
import com.example.doctormate.models.heartmodel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class heartratepage extends AppCompatActivity {
    ArrayList<Integer> arrayList = new ArrayList<>();
    boolean maleisclicked = false;
    boolean femaleisclicked = false;
    int agetodisplay;
    MaterialButton boy, girl, calculateheartrate;
    ImageView ageplus, ageminus, history1,goback,chart;
    MaterialCardView walking, running, swimming, resting, standing, sleeping;
    TextView heartrate,heartratetext;
    EditText age;
    LinearSnapHelper snapHelper;
    String selectedactivity;
    String typeofactivity = "";
    int numberis;
    int centerPosition;
    int currentpostion;
    int valueAtCenter=60;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String heartratetype,heartvalue,heartstatus,date,time;
    int count = 0;

    ArrayList<heartmodel> arrayList2 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_heartratepage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        history1 = findViewById(R.id.hearthis);
        goback = findViewById(R.id.gobackheartrate);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gobackintent = new Intent(heartratepage.this, actvitiespage.class);
                startActivity(gobackintent);
                finish();
            }
        });
        chart = findViewById(R.id.heartratechart);
        chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog;
                dialog = new Dialog(heartratepage.this);
                dialog.setContentView(R.layout.hearratechart);
                dialog.show();
            }
        });
        sharedPreferences = getSharedPreferences("hearthistory", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        ImageView heartimg;
        heartimg = findViewById(R.id.hearticon);
        recyclerView = findViewById(R.id.numberrecyclerview);
        for (int i = 0; i < 400; i++) {
            arrayList.add(i);
        }
        linearLayoutManager = new LinearLayoutManager(heartratepage.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        heartrateadapter adapter;
        heartrateadapter.OnNumberClickListener onNumberClickListener = number ->
        {
        };
        int positon = arrayList.indexOf(63);
        if (positon >= 63) {
            recyclerView.smoothScrollToPosition(positon);
        }
        snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        adapter = new heartrateadapter(arrayList, onNumberClickListener);
        recyclerView.setAdapter(adapter);
        Animation alpha = AnimationUtils.loadAnimation(heartratepage.this, R.anim.bigsmall);
        heartimg.startAnimation(alpha);
        age = findViewById(R.id.heartage);
        boy = findViewById(R.id.Maleheart);
        girl = findViewById(R.id.Femaleheart);
        ageplus = findViewById(R.id.heartageplus);
        ageminus = findViewById(R.id.heartageminus);
        boy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boy.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                girl.setBackgroundTintList(getResources().getColorStateList(R.color.signupred));
                maleisclicked = true;
                femaleisclicked = false;
            }
        });
        girl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                girl.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                boy.setBackgroundTintList(getResources().getColorStateList(R.color.signupred));
                maleisclicked = false;
                femaleisclicked = true;
            }
        });
        ageplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newage = age.getText().toString();
                int newage1 = Integer.parseInt(newage);
                agetodisplay = newage1 + 1;
                age.setText(String.valueOf(agetodisplay));

            }
        });
        ageminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newage = age.getText().toString();
                int newage1 = Integer.parseInt(newage);
                agetodisplay = newage1 - 1;
                if (agetodisplay >= 1) {
                    age.setText(String.valueOf(agetodisplay));
                } else {
                    age.setText(String.valueOf(1));
                }
            }
        });
        heartrate = findViewById(R.id.heartrate);
        heartratetext = findViewById(R.id.heratratetext);
        walking = findViewById(R.id.Walking);
        running = findViewById(R.id.Running);
        swimming = findViewById(R.id.Swimming);
        resting = findViewById(R.id.Resting);
        standing = findViewById(R.id.Standing);
        sleeping = findViewById(R.id.Sleeping);
        walking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedactivity = "Walking";
                typeofactivity = "Exercise Activity HR";
                Toast.makeText(heartratepage.this, typeofactivity, Toast.LENGTH_SHORT).show();
                walking.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                running.setBackgroundTintList(getResources().getColorStateList(R.color.lightblack));
                swimming.setBackgroundTintList(getResources().getColorStateList(R.color.lightblack));
                resting.setBackgroundTintList(getResources().getColorStateList(R.color.lightblack));
                standing.setBackgroundTintList(getResources().getColorStateList(R.color.lightblack));
                sleeping.setBackgroundTintList(getResources().getColorStateList(R.color.lightblack));
            }
        });
        running.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedactivity = "Running";
                typeofactivity = "Exercise Activity HR";
                Toast.makeText(heartratepage.this, typeofactivity, Toast.LENGTH_SHORT).show();
                running.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                swimming.setBackgroundTintList(getResources().getColorStateList(R.color.lightblack));
                resting.setBackgroundTintList(getResources().getColorStateList(R.color.lightblack));
                standing.setBackgroundTintList(getResources().getColorStateList(R.color.lightblack));
                sleeping.setBackgroundTintList(getResources().getColorStateList(R.color.lightblack));
                walking.setBackgroundTintList(getResources().getColorStateList(R.color.lightblack));
            }
        });
        swimming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedactivity = "Swimming";
                typeofactivity = "Exercise Activity HR";
                Toast.makeText(heartratepage.this, typeofactivity, Toast.LENGTH_SHORT).show();
                running.setBackgroundTintList(getResources().getColorStateList(R.color.lightblack));
                swimming.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                resting.setBackgroundTintList(getResources().getColorStateList(R.color.lightblack));
                standing.setBackgroundTintList(getResources().getColorStateList(R.color.lightblack));
                sleeping.setBackgroundTintList(getResources().getColorStateList(R.color.lightblack));
                walking.setBackgroundTintList(getResources().getColorStateList(R.color.lightblack));
            }
        });
        resting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedactivity = "Resting";
                typeofactivity = "Resting Activity HR";
                Toast.makeText(heartratepage.this, typeofactivity, Toast.LENGTH_SHORT).show();
                running.setBackgroundTintList(getResources().getColorStateList(R.color.lightblack));
                swimming.setBackgroundTintList(getResources().getColorStateList(R.color.lightblack));
                resting.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                standing.setBackgroundTintList(getResources().getColorStateList(R.color.lightblack));
                sleeping.setBackgroundTintList(getResources().getColorStateList(R.color.lightblack));
                walking.setBackgroundTintList(getResources().getColorStateList(R.color.lightblack));
            }
        });
        standing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedactivity = "Standing";
                typeofactivity = "Resting Activity HR";
                Toast.makeText(heartratepage.this, typeofactivity, Toast.LENGTH_SHORT).show();
                running.setBackgroundTintList(getResources().getColorStateList(R.color.lightblack));
                swimming.setBackgroundTintList(getResources().getColorStateList(R.color.lightblack));
                resting.setBackgroundTintList(getResources().getColorStateList(R.color.lightblack));
                standing.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                sleeping.setBackgroundTintList(getResources().getColorStateList(R.color.lightblack));
                walking.setBackgroundTintList(getResources().getColorStateList(R.color.lightblack));
            }
        });
        sleeping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedactivity = "Sleeping";
                typeofactivity = "Resting Activity HR";
                Toast.makeText(heartratepage.this, typeofactivity, Toast.LENGTH_SHORT).show();
                running.setBackgroundTintList(getResources().getColorStateList(R.color.lightblack));
                swimming.setBackgroundTintList(getResources().getColorStateList(R.color.lightblack));
                resting.setBackgroundTintList(getResources().getColorStateList(R.color.lightblack));
                standing.setBackgroundTintList(getResources().getColorStateList(R.color.lightblack));
                sleeping.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                walking.setBackgroundTintList(getResources().getColorStateList(R.color.lightblack));
            }
        });
        positon();
        calculateheartrate = findViewById(R.id.calculatehaertratebtn);
        calculateheartrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positon();
                if (maleisclicked == true || femaleisclicked == true) {
                    Gson gson = new Gson();
                    String json = sharedPreferences.getString("heartdata", null);
                    Type type = new TypeToken<ArrayList<heartmodel>>(){}.getType();
                    arrayList2 = gson.fromJson(json, type);
                    if (arrayList2 == null) {
                        arrayList2 = new ArrayList<>();
                    }

                    if(typeofactivity.equals(""))
                    {
                        Toast.makeText(heartratepage.this, "Please Select An Activity", Toast.LENGTH_SHORT).show();
                    }
                    else if (typeofactivity.equals("Resting Activity HR")) {
                        if (valueAtCenter >= 60 && valueAtCenter <= 100) {
                            heartrate.setText("BPM: " + String.valueOf(valueAtCenter));
                            heartratetext.setText("Normal");
                            heartrate.setTextColor(getResources().getColorStateList(R.color.lightgreen));
                            heartratetext.setTextColor(getResources().getColorStateList(R.color.lightgreen));
                        } else if (valueAtCenter>=40&&valueAtCenter < 60) {

                            heartrate.setText("BPM: " + String.valueOf(valueAtCenter));
                            heartratetext.setText("Slow");
                            heartrate.setTextColor(getResources().getColorStateList(R.color.lightblue));
                            heartratetext.setTextColor(getResources().getColorStateList(R.color.lightblue));
                        } else if(valueAtCenter>100&&valueAtCenter<=220) {
                            heartrate.setText("BPM: " + String.valueOf(valueAtCenter));
                            heartratetext.setText("Fast");
                            heartrate.setTextColor(getResources().getColorStateList(R.color.red));
                            heartratetext.setTextColor(getResources().getColorStateList(R.color.red));
                        }
                        else
                        {
                            heartrate.setText("BPM:>=40 & <=220");
                            heartratetext.setText("Not Calculated");
                            heartrate.setTextColor(getResources().getColorStateList(R.color.red));
                            heartratetext.setTextColor(getResources().getColorStateList(R.color.red));
                        }

                        heartratetype = typeofactivity.toString();
                        heartvalue = heartrate.getText().toString();
                        heartstatus = heartratetext.getText().toString();
                        String currentdate = new SimpleDateFormat("dd-MM-yy", Locale.getDefault()).format(new Date());
                        String currenttime = new SimpleDateFormat("h:mm a",Locale.getDefault()).format(new Date());
                        date = currentdate;
                        time = currenttime;
                        if(!heartstatus.equals("Not Calculated")&&!heartvalue.equals("BPM:>=40 & <=220")) {
                            heartmodel models = new heartmodel(heartratetype, heartvalue, heartstatus, date, time);
                            arrayList2.add(models);
                            String updatedJson = gson.toJson(arrayList2);
                            editor.putString("heartdata", updatedJson);
                            editor.apply();
                        }
                        else
                        {
                            Toast.makeText(heartratepage.this, "Choose Gender", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if (typeofactivity.equals("Exercise Activity HR")) {

                        if (valueAtCenter>=40&&valueAtCenter < 121) {
                            heartrate.setText("BPM: " + String.valueOf(valueAtCenter));
                            heartratetext.setText("Normal");
                            heartrate.setTextColor(getResources().getColorStateList(R.color.lightgreen));
                            heartratetext.setTextColor(getResources().getColorStateList(R.color.lightgreen));
                        }
                        else if (valueAtCenter >= 121 && valueAtCenter <= 160) {
                            heartrate.setText("BPM: " + String.valueOf(valueAtCenter));
                            heartratetext.setText("Moderate Intensity");
                            heartrate.setTextColor(getResources().getColorStateList(R.color.lightblue));
                            heartratetext.setTextColor(getResources().getColorStateList(R.color.lightblue));
                        }

                        else if (valueAtCenter >= 161 && valueAtCenter <= 180) {
                            heartrate.setText("BPM: " + String.valueOf(valueAtCenter));
                            heartratetext.setText("High Intensity");
                            heartrate.setTextColor(getResources().getColorStateList(R.color.orange));
                            heartratetext.setTextColor(getResources().getColorStateList(R.color.orange));
                        }
                        else if (valueAtCenter >= 181 && valueAtCenter<=220) {
                            heartrate.setText("BPM: " + String.valueOf(valueAtCenter));
                            heartratetext.setText("Extreme Intensity");
                            heartrate.setTextColor(getResources().getColorStateList(R.color.red));
                            heartratetext.setTextColor(getResources().getColorStateList(R.color.red));
                        }
                        else
                        {
                            heartrate.setText("BPM:>=40 & <=220");
                            heartratetext.setText("Not Calculated");
                            heartrate.setTextColor(getResources().getColorStateList(R.color.red));
                            heartratetext.setTextColor(getResources().getColorStateList(R.color.red));
                        }
                        heartratetype = typeofactivity.toString();
                        heartvalue = heartrate.getText().toString();
                        heartstatus = heartratetext.getText().toString();
                        String currentdate = new SimpleDateFormat("dd-MM-yy", Locale.getDefault()).format(new Date());
                        String currenttime = new SimpleDateFormat("h:mm a",Locale.getDefault()).format(new Date());
                        date = currentdate;
                        time = currenttime;
                        if(!heartstatus.equals("Not Calculated")&&!heartvalue.equals("BPM:>=40 & <=220")) {
                            heartmodel models = new heartmodel(heartratetype, heartvalue, heartstatus, date, time);
                            arrayList2.add(models);
                            String updatedJson = gson.toJson(arrayList2);
                            editor.putString("heartdata", updatedJson);
                            editor.apply();

                        }
                    }
                }
                else
                {
                    Toast.makeText(heartratepage.this, "Choose Gender", Toast.LENGTH_SHORT).show();
                }
            }
        });
        history1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent heartreateredirect = new Intent(heartratepage.this,Hearthistory.class);
                startActivity(heartreateredirect);
            }
        });


    }
    public void positon() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View centerView = snapHelper.findSnapView(linearLayoutManager);
                if (centerView != null) {
                    centerPosition = recyclerView.getChildAdapterPosition(centerView);
                    if(centerPosition>0&&centerPosition<arrayList.size())
                    {
                        valueAtCenter = arrayList.get(centerPosition);
                    }
                }
            }
        });
    }
    }