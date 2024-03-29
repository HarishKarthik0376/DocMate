package com.example.doctormate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.doctormate.models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Signup extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    String role;
    String randomid;
    String finalid;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button signup,userbtn,doctorbtn;
        signup = findViewById(R.id.signupbtn);
        userbtn = findViewById(R.id.userbtn);
        doctorbtn = findViewById(R.id.doctorbtn);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        EditText fname,lname,emailid,mobile,password;
        TextView alreadyhaveanaccount;

        fname = findViewById(R.id.signupfname);
        lname = findViewById(R.id.signuplname);
        emailid = findViewById(R.id.signupemail);
        mobile = findViewById(R.id.signupnumber);
        password = findViewById(R.id.signuppass);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Validating");
        progressDialog.setMessage("Please Wait");
        alreadyhaveanaccount = findViewById(R.id.alreadyhaveanaccount);
        alreadyhaveanaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup.this,login.class);
                startActivity(intent);
                finish();
            }
        });
        userbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                role = "User";
                userbtn.setBackgroundTintList(getResources().getColorStateList(R.color.signupred));
                userbtn.setTextColor(getResources().getColorStateList(R.color.white));
                doctorbtn.setTextColor(getResources().getColorStateList(R.color.signupred));
                doctorbtn.setBackgroundTintList(getResources().getColorStateList(R.color.white));
            }
        });
        doctorbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                role = "Doctor";
                doctorbtn.setBackgroundTintList(getResources().getColorStateList(R.color.signupred));
                doctorbtn.setTextColor(getResources().getColorStateList(R.color.white));
                userbtn.setTextColor(getResources().getColorStateList(R.color.signupred));
                userbtn.setBackgroundTintList(getResources().getColorStateList(R.color.white));
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fname.getText().toString()!=null&&lname.getText().toString()!=null&&emailid.getText().toString()!=null&&mobile!=null&&password!=null&&role!=null)
                {      UUID randomuid =UUID.randomUUID();
                    randomid = randomuid.toString();
                    finalid = "Doc" +  randomid.substring(0,4);
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(emailid.getText().toString(),password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Users users = new Users(fname.getText().toString(), lname.getText().toString(), emailid.getText().toString(), mobile.getText().toString(), password.getText().toString(), role, finalid);
                                        String Uid = task.getResult().getUser().getUid();
                                        database.getReference().child("Users").child(Uid).setValue(users);
                                       new Handler().postDelayed(new Runnable() {
                                           @Override
                                           public void run() {
                                               Toast.makeText(Signup.this, "Success", Toast.LENGTH_SHORT).show();
                                               progressDialog.dismiss();
                                               Intent redirect = new Intent(Signup.this, MainActivity.class);
                                               startActivity(redirect);
                                               finish();
                                           }
                                       },3000);

                                    }
                                    else
                                    { new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(Signup.this, "Error", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    },3000);
                                    }
                                }
                            });

                }
                else
                {
                    Toast.makeText(Signup.this, "All Fields Are Mandatory!!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        if(mAuth.getCurrentUser()!=null)
        {
            Intent exists1 = new Intent(Signup.this,MainActivity.class);
            startActivity(exists1);
            finish();
        }
    }
}