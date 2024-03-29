package com.example.doctormate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

public class actvitiespage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    String ftechname;
    TextView nametodisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_actvitiespage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        nametodisplay = findViewById(R.id.nametodisplay1);

        String Uid = mAuth.getUid();
        if(Uid!=null) {
            database.getReference().child("Users").child(Uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Users users = snapshot.getValue(Users.class);
                    if(users.getRole().equals("Doctor"))
                    {
                        nametodisplay.setText("Dr." + users.getFname());

                    }
                    else {
                        nametodisplay.setText(users.getFname());

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        CardView bmipage,heartratepage;
        bmipage = findViewById(R.id.bmipagebtn);
        bmipage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirect = new Intent(actvitiespage.this,bmi.class);
                startActivity(redirect);
            }
        });
        heartratepage = findViewById(R.id.heartratepagebtn);
        heartratepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent heartredirect = new Intent(actvitiespage.this,heartratepage.class);
                startActivity(heartredirect);
            }
        });
    }
}