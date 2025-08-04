package com.teleconta.pas.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teleconta.pas.R
import com.teleconta.pas.adapters.PaidBillingAdapter
import com.teleconta.pas.entities.PaidBilling
import com.teleconta.pas.managers.PaidBillingsManager

class PaidBillingsActivity: AppCompatActivity(), PaidBillingsManager.PaidBillingsCallBack {

    private lateinit var paidBillingsManager: PaidBillingsManager
    private lateinit var billings: List<PaidBilling>
    private lateinit var backBtn: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var errorTextView: TextView
    private lateinit var closeAppButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_view_main)

        initializeViews()
        val cpf = intent.getStringExtra("CPF_EXTRA")
        if(cpf != null) {
            paidBillingsManager.getPaidBillings(cpf)
        }
    }

    private fun initializeViews() {
        backBtn = findViewById(R.id.backBtnOpenBillings)
        paidBillingsManager = PaidBillingsManager(this)
        errorTextView = findViewById(R.id.errorOpenBilling)
        recyclerView = findViewById(R.id.openBillingsList)
        closeAppButton = findViewById(R.id.closeAppButton)

        backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }

        closeAppButton.setOnClickListener {
            closeApp()
        }
    }

    override fun onSuccess(billings: List<PaidBilling>) {
        this.billings = billings
        displayBillings()
    }

    override fun onFailure(errorMessage: String) {
        errorTextView.text = errorMessage
    }

    private fun displayBillings() {
        val adapter = PaidBillingAdapter(billings)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun closeApp(){
        finishAffinity()
    }
}