package com.example.clinicmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class midwife1 extends AppCompatActivity {

    private TextView skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.midwife1);
        getSupportActionBar().setTitle("Postnatal Care Midwife");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        skip = findViewById(R.id.btn_request);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(midwife1.this, midwife2.class));
            }
        });

    }
}