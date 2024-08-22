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

public class ReviewTest extends AppCompatActivity {

    private EditText mFeedback;
    private Button mSubmit;
    private RatingBar ratingBar;
    String uId;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_test);
        getSupportActionBar().setTitle("Review");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFeedback=findViewById(R.id.et_feedback);
        mSubmit=findViewById(R.id.btn_submitFeed);
        ratingBar=(RatingBar) findViewById(R.id.rt_feedbackRating);

        Intent intent=getIntent();
        uId=intent.getStringExtra("uId");

        //db
        db=FirebaseFirestore.getInstance();

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String feedback=mFeedback.getText().toString();
                String id= UUID.randomUUID().toString();
                Float rating=ratingBar.getRating();

                saveToFireStore(id,feedback,uId,rating);
            }
        });

    }

    private void saveToFireStore(String id,String feedBack,String uId,Float rating){
        if(!feedBack.isEmpty()){
            HashMap<String,Object> map=new HashMap<>();
            map.put("id",id);
            map.put("feedBack",feedBack);
            map.put("testId",uId);
            map.put("rating",rating);

            db.collection("feedback").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ReviewTest.this, "Thank you for the feedback", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ReviewTest.this, "Failed !!", Toast.LENGTH_SHORT).show();

                }
            });

        }else{
            Toast.makeText(this, "Empty fields not allowed", Toast.LENGTH_SHORT).show();

        }
    }


}