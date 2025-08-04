package com.teleconta.pas.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.teleconta.pas.R

class HomeActivity : AppCompatActivity() {

    private lateinit var welcomeTextView: TextView
    private lateinit var openBillingsButton: Button
    private lateinit var paidBillingsButton: Button
    private lateinit var usedServicesButton: Button
    private lateinit var solicitationsButton: Button
    private lateinit var userDataButton: Button
    private lateinit var closeAppButton: Button

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
        usedServicesButton = findViewById(R.id.buttonUsedServices)
        solicitationsButton = findViewById(R.id.buttonSolicitations)
        userDataButton = findViewById(R.id.buttonUserData)
        closeAppButton = findViewById(R.id.closeAppButton)


        solicitationsButton.setOnClickListener {
            solicitationsActivity()
        }

        userDataButton.setOnClickListener {
            userDataActivity()
        }

        openBillingsButton.setOnClickListener {
            openBillingsActivity()
        }

        paidBillingsButton.setOnClickListener{
            paidBillingsActivity()
        }

        closeAppButton.setOnClickListener {
            closeApp()
        }

        usedServicesButton.setOnClickListener {
            usedServicesActivity()
        }
    }

    private fun sayHello(){
        welcomeTextView = findViewById(R.id.textHello)

        val extraData = intent.getStringExtra("NAME_DATA")
        if (extraData != null) {
            welcomeTextView.text = "Olá, $extraData!"
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

    private fun solicitationsActivity() {
        val cpf = intent.getStringExtra("CPF_DATA")

        val intent = Intent(this, SolicitationMainActivity::class.java)

        intent.putExtra("CPF_EXTRA", cpf)

        startActivity(intent)
    }

    private fun paidBillingsActivity(){
        val cpf = intent.getStringExtra("CPF_DATA")

        val intent = Intent(this, PaidBillingsActivity::class.java)

        intent.putExtra("CPF_EXTRA", cpf)

        startActivity(intent)
    }

    private fun userDataActivity(){
        val cpf = intent.getStringExtra("CPF_DATA")

        val intent = Intent(this, UserDataActivity::class.java)

        intent.putExtra("CPF_EXTRA", cpf)

        startActivity(intent)
    }

    private fun usedServicesActivity(){
        val cpf = intent.getStringExtra("CPF_DATA")

        val intent = Intent(this, ChooseLineActivity::class.java)

        intent.putExtra("CPF_EXTRA", cpf)

        startActivity(intent)
    }

    private fun closeApp(){
        finishAffinity()
    }
}
