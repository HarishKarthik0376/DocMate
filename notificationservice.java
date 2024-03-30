package com.example.doctormate;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.doctormate.models.remindermodel;

import java.util.ArrayList;

public class notificationservice extends BroadcastReceiver {
    private static final String CHANNEL_ID = "testingid";
    private static final int notificationid = 81;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String newtype;
    String description;
    String reminderId;
    ArrayList<remindermodel> arrayList;

    @Override
    public void onReceive(Context context, Intent intent) {
        sharedPreferences = context.getSharedPreferences("reminderhistory", context.MODE_PRIVATE);
        String reminderId;
        reminderId = intent.getStringExtra("keyactual");
        String messageTypeKey = reminderId + "_messageTypeKey";
        String messageDescriptionKey = reminderId + "_messageDescriptionKey";
        newtype = sharedPreferences.getString(messageTypeKey, null);
        description = sharedPreferences.getString(messageDescriptionKey, null);
        if (newtype != null && description != null) {
            remindermodel remindermodel = new remindermodel();
            remindermodel.setType(remindermodel.getType());
            remindermodel.setDescription(remindermodel.getDescription());
            Intent newintent = new Intent(context, MainActivity.class);
            newintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent onnotificationclick = PendingIntent.getActivity(context, 0, newintent, PendingIntent.FLAG_IMMUTABLE);
            Drawable drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.clock, null);
            if (drawable != null) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                Bitmap largeIcon = bitmapDrawable.getBitmap();

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setLargeIcon(largeIcon)
                        .setSmallIcon(R.drawable.bmiicon)
                        .setContentText(description)
                        .setSubText(newtype)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);
                builder.setContentIntent(onnotificationclick);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                notificationManager.notify(notificationid, builder.build());

            }

        }
        else
        {
            Toast.makeText(context, "error!!", Toast.LENGTH_SHORT).show();
        }
    }
}
