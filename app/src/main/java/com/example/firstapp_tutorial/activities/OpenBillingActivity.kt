package com.example.firstapp_tutorial.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp_tutorial.R
import com.example.firstapp_tutorial.adapters.OpenBillingsAdapter
import com.example.firstapp_tutorial.entities.OpenBilling
import com.example.firstapp_tutorial.managers.OpenBillingsManager

class OpenBillingActivity : AppCompatActivity() , OpenBillingsManager.OpenBillingsCallback{

    private lateinit var openBillingsManager: OpenBillingsManager
    private lateinit var billings: List<OpenBilling>
    private lateinit var recyclerView: RecyclerView
    private lateinit var errorTextView: TextView
    private lateinit var backBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_view_main)

        initializeViews()
        val cpf = intent.getStringExtra("CPF_EXTRA")
        if(cpf != null) {
            openBillingsManager.getOpenBillings(cpf)
        }
    }

    private fun initializeViews() {
        openBillingsManager = OpenBillingsManager(this)
        errorTextView = findViewById(R.id.errorOpenBilling)
        backBtn = findViewById(R.id.backBtnOpenBillings)
        recyclerView = findViewById(R.id.openBillingsList)

        backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }
    }

    override fun onSuccess(billings: List<OpenBilling>){
        this.billings = billings
        displayBillings()
    }

    override fun onFailure(errorMessage: String){
        errorTextView.text = errorMessage
    }

    private fun displayBillings() {
        val adapter = OpenBillingsAdapter(billings)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}