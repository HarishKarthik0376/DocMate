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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        Button loginbtn;
        EditText emailid,mobile,password;
        TextView newaccount;
        emailid = findViewById(R.id.loginemail);
        mobile = findViewById(R.id.loginnumber);
        password = findViewById(R.id.loginpass);
        newaccount = findViewById(R.id.logintoaccount);
        loginbtn = findViewById(R.id.loginbtn);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Validating");
        dialog.setMessage("Please Wait");
        newaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirect = new Intent(login.this, Signup.class);
                startActivity(redirect);
                finish();
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailid.getText().toString()!=null&&mobile.getText().toString()!=null&&password.getText().toString()!=null)
                {   dialog.show();
                    mAuth.signInWithEmailAndPassword(emailid.getText().toString(),password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful())
                                    {

                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                dialog.dismiss();
                                                Toast.makeText(login.this, "Login Successful!!", Toast.LENGTH_SHORT).show();
                                                Intent mainpage1 = new Intent(login.this,MainActivity.class);
                                                startActivity(mainpage1);
                                                finish();
                                            }
                                        },4000);
                                    }
                                    else
                                    {   new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            dialog.dismiss();
                                            Toast.makeText(login.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    },3000);

                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(login.this, "All Fields Are Required!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if(mAuth.getCurrentUser()!=null)
        {
            Intent exists1 = new Intent(login.this,MainActivity.class);
            startActivity(exists1);
            finish();
        }
                            }
                }
