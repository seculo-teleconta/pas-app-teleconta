package com.example.firstapp_tutorial.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firstapp_tutorial.R

class HomeActivity : AppCompatActivity() {

    private lateinit var welcomeTextView: TextView
    private lateinit var openBillingsButton: Button
    private lateinit var paidBillingsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_main)

        initializeViews()
        sayHello()
    }

    private fun initializeViews() {
        welcomeTextView = findViewById(R.id.textHello)
        openBillingsButton = findViewById(R.id.buttonOpenBillings)
        paidBillingsButton = findViewById(R.id.buttonPaidBillings)

        openBillingsButton.setOnClickListener {
            openBillingsButton.isEnabled = false

            openBillingsActivity()
        }

        paidBillingsButton.setOnClickListener{
            paidBillingsButton.isEnabled = false

            paidBillingsActivity()
        }
    }

    private fun sayHello(){
        welcomeTextView = findViewById(R.id.textHello)

        val extraData = intent.getStringExtra("NAME_DATA")
        if (extraData != null) {
            welcomeTextView.text = "Welcome, $extraData!"
        } else {
            welcomeTextView.text = "Welcome to Home!"
        }
    }

    private fun openBillingsActivity() {
        val cpf = intent.getStringExtra("CPF_DATA")

        val intent = Intent(this, OpenBillingActivity::class.java)

        intent.putExtra("CPF_EXTRA", cpf)

        startActivity(intent)
    }

    private fun paidBillingsActivity(){
        val cpf = intent.getStringExtra("CPF_DATA")

        val intent = Intent(this, PaidBillingsActivity::class.java)

        intent.putExtra("CPF_EXTRA", cpf)

        startActivity(intent)
    }
}
