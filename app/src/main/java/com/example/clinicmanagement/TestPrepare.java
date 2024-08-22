package com.example.clinicmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TestPrepare extends AppCompatActivity {

    TextView tvTestDesc;
    String test;
    RadioButton rbButton1;
    RadioButton rbButton2;
    Button btnSubmit;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_prepare);
        getSupportActionBar().setTitle("Lab Test Preparation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        tvTestDesc=findViewById(R.id.tv_testDescription);
        rbButton1=findViewById(R.id.rb_testYes);
        rbButton2=findViewById(R.id.rb_testNo);
        btnSubmit=findViewById(R.id.btn_prepareTest);

        Intent intent=getIntent();
        test=intent.getStringExtra("test");

        if(test.equals("Full blood count")){
            tvTestDesc.setText("If your blood sample is being tested only for a complete blood count, you can eat and drink normally before the test");
        }else if(test.equals("Urine test")){
            tvTestDesc.setText("you can eat and drink before the test. If you're having other tests, you might need to fast before the test.");
        }else if(test.equals("PCR test")){
            tvTestDesc.setText("For a COVID PCR swab test, you should: Avoid nasal sprays or any other solution in the nose for 24 hours before your test. Avoid eating salty meals or drinking alcohol for 2 hours before your test.");
        }








    }
    public void btn_prepareTest(View view) {
        String test1="blood count";
        Intent intent1=new Intent(this, PatientDetails.class);
        //intent1.putExtra("dinul",test1);
        startActivity(intent1);
    }

//    public void testContinue(){
//
//        Intent getIntent=getIntent();
//
//
//        if(rbButton1.isChecked()){
//            Innt intent1=new Intent(TestPrepare.this,PatientDetails.class);
//            intent1.putExtra("testName","Full blood count");
//            startAtectivity(intent1);
//        }else{
//            Toast.makeText(this, "OOOPs hadapan", Toast.LENGTH_SHORT).show();
//        }
//
//
//    }






}