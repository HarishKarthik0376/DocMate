package com.example.doctormate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.doctormate.models.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    String ftechname;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setTitle("Please Wait");
        dialog.setMessage("Loading");
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        },1500);
        TextView nametodisplay,contactdoctor;
        ImageView imageviewdoctor,profileicon;
        nametodisplay = findViewById(R.id.nametodisplay);
        contactdoctor = findViewById(R.id.contactdoctor);
        imageviewdoctor = findViewById(R.id.imageviewdoctor);
        String Uid = mAuth.getUid();
        if(Uid!=null) {
            database.getReference().child("Users").child(Uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Users users = snapshot.getValue(Users.class);
                    if(users.getRole().equals("Doctor"))
                    {
                        nametodisplay.setText("Dr." + users.getFname());
                        contactdoctor.setText("Contact Patient");
                        imageviewdoctor.setImageResource(R.drawable.user);

                    }
                    else {
                        nametodisplay.setText(users.getFname());
                        contactdoctor.setText("Contact Doctor");
                        imageviewdoctor.setImageResource(R.drawable.doctor);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        profileicon = findViewById(R.id.profileicon);
        profileicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent red = new Intent(MainActivity.this,profilepage.class);
                startActivity(red);
            }
        });
        CardView cardView,contactdoctorcard;
        cardView = findViewById(R.id.healthttrack);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent healthactivity = new Intent(MainActivity.this,actvitiespage.class);
                startActivity(healthactivity);
            }
        });
        contactdoctorcard = findViewById(R.id.doctorcardview);
        contactdoctorcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirecttodoc = new Intent(MainActivity.this, contactdoctor.class);
                startActivity(redirecttodoc);
            }
        });
        }
    }