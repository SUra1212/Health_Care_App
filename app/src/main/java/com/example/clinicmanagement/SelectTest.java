package com.example.clinicmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SelectTest extends AppCompatActivity {

    Button bTest1,bTest2,bTest3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_test);
        getSupportActionBar().setTitle("Select Test");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bTest1=findViewById(R.id.btn_test1);
        bTest2=findViewById(R.id.btn_test2);
        bTest3=findViewById(R.id.btn_test3);

        bTest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(SelectTest.this,TestPrepare.class);
                intent1.putExtra("test","Full blood count");
                startActivity(intent1);
            }
        });
        bTest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(SelectTest.this,TestPrepare.class);
                intent2.putExtra("test","Urine test");
                startActivity(intent2);
            }
        });

        bTest3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3=new Intent(SelectTest.this,TestPrepare.class);
                intent3.putExtra("test","PCR test");
                startActivity(intent3);
            }
        });


    }






}