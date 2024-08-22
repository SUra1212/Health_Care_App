package com.example.clinicmanagement;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BmiCalculator extends AppCompatActivity {

    EditText weight, height;
    TextView resulttext;
    String calculation, BMIresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bmi_calculator);
        getSupportActionBar().setTitle("BMI Calculator");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        weight = findViewById(R.id.weight);
        height = findViewById(R.id.height);
        resulttext = findViewById(R.id.result);

    }

    public void CalculateBMI(View view) {
        String S1 = weight.getText().toString();
        String S2 = height.getText().toString();

        //retrieve values
        float weightValue = Float.parseFloat(S1);
        float heightValue = Float.parseFloat(S2) / 100;

        //formula for calculate BMI
        float bmi = weightValue / (heightValue * heightValue);

        //display result according to the calculation
        if(bmi < 16){
            BMIresult = "Severely Under Weight";
        }else if(bmi < 18.5) {
            BMIresult = "Under Weight";
        }else if(bmi >= 18.5 && bmi <=24.9) {
            BMIresult = "Normal Weight";
        }else if(bmi >=25 && bmi <=29.9){
            BMIresult = "Overweight";
        }else{
            BMIresult = "Obese";
        }
        calculation = "Result:\n" + bmi + "\n" + BMIresult;

        resulttext.setText(calculation);

    }
}
