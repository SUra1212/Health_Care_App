package com.example.clinicmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class midwife2 extends AppCompatActivity {

    private EditText mid1,mid2,mid3,mid4,mid5,mid6,mid7,mid8,mid9;
    private Button midSub;
    String uId;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.midwife2);
        getSupportActionBar().setTitle("Request Midwife");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mid1=findViewById(R.id.mid1);
        mid2=findViewById(R.id.mid2);
        mid3=findViewById(R.id.mid3);
        mid4=findViewById(R.id.mid4);
        mid5=findViewById(R.id.mid5);
        mid6=findViewById(R.id.mid6);
        mid7=findViewById(R.id.mid7);
        mid8=findViewById(R.id.mid8);
        mid9=findViewById(R.id.mid9);
        midSub=findViewById(R.id.mid_sub);


        Intent intent=getIntent();
        uId=intent.getStringExtra("uId");

        //db
        db=FirebaseFirestore.getInstance();

        midSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Mid1= mid1.getText().toString();
                String Mid2= mid2.getText().toString();
                String Mid3= mid3.getText().toString();
                String Mid4= mid4.getText().toString();
                String Mid5= mid5.getText().toString();
                String Mid6= mid6.getText().toString();
                String Mid7= mid7.getText().toString();
                String Mid8= mid8.getText().toString();
                String Mid9= mid9.getText().toString();
                String id= UUID.randomUUID().toString();

                saveToFireStore(id,Mid1,Mid2,Mid3,Mid4,Mid5,Mid6,Mid7,Mid8,Mid9,uId);
            }
        });

    }

    private void saveToFireStore(String id,String Mid1,String Mid2,String Mid3,String Mid4,String Mid5,String Mid6,String Mid7,String Mid8,String Mid9,String uId){
        if(!Mid1.isEmpty() && !Mid2.isEmpty() &&!Mid3.isEmpty() && !Mid4.isEmpty() &&!Mid5.isEmpty() && !Mid6.isEmpty() &&!Mid7.isEmpty() &&!Mid8.isEmpty() &&!Mid9.isEmpty()){
            HashMap<String,Object> map=new HashMap<>();
            map.put("id",id);
            map.put("Mid1",Mid1);
            map.put("Mid2",Mid2);
            map.put("Mid3",Mid3);
            map.put("Mid4",Mid4);
            map.put("Mid5",Mid5);
            map.put("Mid6",Mid6);
            map.put("Mid7",Mid7);
            map.put("Mid8",Mid8);
            map.put("Mid9",Mid9);
            map.put("testId",uId);


            db.collection("Midwife").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(midwife2.this, "Details Saved", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(midwife2.this, "Failed !!", Toast.LENGTH_SHORT).show();

                }
            });

        }else{
            Toast.makeText(this, "Empty fields not allowed", Toast.LENGTH_SHORT).show();

        }
    }


}