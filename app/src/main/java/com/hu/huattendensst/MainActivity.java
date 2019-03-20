package com.hu.huattendensst;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    FirebaseDatabase database;
    SharedPreference sharedPreference;
    ArrayList<info> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        textView = findViewById(R.id.textView);
        textView.setText(getIntent().getStringExtra("key"));
        sharedPreference = new SharedPreference();
        arrayList=new ArrayList<>();
        arrayList=sharedPreference.getFavorites(MainActivity.this);

//        database.getReference(arrayList.get(0).getDoctorid()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }
}
