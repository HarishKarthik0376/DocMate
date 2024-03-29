package com.example.doctormate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctormate.adapters.hearthistoryadapter;
import com.example.doctormate.models.heartmodel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Hearthistory extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<heartmodel> heartmodelArrayList;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView nodata;
    ImageView goback;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hearthistory);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sharedPreferences = getSharedPreferences("hearthistory", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        goback = findViewById(R.id.gobackhistory);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gobackintent = new Intent(Hearthistory.this, heartratepage.class);
                startActivity(gobackintent);
                finish();
            }
        });
        nodata = findViewById(R.id.nodata);
        recyclerView = findViewById(R.id.historyrelativelayout1234);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Hearthistory.this);
        loadData();

        hearthistoryadapter adapter = new hearthistoryadapter(Hearthistory.this, heartmodelArrayList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
    public void removedata(heartmodel item) {
        if (item!=null) {
            heartmodelArrayList.remove(item);
            Gson gson = new Gson();
            String json = gson.toJson(heartmodelArrayList);
            editor.putString("heartdata", json);
            editor.apply();
        } else {
            Toast.makeText(this, "Empty!!", Toast.LENGTH_SHORT).show();
        }
        if(heartmodelArrayList.size()==0)
        {
            nodata.setText("No Data Available");
        }
    }


    private void loadData() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("heartdata", null);
        Type type = new TypeToken<ArrayList<heartmodel>>() {
        }.getType();
        heartmodelArrayList = gson.fromJson(json, type);
        if (heartmodelArrayList == null) {
            heartmodelArrayList = new ArrayList<>();
            if (heartmodelArrayList.size() == 0) {
                nodata.setText("No Data Available");
            } else {
                nodata.setText("");
                Toast.makeText(this, String.valueOf(heartmodelArrayList.size()), Toast.LENGTH_SHORT).show();
            }

        }
        if (heartmodelArrayList.size() == 0) {
            nodata.setText("No Data Available");
        } else {
            nodata.setText("");
        }
    }
}