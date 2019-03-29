package com.hu.huattendensst;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

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
    static String password,countstr,lecture,pass;
    static int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        textView = findViewById(R.id.textView);
        pass = getIntent().getStringExtra("key");
        sharedPreference = new SharedPreference();
        arrayList=new ArrayList<>();
        arrayList=sharedPreference.getFavorites(MainActivity.this);
        lecture = getIntent().getStringExtra("lecture");
        database.getReference(getIntent().getStringExtra("doctorid")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                password = dataSnapshot.child(getIntent().getStringExtra("name")+"_"+lecture).child("pass_lecture")
                        .getValue(String.class);
                try{

                    countstr = dataSnapshot.child(getIntent().getStringExtra("name")+"_"+lecture).child("count")
                            .getValue(String.class);}
                catch (Exception e){
                    textView.setTextColor(Color.RED);
                    textView.setTextSize(50);
                    textView.setText("your attendees is  failed");

                }
                try{
                    count =Integer.parseInt(countstr);
                }
                catch (NumberFormatException e){
                    count =0;
                }
                if(password.matches(pass)){
                    database.getReference(getIntent().getStringExtra("doctorid")).child(getIntent().getStringExtra("name")+"_"+lecture).child("count")
                            .setValue(++count);
                    database.getReference(getIntent().getStringExtra("doctorid")).child(getIntent().getStringExtra("name")+"_"+lecture).child(String.valueOf(count))
                            .setValue(arrayList.get(0).getId());

                    textView.setTextColor(Color.GREEN);
                    textView.setTextSize(50);
                    textView.setText("your attendees is  successful");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
