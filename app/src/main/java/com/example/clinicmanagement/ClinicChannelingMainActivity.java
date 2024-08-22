package com.example.clinicmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.UUID;

public class ClinicChannelingMainActivity extends AppCompatActivity {

    private EditText cname, cage, cgender, caddress, cdiagnosis, cphone, cphone2;
    private Button btn_submit, btn_view;
    private FirebaseFirestore db;

    private String uId, uName, uAge, uGender, uAddress, uDiagnosis, uPhone, uPhone2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_channeling_main);
        getSupportActionBar().setTitle("Register Monthly Clinic");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cname = findViewById(R.id.cname);
        cage = findViewById(R.id.cage);
        cgender = findViewById(R.id.cgender);
        caddress = findViewById(R.id.caddress);
        cdiagnosis = findViewById(R.id.cdiagnosis);
        cphone = findViewById(R.id.cphone);
        cphone2 = findViewById(R.id.cphone2);


        btn_submit = findViewById(R.id.btn_submit);
        btn_view = findViewById(R.id.btn_view);

        db = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            btn_submit.setText("Update");
            uName = bundle.getString("uName");
            uAge = bundle.getString("uAge");
            uGender = bundle.getString("uGender");
            uAddress = bundle.getString("uAddress");
            uDiagnosis = bundle.getString("uDiagnosis");
            uPhone = bundle.getString("uPhone");
            uPhone2 = bundle.getString("uPhone2");
            uId = bundle.getString("uId");

            cname.setText(uName);
            cage.setText(uAge);
            cgender.setText(uGender);
            caddress.setText(uAddress);
            cdiagnosis.setText(uDiagnosis);
            cphone.setText(uPhone);
            cphone2.setText(uPhone2);


        } else{
            btn_submit.setText("Submit");
        }


        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ClinicChannelingMainActivity.this , ShowClinicChannelingActivity.class));
            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = cname.getText().toString();
                String age = cage.getText().toString();
                String gender = cgender.getText().toString();
                String address = caddress.getText().toString();
                String diagnosis = cdiagnosis.getText().toString();
                String phone = cphone.getText().toString();
                String phone2 = cphone2.getText().toString();

                Bundle bundle1 = getIntent().getExtras();
                if(bundle1 !=null){
                    String id = uId;
                    updateToFireStore(id, name, age, gender, address, diagnosis, phone, phone2);

                }else{
                    String id = UUID.randomUUID().toString();
                    saveToFireStore(id, name, age, gender, address, diagnosis, phone, phone2);

                }

            }
        });
    }

    private void updateToFireStore(String id, String name, String age, String gender, String address, String diagnosis, String phone, String phone2){
        db.collection("clinic-channeling").document(id).update("name" , name,
                "age" , age,
                "gender", gender,
                "address", address,
                "diagnosis", diagnosis,
                "phone" , phone,
                "phone2" , phone2)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ClinicChannelingMainActivity.this, "Data Updated!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ClinicChannelingMainActivity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ClinicChannelingMainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }





    private void saveToFireStore(String id, String name, String age, String gender, String address, String diagnosis, String phone, String phone2){
        if(!name.isEmpty() && !age.isEmpty() && !gender.isEmpty() && !address.isEmpty() && !diagnosis.isEmpty() && !phone.isEmpty() && !phone2.isEmpty()){
            HashMap<String , Object> map = new HashMap<>();
            map.put("id", id);
            map.put("name", name);
            map.put("age", age);
            map.put("gender", gender);
            map.put("address", address);
            map.put("diagnosis", diagnosis);
            map.put("phone", phone);
            map.put("phone2", phone2);

            db.collection("clinic-channeling").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(ClinicChannelingMainActivity.this, "Data saved!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ClinicChannelingMainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            });

        }else
            Toast.makeText(this, "Empty fields not allowed!", Toast.LENGTH_SHORT).show();

    }
}