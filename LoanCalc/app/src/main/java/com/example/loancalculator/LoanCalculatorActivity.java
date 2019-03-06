package com.example.loancalculator;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class LoanCalculatorActivity extends AppCompatActivity {
    // Initialize Variables
    Button calculate, reset, table;
    EditText loanAmount, apr, loanTerm, loanPayment;
    TextView interestPaid, loanTextView, interestTextView;

    // Global Variables
    public static double LOAN_AMOUNT = 0.0;
    public static double A_P_R = 0.0;
    public static double LOAN_PAYMENT = 0.0;
    public static double LOAN_INTEREST  = 0.0;
    public static int LOAN_TERM = 0;
    public static int ACTIVITY_CHANGE = 0;

    // ComputeTotal method meant to find the total of all items in the list
    public void calculatePayment() {
        double rate = A_P_R/12;
        LOAN_PAYMENT = LOAN_AMOUNT * (rate + (rate/(Math.pow(1+rate, LOAN_TERM*12) - 1)));
        LOAN_PAYMENT = Math.round(LOAN_PAYMENT*100.0) / 100.0; //Rounding to 2 decimal places
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_calculator_activity);

        // BUTTONS
        calculate = findViewById(R.id.calculateButton);
        reset = findViewById(R.id.main_resetButton);
        table= findViewById(R.id.main_showTableButton);

        // EDIT TEXTS
        interestTextView = findViewById(R.id.interestTextView);
        loanAmount = findViewById(R.id.loanAmount);
        loanTextView = findViewById(R.id.loanTextView);
        apr = findViewById(R.id.enterAPR);
        loanTerm = findViewById(R.id.loanTerm);
        loanPayment = findViewById(R.id.loanPayment);
        interestPaid = findViewById(R.id.interestPaid);

        // When Compute is clicked
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Gets the Item's Values for computation
                LOAN_AMOUNT = Double.parseDouble(loanAmount.getText().toString());
                LOAN_TERM = Integer.parseInt(loanTerm.getText().toString());

                // Sets sales_Tax to the user entered value for computation
                A_P_R = Double.parseDouble(apr.getText().toString()) / 100;


                // Will only compute if the user has entered an item
                if (LOAN_AMOUNT > 0 && LOAN_TERM > 0 ) {
                    calculatePayment();
                    String calculatedPayment = new DecimalFormat("$#,##0.00").format(LOAN_PAYMENT);
                    loanPayment.setText(calculatedPayment);
                    String amountString = new DecimalFormat("$#,##0.00").format(LOAN_AMOUNT);
                    loanAmount.setText(amountString);
                    loanTextView.setTextColor(Color.BLACK);
                    double tempApr = A_P_R * 100;
                    String aprString = new DecimalFormat("#,##0.00 '%'").format(tempApr);
                    apr.setText(aprString);
                    // Disables editing the fields
                    loanAmount.setEnabled(false); apr.setEnabled(false); loanTerm.setEnabled(false);
                    loanPayment.setEnabled(false);

                    // Enable AddItem and ShowList and disable Compute
                    calculate.setEnabled(false); reset.setEnabled(true); table.setEnabled(true);
                }
            }
        });

        // Changing to addItem activity upon button press
        table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoanCalculatorActivity.this, LoanTableActivity.class));
            }
        });

        // Changing to showList activity upon button press
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loanAmount.setText(""); apr.setText(""); loanTerm.setText(""); loanPayment.setText("");
                calculate.setEnabled(true); reset.setEnabled(false); table.setEnabled(false);
                loanAmount.setEnabled(true); apr.setEnabled(true); loanTerm.setEnabled(true);
                loanTextView.setTextColor(Color.parseColor("#dcdcdc"));
                interestTextView.setTextColor(Color.parseColor("#dcdcdc"));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ACTIVITY_CHANGE > 0) {
            // Disables the EditTexts and enables AddItem and ShowList buttons
            calculate.setEnabled(false); reset.setEnabled(true); table.setEnabled(true);
            loanAmount.setEnabled(false); apr.setEnabled(false); loanTerm.setEnabled(false);
            loanPayment.setEnabled(false);
            interestTextView.setTextColor(Color.BLACK);


            loanTerm.setText(Integer.toString(LOAN_TERM));
            String paymentString = new DecimalFormat("$#,##0.00").format(LOAN_PAYMENT);
            loanPayment.setText(paymentString);
            double tempApr = A_P_R * 100;
            String aprString = new DecimalFormat("#,##0.00 '%'").format(tempApr);
            apr.setText(aprString);
            String amountString = new DecimalFormat("$#,##0.00").format(LOAN_AMOUNT);
            loanAmount.setText(amountString);


        }
    }
}
