package com.example.doctormate.adapters;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctormate.R;
import com.example.doctormate.models.stringmodel;
import com.example.doctormate.notificationservice;
import com.example.doctormate.todoreminder;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.gson.Gson;
import com.example.doctormate.models.remindermodel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class reminderadapter extends RecyclerView.Adapter<reminderadapter.ViewHolder> {
    private static final int RC_NOTIFICATION = 81;

    public reminderadapter(Context context, ArrayList<remindermodel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    Context context;
    ArrayList<remindermodel> arrayList;
    int finalevalue;
    SharedPreferences sharedPreferences;
    SharedPreferences newshared;
    SharedPreferences.Editor neweditor;
    int sendpos;
    int adppos;
    String typediff = "";
    String descriptionupdate = "";
    String timeupdate = "";
    String typeofreminder = "";
    String contenttaskdes = "";
    String timeupdatenew;
    SharedPreferences.Editor editor;
    int drinkwatercount = 0;
    int exercisecount = 0;
    int wakeupcount = 0;
    Calendar calendar = Calendar.getInstance();
    int posget;
    NotificationManager notificationManager;
    private static final String CHANNEL_ID = "testingid";
    private static final int notificationid = 81;
    int currenthr;
    int currentmin;
    String randomUUIDString;
    long millistime;
    Intent mewintent;
    boolean isReminderEnabled;
    remindermodel remindermodel;
    String typetobesent;
    String destobesent;
    ArrayList<stringmodel> stringarray;

    int newreqcode;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reminderlayoutttobeset, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        notificationchanel(context);
        sharedPreferences = context.getSharedPreferences("reminderhistory", Context.MODE_PRIVATE);
        newshared = context.getSharedPreferences("ischeck", Context.MODE_PRIVATE);
        neweditor = newshared.edit();
        editor = sharedPreferences.edit();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        editor = sharedPreferences.edit();
        newreqcode = 0;
        remindermodel remindermodel = arrayList.get(position);
        remindermodel.getUuid();
        int adapter = holder.getAdapterPosition();
        holder.type.setText(remindermodel.getType());
        holder.time.setText(remindermodel.getTime());
        isReminderEnabled = newshared.getBoolean("reminder_" + remindermodel.getUuid() + "_enabled", false);
        holder.materialSwitch.setChecked(isReminderEnabled);
        holder.materialSwitch.setThumbTintList(ContextCompat.getColorStateList(context, isReminderEnabled ? R.color.lightgreen : R.color.red));
        holder.materialSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    if (holder.materialSwitch.isChecked()) {
                        String gettime = remindermodel.getTime();
                        String gettimeinhrs = gettime.substring(0, 2);
                        String gettimeinmins = gettime.substring(3, 5);
                        if (newreqcode == 0) {
                        } else {
                            newreqcode = newshared.getInt("reqcode" + remindermodel.getUuid(), 0);
                        }
                        currenthr = Integer.parseInt(gettimeinhrs);
                        currentmin = Integer.parseInt(gettimeinmins);
                        calendar.set(Calendar.HOUR_OF_DAY, currenthr);
                        calendar.set(Calendar.MINUTE, currentmin);
                        if (System.currentTimeMillis() > calendar.getTimeInMillis()) {
                            calendar.add(Calendar.DAY_OF_MONTH, 1);
                        }
                        millistime = calendar.getTimeInMillis();
                        remindermodel remindermodel = arrayList.get(adapterPosition);
                        mewintent = new Intent(context, notificationservice.class);
                        mewintent.removeExtra("keyactual");
                        typetobesent = remindermodel.getType();
                        destobesent = remindermodel.getDescription();
                        randomUUIDString = remindermodel.getUuid();
                        editor.putString(randomUUIDString + "_messageTypeKey", typetobesent);
                        editor.putString(randomUUIDString + "_messageDescriptionKey", destobesent);
                        mewintent.putExtra("keyactual", randomUUIDString);
                        editor.commit();
                        holder.materialSwitch.setThumbTintList(context.getResources().getColorStateList(R.color.lightgreen));
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, newreqcode, mewintent, PendingIntent.FLAG_IMMUTABLE);
                        holder.materialSwitch.setThumbIconTintList(context.getResources().getColorStateList(R.color.lightgreen));
                        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, millistime, 1000 * 60 * 60 * 24, pendingIntent);
                        neweditor.putBoolean("reminder_" + remindermodel.getUuid() + "_enabled", true);
                        neweditor.putInt("reqcode" + remindermodel.getUuid(), finalevalue);
                        neweditor.apply();
                    } else {
                        holder.materialSwitch.setThumbTintList(context.getResources().getColorStateList(R.color.red));
                        remindermodel off = arrayList.get(adapterPosition);
                        newreqcode = newshared.getInt("reqcode" + off.getUuid(), 0);
                        canclenotifs(context, newreqcode);
                        neweditor.putBoolean("reminder_" + off.getUuid() + "_enabled", false);
                        neweditor.apply();
                    }
                }
            }
        });
        holder.ddelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adppos = holder.getAdapterPosition();
                if (adppos != RecyclerView.NO_POSITION) {
                    remindermodel deleteitem = arrayList.get(adppos);
                    arrayList.remove(adppos);
                    newreqcode = newshared.getInt("reqcode" + deleteitem.getUuid(), 0);
                    neweditor.remove("reminder_" + deleteitem.getUuid() + "_enabled");
                    neweditor.remove("reqcode" + deleteitem.getUuid());
                    neweditor.apply();
                    notifyItemRemoved(adppos);
                    ((todoreminder) context).removedata(deleteitem);
                    canclenotifs(context, newreqcode);
                    editor = sharedPreferences.edit();
                    editor.remove(randomUUIDString + "_actualkey");
                    editor.apply();


                }
            }
        });
        holder.editopts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remindermodel remindermodel = arrayList.get(position);
                EditText description;
                TextView timetext, addatasktext;
                MaterialButton drinkwater, exercise, wakeup, addremindder;
                Dialog dialog;
                dialog = new Dialog(context);
                dialog.setContentView(R.layout.addtask);
                drinkwater = dialog.findViewById(R.id.drinkwater);
                exercise = dialog.findViewById(R.id.exercise);
                wakeup = dialog.findViewById(R.id.wakeup);
                addremindder = dialog.findViewById(R.id.submittask);
                timetext = dialog.findViewById(R.id.selecttime);
                description = dialog.findViewById(R.id.taskdescription);
                addatasktext = dialog.findViewById(R.id.addatasktext);
                description.setText(remindermodel.getDescription());
                addremindder.setText("Update Reminder");
                addatasktext.setText("Update Reminder");
                timetext.setText(remindermodel.getTime());
                dialog.show();
                drinkwater.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (drinkwatercount == 0) {
                            typeofreminder = "Drink Water";
                            drinkwater.setBackgroundTintList(context.getResources().getColorStateList(R.color.white));
                            drinkwater.setTextColor(context.getResources().getColorStateList(R.color.black));
                            drinkwater.setStrokeColor(context.getResources().getColorStateList(R.color.lightgreen));
                            exercise.setBackgroundTintList(context.getResources().getColorStateList(R.color.black));
                            exercise.setTextColor(context.getResources().getColorStateList(R.color.white));
                            exercise.setStrokeColor(context.getResources().getColorStateList(R.color.red));
                            wakeup.setBackgroundTintList(context.getResources().getColorStateList(R.color.black));
                            wakeup.setTextColor(context.getResources().getColorStateList(R.color.white));
                            wakeup.setStrokeColor(context.getResources().getColorStateList(R.color.orange));
                            drinkwatercount = 1;
                            exercisecount = 0;
                            wakeupcount = 0;
                            description.setText("Time To Drink Water Buddy!!");

                        }
                    }
                });
                exercise.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (exercisecount == 0) {
                            typeofreminder = "Exercise Reminder";
                            exercise.setBackgroundTintList(context.getResources().getColorStateList(R.color.white));
                            exercise.setTextColor(context.getResources().getColorStateList(R.color.black));
                            exercise.setStrokeColor(context.getResources().getColorStateList(R.color.lightgreen));
                            drinkwater.setBackgroundTintList(context.getResources().getColorStateList(R.color.black));
                            drinkwater.setTextColor(context.getResources().getColorStateList(R.color.white));
                            drinkwater.setStrokeColor(context.getResources().getColorStateList(R.color.lightblue));
                            wakeup.setBackgroundTintList(context.getResources().getColorStateList(R.color.black));
                            wakeup.setTextColor(context.getResources().getColorStateList(R.color.white));
                            wakeup.setStrokeColor(context.getResources().getColorStateList(R.color.orange));
                            exercisecount = 1;
                            drinkwatercount = 0;
                            wakeupcount = 0;
                            description.setText("Its Time To Hit The Gym Buddy!!");

                        }
                    }
                });
                wakeup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (wakeupcount == 0) {
                            typeofreminder = "Sleep Reminder";
                            wakeup.setBackgroundTintList(context.getResources().getColorStateList(R.color.white));
                            wakeup.setTextColor(context.getResources().getColorStateList(R.color.black));
                            wakeup.setStrokeColor(context.getResources().getColorStateList(R.color.lightgreen));
                            exercise.setBackgroundTintList(context.getResources().getColorStateList(R.color.black));
                            exercise.setTextColor(context.getResources().getColorStateList(R.color.white));
                            exercise.setStrokeColor(context.getResources().getColorStateList(R.color.red));
                            drinkwater.setBackgroundTintList(context.getResources().getColorStateList(R.color.black));
                            drinkwater.setTextColor(context.getResources().getColorStateList(R.color.white));
                            drinkwater.setStrokeColor(context.getResources().getColorStateList(R.color.lightblue));
                            wakeupcount = 1;
                            drinkwatercount = 0;
                            exercisecount = 0;
                            description.setText("Time To Sleep Buddy!!");

                        }
                    }
                });
                timetext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currenthr = calendar.get(Calendar.HOUR_OF_DAY);
                        currentmin = calendar.get(Calendar.MINUTE);
                        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String modifiedtime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                                timeupdatenew = modifiedtime;
                                timetext.setText(modifiedtime);
                            }
                        }, currenthr, currentmin, false);
                        timePickerDialog.setTitle("Choose Time");
                        timePickerDialog.show();
                    }
                });
                addremindder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!typeofreminder.equals("") && !timetext.getText().toString().equals("") && !description.getText().toString().equals("")) {
                            Gson gson = new Gson();
                            adppos = holder.getAdapterPosition();
                            canclenotifs(context, newreqcode);
                            remindermodel newremindermodel = arrayList.get(adppos);
                            randomUUIDString = newremindermodel.getUuid();
                            contenttaskdes = description.getText().toString();
                            typediff = typeofreminder;
                            timeupdate = timetext.getText().toString();
                            description.setText(contenttaskdes);
                            holder.type.setText(typeofreminder);
                            holder.time.setText(timeupdate);
                            remindermodel remindermodel = arrayList.get(adppos);
                            remindermodel.setType(typediff);
                            remindermodel.setDescription(contenttaskdes);
                            remindermodel.setTime(timeupdate);
                            arrayList.set(adppos, new remindermodel(typediff, contenttaskdes, timeupdate, randomUUIDString));
                            sharedPreferences = context.getSharedPreferences("reminderhistory", Context.MODE_PRIVATE);
                            String updatedJson = gson.toJson(arrayList);
                            editor.putString("reminderdata", updatedJson);
                            editor.apply();
                            holder.materialSwitch.setChecked(false);
                            editor = sharedPreferences.edit();
                            editor.putBoolean("reminder_" + remindermodel.getUuid() + "_enabled", false);
                            holder.materialSwitch.setThumbTintList(context.getResources().getColorStateList(R.color.red));
                            editor.apply();
                            dialog.dismiss();


                        } else if (typeofreminder.equals("") || timetext.getText().toString().equals("") || description.getText().toString().equals("")) {
                            Toast.makeText(context, "All Fields Are Mandatory", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "All Fields Are Mandatory", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });


            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView type, time;
        ImageView ddelete, editopts;
        MaterialSwitch materialSwitch;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.remndertype);
            time = itemView.findViewById(R.id.remindersettime);
            materialSwitch = itemView.findViewById(R.id.switchbtn);
            ddelete = itemView.findViewById(R.id.deletereminder);
            editopts = itemView.findViewById(R.id.options);

        }
    }

    private void notificationchanel(Context context) {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Reminder", NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("Checkchannel");
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }

    private void canclenotifs(Context context, int reqcode) {
        Intent intent = new Intent(context, notificationservice.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, reqcode, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        notificationManager.cancel(newreqcode);
    }

    private int uniqyereqcode(String uuid) {
        finalevalue = uuid.hashCode();
        return finalevalue;
    }
}

