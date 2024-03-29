package com.example.doctormate;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import static java.lang.invoke.MethodHandles.loop;

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

import com.example.doctormate.models.bmimodels;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class bmi extends AppCompatActivity {
    int agetodisplay;
    double weighttodisplay;
    String newweight;
    String newheight;
    String newheightinches;
    double heighttodisplay;
    int heightinft;
    int cmtoftconvert;
    int heightinchestodisplay;
    double onedigit;
    boolean maleisclicked = false;
    boolean femaleisclicked = false;
    int count = 0;
    ImageView ageplus, ageminus, weightplus, weightminus, heightplus, heightminus, height1plus, height1minus, history, chart, needle, goback;
    MaterialButton male, female, calculatebmi;
    EditText age, weight, heightcm, heightinches;
    TextView weightunit, heightunit, bmi, bmitext, textView2;
    double bmiroundof;
    ArrayList<bmimodels> arrayList = new ArrayList<>();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bmi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


            //Code
            sharedPreferences = getSharedPreferences("Bmihistory", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            ageplus = findViewById(R.id.ageplus);
            ageminus = findViewById(R.id.ageminus);
            weightplus = findViewById(R.id.weightplus);
            weightminus = findViewById(R.id.weightminus);
            weightunit = findViewById(R.id.weightunit);
            weight = findViewById(R.id.weightedttext);
            heightcm = findViewById(R.id.heightedttext);
            heightplus = findViewById(R.id.heightplus);
            heightminus = findViewById(R.id.heightminus);
            height1plus = findViewById(R.id.incheplus);
            height1minus = findViewById(R.id.inchesminus);
            heightinches = findViewById(R.id.heightinches);
            heightunit = findViewById(R.id.heightunit);
            male = findViewById(R.id.Male);
            female = findViewById(R.id.Female);
            chart = findViewById(R.id.chart);
            calculatebmi = findViewById(R.id.calculatebmibtn);
            bmi = findViewById(R.id.bmivalue);
            bmitext = findViewById(R.id.bmitext);
            age = findViewById(R.id.ageedttxt);
            needle = findViewById(R.id.arrow);
            textView2 = findViewById(R.id.textView2);
            goback = findViewById(R.id.gobackbmi);
            history = findViewById(R.id.bmihistory);
            history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent redirecttobmi = new Intent(bmi.this,History.class);
                    startActivity(redirecttobmi);
                }
            });
            chart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog chartdidalog;
                    chartdidalog = new Dialog(bmi.this);
                    chartdidalog.setContentView(R.layout.charttbmi);
                    chartdidalog.show();
                }
            });
            goback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent gobackintent = new Intent(bmi.this, actvitiespage.class);
                    startActivity(gobackintent);
                    finish();
                }
            });
            male.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    male.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                    female.setBackgroundTintList(getResources().getColorStateList(R.color.signupred));
                    maleisclicked = true;
                    femaleisclicked = false;
                }
            });
            female.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    female.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                    male.setBackgroundTintList(getResources().getColorStateList(R.color.signupred));
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
            weightplus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newweight = weight.getText().toString();
                    double weightconvert = Double.parseDouble(newweight);
                    weighttodisplay = weightconvert + 1.0;
                    weight.setText(String.valueOf(weighttodisplay));

                }
            });
            weightminus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newweight = weight.getText().toString();
                    double weightconvert = Double.parseDouble(newweight);
                    weighttodisplay = weightconvert - 1.0;
                    if (weighttodisplay >= 1.0) {
                        weight.setText(String.valueOf(weighttodisplay));
                    } else {
                        weight.setText(String.valueOf(1.0));
                    }

                }
            });
            weightunit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    weightuntifunc();
                    heightunitfunc();
                }
            });
            heightplus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newheight = heightcm.getText().toString();
                    double heightconvert = Double.parseDouble(newheight);
                    heighttodisplay = heightconvert + 1.0;
                    heightcm.setText(String.valueOf(heighttodisplay));
                }
            });
            heightminus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newheight = heightcm.getText().toString();
                    Double heightconvert = Double.parseDouble(newheight);
                    heighttodisplay = heightconvert - 1.0;
                    if (heighttodisplay >= 1.0) {
                        heightcm.setText(String.valueOf(String.valueOf(heighttodisplay)));
                    } else {
                        heightcm.setText(String.valueOf(1.0));
                    }

                }
            });
            heightunit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    weightuntifunc();
                    heightunitfunc();

                }

            });
            calculatebmi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Bmivalue;
                    String Bmitext;
                    String Typeoftest;
                    String Date;
                    String Time;
                    if (weightunit.getText().toString().equals("Kg") && heightunit.getText().toString().equals("Cm")) {
                        if (maleisclicked == true || femaleisclicked == true && !age.getText().toString().equals("") && !weight.getText().toString().equals("") && !heightcm.getText().toString().equals("")) {
                            Gson gson = new Gson();
                            String json = sharedPreferences.getString("data", null);
                            Type type = new TypeToken<ArrayList<bmimodels>>() {
                            }.getType();
                            arrayList = gson.fromJson(json, type);
                            if (arrayList == null) {
                                arrayList = new ArrayList<>();
                            }
                            double currentweight = Double.parseDouble(weight.getText().toString());
                            double currentheight = Double.parseDouble(heightcm.getText().toString());
                            double currentheightinm = currentheight / 100;
                            double bmiwithout = (currentweight / Math.pow(currentheightinm, 2));
                            bmiroundof = Math.round(bmiwithout * 10) / 10.0;
                            bmi.setText(String.valueOf(bmiroundof));
                       loop();
                            String currentdate = new SimpleDateFormat("dd-MM-yy", Locale.getDefault()).format(new Date());
                            String currenttime = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());
                            Bmivalue = String.valueOf(bmiroundof);
                            Bmitext = bmitext.getText().toString();
                            Typeoftest = textView2.getText().toString();
                            Date = currentdate;
                            Time = currenttime;
                            bmimodels bmimodels = new bmimodels(Typeoftest, Bmivalue, Bmitext, Date, Time);
                            arrayList.add(bmimodels);
                            String updatedJson = gson.toJson(arrayList);
                            editor.putString("data", updatedJson);
                            editor.apply();


                        } else {
                            Toast.makeText(bmi.this, "All Fields Are Mandatory", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (maleisclicked == true || femaleisclicked == true && !age.getText().toString().equals("") && !weight.getText().toString().equals("") && !heightcm.getText().toString().equals("")) {
                            double currentweight = Double.parseDouble(weight.getText().toString());
                            double currentheightft = Double.parseDouble(heightcm.getText().toString());
                            int currentheightinches = Integer.parseInt(heightinches.getText().toString());
                            double newtotal = (currentheightft * 12) + currentheightinches;
                            double bmiwithout = (currentweight / Math.pow(newtotal, 2)) * 703;
                            bmiroundof = Math.round(bmiwithout * 10) / 10.0;
                            bmi.setText(String.valueOf(bmiroundof));
                            loop();
                            Bmivalue = String.valueOf(bmiroundof);
                            Bmitext = bmitext.getText().toString();
                            Typeoftest = textView2.getText().toString();
                            String currentdate = new SimpleDateFormat("dd-MM-yy", Locale.getDefault()).format(new Date());
                            String currenttime = new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date());
                            Date = currentdate;
                            Time = currenttime;
                            Gson gson = new Gson();
                            bmimodels bmimodels = new bmimodels(Typeoftest, Bmivalue, Bmitext, Date, Time);
                            arrayList.add(bmimodels);
                            String json = gson.toJson(arrayList);
                            editor.putString("data", json);
                            editor.commit();
                        } else {
                            Toast.makeText(bmi.this, "All Fields Are Mandatory", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            });

        }
    private void setEditTextsEnabled(boolean enabled) {
        age.setEnabled(enabled);
        weight.setEnabled(enabled);
        heightcm.setEnabled(enabled);
        heightinches.setEnabled(enabled);
    }
    public void weightuntifunc()
    {
        if(weightunit.getText().toString().equals("Kg")) {
            weightunit.setText("lb");
            weighttodisplay = Double.parseDouble((weight.getText().toString())) * 2.2;
            onedigit = Math.round(weighttodisplay * 10) / 10.0;
            weight.setText(String.valueOf(onedigit));
        }
        else {
            weightunit.setText("Kg");
            weighttodisplay = Double.parseDouble((weight.getText().toString())) / 2.2;
            onedigit = Math.round(weighttodisplay * 10) / 10.0;
            weight.setText(String.valueOf(onedigit));
        }
    }
    public void heightunitfunc()
    {
        if(heightunit.getText().toString().equals("Cm")) {
            heightunit.setText("Ft");
            double beforeconvert = Double.parseDouble(heightcm.getText().toString());
            heightinft = (int) (Math.floor(beforeconvert));
            double heightinchesmultiply = heightinft * 0.393701;
            double heightinftnew = heightinchesmultiply / 12;
            int newheightinft = (int) (Math.floor(heightinftnew));
            double remaing = heightinftnew - newheightinft;
            remaing = remaing * 12;
            heightcm.setText(String.valueOf(newheightinft));
            heightcm.setHint("Ft");
            heightinches.setVisibility(View.VISIBLE);
            heightinches.setHint("Inches");
            heightinches.setText(String.valueOf(Math.round(remaing)));
            height1plus.setVisibility(View.VISIBLE);
            height1minus.setVisibility(View.VISIBLE);
            height1plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newheightinches = heightinches.getText().toString();
                    int newheightconvert = Integer.parseInt(newheightinches);
                    heightinchestodisplay = newheightconvert + 1;
                    heightinches.setText(String.valueOf(heightinchestodisplay));

                }
            });
            height1minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newheightinches = heightinches.getText().toString();
                    int newheightconvert = Integer.parseInt(newheightinches);
                    heightinchestodisplay = newheightconvert - 1;
                    if (heightinchestodisplay >= 0) {
                        heightinches.setText(String.valueOf(heightinchestodisplay));
                    } else {
                        heightinches.setText(String.valueOf(0));
                    }


                }
            });
        }
        else
        {
            heightunit.setText("Cm");
            double heightincm = Double.parseDouble(heightcm.getText().toString());
            int heightininches = Integer.parseInt(heightinches.getText().toString());
            heighttodisplay = ((heightincm*12)+heightininches)*2.54;
            heighttodisplay = Math.round(heighttodisplay*10)/10.0;
            heightcm.setText(String.valueOf(heighttodisplay));
            heightcm.setHint("Cm");
            heightinches.setVisibility(View.INVISIBLE);
            height1plus.setVisibility(View.INVISIBLE);
            height1minus.setVisibility(View.INVISIBLE);

        }
    }
    public void loop()
    {
        if (bmiroundof <= 15.9) {
            bmi.setTextColor(getResources().getColorStateList(R.color.lightblue));
            bmitext.setText("Very Severly Underweight");
            bmitext.setTextColor(getResources().getColorStateList(R.color.lightblue));
            Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
            needle.setRotation(253);
            needle.startAnimation(rotate);
        }
        else if (bmiroundof >= 16 && bmiroundof <= 16.9) {
            bmi.setTextColor(getResources().getColorStateList(R.color.lightblue));
            bmitext.setText("=");
            bmitext.setTextColor(getResources().getColorStateList(R.color.lightblue));
            if(bmiroundof==16)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(253);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof<=16.9)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(259);
                needle.startAnimation(rotate);
            }

        }
        else if (bmiroundof >= 17 && bmiroundof <= 18.4) {
            bmi.setTextColor(getResources().getColorStateList(R.color.lightblue));
            bmitext.setText("Underweight");
            bmitext.setTextColor(getResources().getColorStateList(R.color.lightblue));
            if(bmiroundof==17)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(261);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=17&&bmiroundof<17.5)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(270);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=17.5&&bmiroundof<18)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(280);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=18&&bmiroundof<18.2)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(286);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=18.2&&bmiroundof<18.4)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(293);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof == 18.4)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(297);
                needle.startAnimation(rotate);
            }
        }
        else if (bmiroundof >= 18.5 && bmiroundof < 25) {
            bmi.setTextColor(getResources().getColorStateList(R.color.lightgreen));
            bmitext.setText("Healthy Weight");
            bmitext.setTextColor(getResources().getColorStateList(R.color.lightgreen));
            if(bmiroundof>=18.5&&bmiroundof<19)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(301);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>19&&bmiroundof<20)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(310);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>20&&bmiroundof<20.5)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(320);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=20.5&&bmiroundof<21)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(325);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=21&&bmiroundof<21.5)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(330);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=21.5&&bmiroundof<22)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(335);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=22&&bmiroundof<22.5)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(340);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=22.5&&bmiroundof<23)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(345);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=23&&bmiroundof<23.5)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(360);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=23.5&&bmiroundof<23.7)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(370);
                needle.startAnimation(rotate);
            } else if(bmiroundof>=23.7&&bmiroundof<24)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(375);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=24&&bmiroundof<24.5)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(385);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=24.5&&bmiroundof<24.7)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(397);
                needle.startAnimation(rotate);
            } else if(bmiroundof>=24.7&&bmiroundof<24.8)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(410);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=24.8&&bmiroundof<24.9)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(415);
                needle.startAnimation(rotate);
            }

        }
        else if (bmiroundof >= 25 && bmiroundof < 30) {
            bmi.setTextColor(getResources().getColorStateList(R.color.red));
            bmitext.setText("Overweight");
            bmitext.setTextColor(getResources().getColorStateList(R.color.red));
            if(bmiroundof>=25&&bmiroundof<25.2)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(415);
                needle.startAnimation(rotate);
            } else if(bmiroundof>=25.2&&bmiroundof<25.5)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(417);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=25.5&&bmiroundof<25.7)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(419);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=25.7&&bmiroundof<26)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(421);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=26&&bmiroundof<26.5)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(423);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=26.5&&bmiroundof<26.7)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(425);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=26.7&&bmiroundof<27)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(429);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=27&&bmiroundof<27.5)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(431);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=27.5&&bmiroundof<27.7)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(433);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=27.7&&bmiroundof<28)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(435);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=28&&bmiroundof<28.3)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(438);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=28.3&&bmiroundof<28.6)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(441);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=28.6&&bmiroundof<29)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(443);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=29&&bmiroundof<29.5)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(446);
                needle.startAnimation(rotate);
            }else if(bmiroundof>=29.5&&bmiroundof<29.7)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(447);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=29.7&&bmiroundof<29.9)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(449);
                needle.startAnimation(rotate);
            }
        }
        else if (bmiroundof >= 30 && bmiroundof < 35) {
            bmi.setTextColor(getResources().getColorStateList(R.color.red));
            bmitext.setText("Obese Class 1");
            bmitext.setTextColor(getResources().getColorStateList(R.color.red));
            if(bmiroundof>=30&&bmiroundof<30.2)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(450);
                needle.startAnimation(rotate);
            }else if(bmiroundof>=30.2&&bmiroundof<30.5)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(451);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=30.5&&bmiroundof<31)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(452);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=31&&bmiroundof<31.4)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(453);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=31.4&&bmiroundof<31.9)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(454);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=32&&bmiroundof<32.5)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(455);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=32.5&&bmiroundof<33)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(456);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=33&&bmiroundof<34)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(457);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=34&&bmiroundof<34.9)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(458);
                needle.startAnimation(rotate);
            }

        }
        else if (bmiroundof >= 35 && bmiroundof < 40) {
            bmi.setTextColor(getResources().getColorStateList(R.color.red));
            bmitext.setText("Obese Class 2");
            bmitext.setTextColor(getResources().getColorStateList(R.color.red));
            if(bmiroundof>=35&&bmiroundof<35.5)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(460);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=35.5&&bmiroundof<36)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(461);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=36&&bmiroundof<36.7)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(462);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=36.7&&bmiroundof<38)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(463);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=38&&bmiroundof<38.5)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(464);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=38.5&&bmiroundof<39)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(465);
                needle.startAnimation(rotate);
            }
            else if(bmiroundof>=39&&bmiroundof<39.9)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(466);
                needle.startAnimation(rotate);
            }
        }
        else if (bmiroundof >= 40) {
            bmi.setTextColor(getResources().getColorStateList(R.color.red));
            bmitext.setText("Obese Class 3");
            bmitext.setTextColor(getResources().getColorStateList(R.color.red));
            if(bmiroundof>=40)
            {
                Animation rotate = AnimationUtils.loadAnimation(bmi.this,R.anim.rotate);
                needle.setRotation(468);
                needle.startAnimation(rotate);
            }
        }
    }

    }