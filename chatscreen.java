package com.example.doctormate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctormate.adapters.messageadapter;
import com.example.doctormate.models.message;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class chatscreen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chatscreen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        final String senderid = mAuth.getUid();
        ImageView goback;
        goback = findViewById(R.id.gobackhome);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gohome = new Intent(chatscreen.this,contactdoctor.class);
                startActivity(gohome);
                finish();
            }
        });
        String recvid = getIntent().getStringExtra("UserId");
        String username = getIntent().getStringExtra("UserName");
        TextView chatusername;
        chatusername = findViewById(R.id.chatusername);
        chatusername.setText(username);
        recyclerView = findViewById(R.id.chatscreenrecyclerview);
        final ArrayList<message> arrmsg = new ArrayList<>();
        final String senderroom = senderid+recvid;
        final String recvroom = recvid+senderid;
        final messageadapter msgadapter = new messageadapter(arrmsg,this,recvid);
        recyclerView.setAdapter(msgadapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.scrollToPosition(arrmsg.size()-1);
        database.getReference().child("chats")
                .child(senderroom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        arrmsg.clear();
                        for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                            message msg = snapshot1.getValue(message.class);
                            msg.setMessageid(snapshot1.getKey());
                            msgadapter.notifyDataSetChanged();
                            arrmsg.add(msg);
                        }
                        msgadapter.notifyDataSetChanged();
                        recyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.scrollToPosition(arrmsg.size() - 1);
                            }
                        }, 100);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        ImageView msggo;
        EditText msgvalue = findViewById(R.id.msgvalue);
        msggo = findViewById(R.id.msggo);
        msggo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msggo.setEnabled(false);

                String actualmsg = msgvalue.getText().toString();
                if (actualmsg.equals("")) {
                    msgvalue.setText("");
                } else {
                    final message message = new message(actualmsg, senderid);
                    message.setTimestamp(new Date().getTime());
                    msgvalue.setText("");

                    database.getReference().child("chats")
                            .child(senderroom)
                            .push()
                            .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    database.getReference().child("chats")
                                            .child(recvroom)
                                            .push()
                                            .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    msggo.setEnabled(true);
                                                }
                                            });
                                }
                            });

                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.smoothScrollToPosition(arrmsg.size() - 1);
                        }
                    }, 1000);
                }
            }
        });
    }
}