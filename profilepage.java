package com.example.doctormate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.doctormate.models.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profilepage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    TextView nametodis, idtodis;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profilepage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        nametodis = findViewById(R.id.nametofetchdis);
        idtodis = findViewById(R.id.idtofetchdis);
        logout = findViewById(R.id.logout);
        String Uid = mAuth.getUid();
        if (Uid != null) {
            database.getReference().child("Users").child(Uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Users users = snapshot.getValue(Users.class);
                    String name = snapshot.child("fname").getValue(String.class);
                    String idto = snapshot.child("ranuid").getValue(String.class);
                    nametodis.setText(name);
                    idtodis.setText(idto);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent loged = new Intent(profilepage.this,login.class);
                startActivity(loged);
                finish();
            }
        });
    }
}