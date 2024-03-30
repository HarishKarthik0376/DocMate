package com.example.doctormate;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctormate.adapters.contactadapter;
import com.example.doctormate.models.bmimodels;
import com.example.doctormate.models.contactmodel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class contactdoctor extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    Dialog dialog;
    ImageView goback, add;
    RecyclerView recyclerView;
    TextView nodata;
    contactadapter contactadapter;
    String refid;
    ArrayList<contactmodel> arrayList = new ArrayList<>();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String uidofdoc;
    String roleofdoc;
    String fnameofdoc;
    String lnamedoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contactdoctor);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        goback = findViewById(R.id.gobackcontact);
        add = findViewById(R.id.addbtn);
        dialog = new Dialog(contactdoctor.this);
        dialog.setContentView(R.layout.dialotopopcmt);
        nodata = findViewById(R.id.nodata);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        MaterialButton addbtndialog;
        EditText entercode;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(contactdoctor.this);
        addbtndialog = dialog.findViewById(R.id.addperson);
        entercode = dialog.findViewById(R.id.entercode);
        recyclerView = findViewById(R.id.doctorrelative);
        sharedPreferences = getSharedPreferences("chatsdis", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Gson gson = new Gson();
        loadData();

        addbtndialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Uid = mAuth.getUid();
                if (Uid != null) {
                    DatabaseReference usersRef = database.getReference().child("Users");
                    String keyenterd = entercode.getText().toString();
                    usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean found = false;
                            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                refid = userSnapshot.child("ranuid").getValue(String.class);
                                if (keyenterd.equals(refid)) {
                                    uidofdoc = userSnapshot.getKey();
                                    roleofdoc = userSnapshot.child("role").getValue(String.class);
                                    fnameofdoc = userSnapshot.child("fname").getValue(String.class);
                                    lnamedoc = userSnapshot.child("lname").getValue(String.class);
                                    found = true;
                                    break;
                                }
                            }
                            if (found) {
                                contactmodel contactmodel1 = new contactmodel(roleofdoc, uidofdoc, fnameofdoc, lnamedoc, refid);
                                arrayList.add(contactmodel1);
                                String updatedJson = gson.toJson(arrayList);
                                editor.putString("chatdata", updatedJson);
                                editor.apply();
                                dialog.dismiss();
                                contactadapter = new contactadapter(arrayList, contactdoctor.this);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                recyclerView.setAdapter(contactadapter);
                                recyclerView.scrollToPosition(arrayList.size() - 1);
                            } else {
                                Toast.makeText(contactdoctor.this, "Invalid Code", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
        });
        contactadapter = new contactadapter(arrayList, contactdoctor.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        loadData();
        recyclerView.setAdapter(contactadapter);

    }

    public void removedata(contactmodel item) {
        if (item != null) {
            arrayList.remove(item);
            Gson gson = new Gson();
            String json = gson.toJson(arrayList);
            editor.putString("chatdata", json);
            editor.apply();
            contactadapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Empty!!", Toast.LENGTH_SHORT).show();
        }
        if (arrayList.size() == 0) {
            nodata.setText("");
        }
        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference().child("chats").child(FirebaseAuth.getInstance().getUid() + uidofdoc);
        chatRef.removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(contactdoctor.this, "Chat deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(contactdoctor.this, "Failed to delete chat: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void loadData() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("chatdata", null);
        Type type = new TypeToken<ArrayList<contactmodel>>() {
        }.getType();
        arrayList = gson.fromJson(json, type);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            if (arrayList.size() == 0) {
                nodata.setText("");
            } else {
                nodata.setText("");
            }

        }
        if (arrayList.size() == 0) {
            nodata.setText("");
        } else {
            nodata.setText("");
        }
        DatabaseReference chatDataRef = FirebaseDatabase.getInstance().getReference().child("chats").child(mAuth.getUid()+uidofdoc);
        chatDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot chatSnapshot : snapshot.getChildren()) {
                    contactmodel contact = chatSnapshot.getValue(contactmodel.class);
                    arrayList.add(contact);
                }
                contactadapter.notifyDataSetChanged();
                if (arrayList.isEmpty()) {
                    nodata.setText("");
                } else {
                    nodata.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
