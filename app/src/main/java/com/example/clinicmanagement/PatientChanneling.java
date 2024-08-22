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

public class PatientChanneling extends AppCompatActivity {
    private EditText pcountry, pname, pphone, pemail, paddress;
    private Button book, view;
    private FirebaseFirestore db;
    private String uId, uCountry, uName, uPhone, uEmail, uAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_channeling);
        getSupportActionBar().setTitle("Channeling");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pcountry = findViewById(R.id.pcountry);
        pname = findViewById(R.id.pname);
        pphone = findViewById(R.id.pphone);
        pemail = findViewById(R.id.pemail);
        paddress = findViewById(R.id.paddress);
        book = findViewById(R.id.btn_book);
        view = findViewById(R.id.btn_view);

        db = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            book.setText("Update");
            uId = bundle.getString("uId");
            uCountry = bundle.getString("uCountry");
            uName = bundle.getString("uName");
            uPhone = bundle.getString("uPhone");
            uEmail = bundle.getString("uEmail");
            uAddress = bundle.getString("uAddress");

            pcountry.setText(uCountry);
            pname.setText(uName);
            pphone.setText(uPhone);
            pemail.setText(uEmail);
            paddress.setText(uAddress);


        } else {
            book.setText("Save");

        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PatientChanneling.this, ViewPatientChanneling.class));
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String country = pcountry.getText().toString();
                String name = pname.getText().toString();
                String phone = pphone.getText().toString();
                String email = pemail.getText().toString();
                String address = paddress.getText().toString();

                Bundle bundle1 = getIntent().getExtras();
                if(bundle1 != null){
                    String id = uId;
                    updateToFireStore(id, country, name, phone, email, address);


                }else{
                    String id = UUID.randomUUID().toString();
                    saveToFireStore(id, country, name, phone, email, address);
                }

            }
        });
    }

    private void updateToFireStore(String id, String country, String name, String phone, String email, String address){
        db.collection("patient-channeling").document(id).update("country" , country,
                "name" , name ,
                "phone ", phone ,
                "email", email,
                "address" , address)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(PatientChanneling.this, "Data updated Successfully!", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(PatientChanneling.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PatientChanneling.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveToFireStore(String id, String country, String name, String phone, String email, String address) {
        if (!country.isEmpty() && !name.isEmpty() && !phone.isEmpty() && !email.isEmpty() && !address.isEmpty()) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("country", country);
            map.put("name", name);
            map.put("phone", phone);
            map.put("email", email);
            map.put("address", address);

            db.collection("patient-channeling").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(PatientChanneling.this, "Data Saved!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PatientChanneling.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            });

        } else
            Toast.makeText(this, "Empty fields not allowed!", Toast.LENGTH_SHORT).show();
    }
}