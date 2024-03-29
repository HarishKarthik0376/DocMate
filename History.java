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

import com.example.doctormate.adapters.recyclerviewadapter;
import com.example.doctormate.models.bmimodels;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class History extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<bmimodels> arrayList;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView nodata;
    ImageView goback;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        goback = findViewById(R.id.gobackhistory);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gobackintent = new Intent(History.this, bmi.class);
                startActivity(gobackintent);
                finish();
            }
        });
        nodata = findViewById(R.id.nodata);
        recyclerView = findViewById(R.id.historyrelativelayout);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        sharedPreferences = getSharedPreferences("Bmihistory", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        loadData();

        recyclerviewadapter adapter = new recyclerviewadapter(History.this, arrayList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
    public void removedata(bmimodels item) {
        if (item!=null) {
            arrayList.remove(item);
            Gson gson = new Gson();
            String json = gson.toJson(arrayList);
            editor.putString("data", json);
            editor.apply();
        } else {
            Toast.makeText(this, "Empty!!", Toast.LENGTH_SHORT).show();
        }
        if(arrayList.size()==0)
        {
            nodata.setText("No Data Available");
        }
    }


    private void loadData() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("data", null);
        Type type = new TypeToken<ArrayList<bmimodels>>() {
        }.getType();
        arrayList = gson.fromJson(json, type);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            if (arrayList.size() == 0) {
                nodata.setText("No Data Available");
            } else {
                nodata.setText("");
            }

        }
        if (arrayList.size() == 0) {
            nodata.setText("No Data Available");
        } else {
            nodata.setText("");
        }
    }
}