package com.teleconta.pas.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teleconta.pas.R
import com.teleconta.pas.adapters.ServicesAdapter
import com.teleconta.pas.entities.PhoneServices
import com.teleconta.pas.managers.ChooseLineManager
import com.teleconta.pas.managers.ServicesManager

class ViewServicesActivity : AppCompatActivity(), ServicesManager.ServicesManagerCallback {

    private lateinit var manager: ServicesManager
    private lateinit var services: PhoneServices
    private lateinit var internetUse: TextView
    private lateinit var errorText: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var closeAppButton: Button
    private lateinit var backBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_services_main)

        initializeViews()
        val line = intent.getStringExtra("LINE_EXTRA")
        if(line != null){
            manager.getServices(line)
        }
    }

    private fun initializeViews() {

        backBtn = findViewById(R.id.backBtnViewServices)
        manager = ServicesManager(this)
        internetUse = findViewById(R.id.viewServicesInternetUse)
        recyclerView = findViewById(R.id.servicesViewServicesList)
        closeAppButton = findViewById(R.id.closeAppButton)
        errorText = findViewById(R.id.errorServices)

        closeAppButton.setOnClickListener {
            closeApp()
        }

        backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }
    }

    private fun displayData(){
        if(services.otherServices.size > 0){
            internetUse.text = formatValue(services.internetConsume)

            val adapter = ServicesAdapter(services.otherServices)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }
        else {
            errorText.text = "Nenhum serviço encontrado"
        }
    }

    override fun onSuccess(phoneServices: PhoneServices) {
        this.services = phoneServices
        displayData()
    }

    private fun closeApp(){
        finishAffinity()
    }

    private fun formatValue(value: Double): String {
        return String.format("%.2f", value).replace('.', ',')
    }
}