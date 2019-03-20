package com.hu.huattendensst;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class data extends AppCompatActivity {
    public static View.OnClickListener myOnClickListener;
    RecyclerView material;
    public static ArrayList<material_data> data;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    FirebaseFirestore db ;
    String id;
    material_data material_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        material = findViewById(R.id.material);
        material.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        material.setLayoutManager(layoutManager);
        material.setItemAnimator(new DefaultItemAnimator());
        db = FirebaseFirestore.getInstance();
        id = getIntent().getStringExtra("id");
        data = new ArrayList<material_data>();
        db.collection("student").document(id).collection("material")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                material_data = new material_data(document.getId(),
                                        document.getData().get("time").toString(),
                                        document.getData().get("hall_num").toString(),
                                        document.getData().get("sec_num").toString(),
                                        document.getData().get("days").toString(),
                                        document.getData().get("doctorid").toString());
                                data.add(material_data);
                                adapter = new adapter(data);
                                material.setAdapter(adapter);
                            }
                        } else {
                            //Log.w(TAG, "Error getting documents.", task.getException());
                            Toast.makeText(data.this,"Error getting documents."+ task.getException().toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
