package com.example.clinicmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class PatientDetails extends AppCompatActivity {

    private EditText mAddress,mPhone,mEmail;
    private Button mSubmit,mShowAll;
    private FirebaseFirestore db;
    private String uId,uAddress,uPhone,uEmail,uTestMode,uPaymentMethod,test;
    private RadioButton tMode1,tMode2,pMethod1,pMethod2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);
        getSupportActionBar().setTitle("Enter Patient Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAddress=findViewById(R.id.ET_address);
        mPhone=findViewById(R.id.ET_phone);
        mEmail=findViewById(R.id.ET_email);
        mSubmit=findViewById(R.id.btn_submit);
        mShowAll=findViewById(R.id.btn_showAll);
        tMode1=findViewById(R.id.rb_visitHospital);
        tMode2=findViewById(R.id.rb_mobileTest);
        pMethod1=findViewById(R.id.rb_cash);
        pMethod2=findViewById(R.id.rb_card);

        db=FirebaseFirestore.getInstance();

        //update part
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            mSubmit.setText("update");
            uAddress=bundle.getString("uAddress");
            uPhone=bundle.getString("uPhone");
            uId=bundle.getString("uId");
            uEmail=bundle.getString("uEmail");
            uTestMode=bundle.getString("testMode");
            uPaymentMethod=bundle.getString("paymentMethod");
            if(uTestMode.equals("Visit hospital")){
                tMode1.setChecked(true);
            }else if(uPaymentMethod.equals("Mobile test")){
                tMode2.setChecked(true);
            }

            if(uPaymentMethod.equals("Cash")){
                pMethod1.setChecked(true);
            }else if(uPaymentMethod.equals("Card")){
                pMethod2.setChecked(true);
            }
            mAddress.setText(uAddress);
            mPhone.setText(uPhone);
            mEmail.setText(uEmail);

        }else{
            mSubmit.setText("Save");
        }

        //getting the test
        Intent intentTest=getIntent();
        test=intentTest.getStringExtra("dinul");



        mShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PatientDetails.this, ShowTests.class));
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String  address=mAddress.getText().toString();
                String phone=mPhone.getText().toString();
                String email=mEmail.getText().toString();
                String testMode = "";
                String paymentMethod = "";
                if(tMode1.isChecked()){
                    testMode="Visit hospital";
                }else if(tMode2.isChecked()){
                    testMode="Mobile test";
                }else{
                    Toast.makeText(PatientDetails.this, "Please select a test mode", Toast.LENGTH_SHORT).show();
                }
                //payment method
                if(pMethod1.isChecked()){
                    paymentMethod="Cash";
                }else if(pMethod2.isChecked()){
                    paymentMethod="Card";
                }else{
                    Toast.makeText(PatientDetails.this, "Please select a payment method", Toast.LENGTH_SHORT).show();
                }

                Bundle bundle1=getIntent().getExtras();
                if(bundle1 !=null){
                    String id=uId;
                    updateFireStore(id,address,phone,email,testMode,paymentMethod);

                }else {
                    String id= UUID.randomUUID().toString();
                    saveToFireStore(id,address,phone,email,testMode,paymentMethod,test);
                }


            }
        });

    }

    private void updateFireStore(String id,String address,String phone,String email,String testMode,String paymentMethod){
        db.collection("tests").document(id).update("address",address,"phone",phone,"email",email,"testMode",testMode,"paymentMethod",paymentMethod)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(PatientDetails.this, "Data updated!!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(PatientDetails.this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PatientDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void saveToFireStore(String id,String address,String phone,String email,String testMode,String paymentMethod,String test ){

        if(!address.isEmpty() && !phone.isEmpty() && !email.isEmpty()){

            HashMap<String,Object> map=new HashMap<>();
            map.put("id",id);
            map.put("address",address);
            map.put("phone",phone);
            map.put("email",email);
            map.put("testMode",testMode);
            map.put("paymentMethod",paymentMethod);
            map.put("test",test);

            db.collection("tests").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(PatientDetails.this, "Data saved", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PatientDetails.this, "Failed !!", Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            Toast.makeText(this, "Empty fields are not accepted", Toast.LENGTH_SHORT).show();
        }

    }


}