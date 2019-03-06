package com.example.loancalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoanTableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_table);
        LoanCalculatorActivity.ACTIVITY_CHANGE+=1;
    }
}
