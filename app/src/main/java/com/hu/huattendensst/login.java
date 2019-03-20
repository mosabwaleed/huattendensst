package com.hu.huattendensst;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class login extends AppCompatActivity {
    EditText id,pass;
    FirebaseFirestore db ;
    Button login;
    final String TAG = "login";
    int wrong;
    SharedPreference sharedPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        id = findViewById(R.id.id);
        pass =findViewById(R.id.pass);
        db = FirebaseFirestore.getInstance();
        sharedPreference = new SharedPreference();
//        sharedPreference.removeFavorite(login.this,0);
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("student")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if (document.getData().get("id").equals(id.getText().toString())&&
                                                document.getData().get("pass").equals(pass.getText().toString())){
                                            sharedPreference.addFavorite(login.this,new info(id.getText()+"",pass.getText()+""));
                                            Intent intent = new Intent(login.this,data.class);
                                            intent.putExtra("id",document.getData().get("id").toString());
                                            startActivity(intent);
                                        }
                                        else {
                                            wrong++;
                                            if(wrong> task.getResult().size()){
                                                Toast.makeText(login.this,"wrong id or pass",Toast.LENGTH_SHORT).show();}
                                        }
                                    }
                                } else {
                                    //Log.w(TAG, "Error getting documents.", task.getException());
                                    Toast.makeText(login.this,"Error getting documents."+ task.getException().toString(),Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(sharedPreference.getFavorites(login.this).size()==1){
            Intent intent = new Intent(login.this,data.class);
            intent.putExtra("id",sharedPreference.getFavorites(login.this).get(0).getId());
            startActivity(intent);
        }
    }
}
