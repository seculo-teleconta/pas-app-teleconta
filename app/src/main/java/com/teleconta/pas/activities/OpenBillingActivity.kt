package com.teleconta.pas.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teleconta.pas.R
import com.teleconta.pas.adapters.OpenBillingsAdapter
import com.teleconta.pas.entities.OpenBilling
import com.teleconta.pas.managers.OpenBillingsManager

class OpenBillingActivity : AppCompatActivity() , OpenBillingsManager.OpenBillingsCallback{

    private lateinit var openBillingsManager: OpenBillingsManager
    private lateinit var billings: List<OpenBilling>
    private lateinit var recyclerView: RecyclerView
    private lateinit var errorTextView: TextView
    private lateinit var backBtn: Button
    private lateinit var closeAppButton: Button

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
        closeAppButton = findViewById(R.id.closeAppButton)

        backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }

        closeAppButton.setOnClickListener {
            closeApp()
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
        if(billings.size > 0){
            val adapter = OpenBillingsAdapter(billings)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }
        else {
            errorTextView.text = "Nenhuma conta em aberto encontrada";
        }
    }

    private fun closeApp(){
        finishAffinity()
    }
}