package com.hu.huattendensst;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class choose extends AppCompatActivity {
EditText lecture,lecture2,pass;
Button qr,passb;
FirebaseDatabase database;
SharedPreference sharedPreference;
ArrayList<info> arrayList;
static String password;
static int count;
static String countstr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        lecture = findViewById(R.id.lecture);
        lecture2 = findViewById(R.id.lecture2);
        pass = findViewById(R.id.pass);
        qr = findViewById(R.id.qr);
        passb = findViewById(R.id.passb);
        database = FirebaseDatabase.getInstance();
        sharedPreference = new SharedPreference();
        arrayList=new ArrayList<>();
        arrayList=sharedPreference.getFavorites(choose.this);

        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(choose.this,qr_capture.class);
                intent.putExtra("name",getIntent().getStringExtra("name"));
                if(!lecture.getText().toString().isEmpty()){
                intent.putExtra("lecture",lecture.getText().toString());
                intent.putExtra("doctorid",getIntent().getStringExtra("doctorid"));
                intent.putExtra("name",getIntent().getStringExtra("name"));
                startActivity(intent);
                }
                else Toast.makeText(choose.this,"please fill lecture field",Toast.LENGTH_LONG);
            }
        });


        passb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lecture.setVisibility(View.GONE);
                qr.setVisibility(View.GONE);
                lecture2.setVisibility(View.VISIBLE);
                pass.setVisibility(View.VISIBLE);
                passb.setText("register your attended");
                passb.setTag("set");
                if(passb.getTag().equals("set")){
                if(!(lecture2.getText().toString().isEmpty()&&pass.getText().toString().isEmpty())){
                    database.getReference(getIntent().getStringExtra("doctorid")).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            password = dataSnapshot.child(getIntent().getStringExtra("name")+"_"+lecture2.getText()+"").child("pass_lecture")
                                    .getValue(String.class);
                            try{

                            countstr = dataSnapshot.child(getIntent().getStringExtra("name")+"_"+lecture2.getText()+"").child("count")
                                    .getValue(String.class);}
                                    catch (Exception e){
                                        Toast.makeText(choose.this,"failed please try again",Toast.LENGTH_LONG).show();

                                    }
                            try{
                                count =Integer.parseInt(countstr);
                            }
                            catch (NumberFormatException e){
                                count =0;
                            }
                            if(password.equals(pass.getText()+"")){
                                database.getReference(getIntent().getStringExtra("doctorid")).child(getIntent().getStringExtra("name")+"_"+lecture2.getText().toString()).child("count")
                                        .setValue(++count);
                                database.getReference(getIntent().getStringExtra("doctorid")).child(getIntent().getStringExtra("name")+"_"+lecture2.getText().toString()).child(String.valueOf(count))
                                        .setValue(arrayList.get(0).getId());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else Toast.makeText(choose.this,"please fill all field",Toast.LENGTH_LONG).show();
            }
            }
        });
    }
}
