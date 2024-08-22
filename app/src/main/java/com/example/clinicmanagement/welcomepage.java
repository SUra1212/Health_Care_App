package com.example.clinicmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class welcomepage extends AppCompatActivity {

    private TextView skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomepage);
        getSupportActionBar().setTitle("Suwa Arana");

        skip = findViewById(R.id.welcomeskip);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(welcomepage.this, login.class));
            }
        });

    }
}

