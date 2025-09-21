package com.teleconta.pas.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teleconta.pas.R
import com.teleconta.pas.adapters.OpenBillingsAdapter
import com.teleconta.pas.adapters.SolicitationsAdapter
import com.teleconta.pas.entities.Solicitation
import com.teleconta.pas.managers.OpenBillingsManager
import com.teleconta.pas.managers.SolicitationsManager

class SolicitationMainActivity : AppCompatActivity(), SolicitationsManager.SolicitationsCallBack {

    private lateinit var manager: SolicitationsManager
    private lateinit var createSolicitationsButton: Button
    private lateinit var solicitations: List<Solicitation>
    private lateinit var recyclerView: RecyclerView
    private lateinit var errorTextView: TextView
    private lateinit var backBtn: Button
    private lateinit var closeAppButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.solicitations_main)

        initializeViews()
        val cpf = intent.getStringExtra("CPF_EXTRA")
        if(cpf != null) {
            manager.getSolicitations(cpf)
        }
    }

    private fun initializeViews() {
        manager = SolicitationsManager(this)
        errorTextView = findViewById(R.id.solicitationsError)
        createSolicitationsButton = findViewById(R.id.solicitationsMainCreateButton)
        backBtn = findViewById(R.id.solicitationsMainBackButton)
        recyclerView = findViewById(R.id.solicitationsMainRecyclerView)
        closeAppButton = findViewById(R.id.closeAppButton)

        backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }

        closeAppButton.setOnClickListener {
            closeApp()
        }

        createSolicitationsButton.setOnClickListener {
            createSolicitation()
        }
    }

    private fun displayBillings() {
        if(solicitations.size > 0){
            val adapter = SolicitationsAdapter(solicitations)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }
        else {
            errorTextView.text = "Nenhuma solicitação encontrada";
        }
    }

    private fun createSolicitation (){
        val cpf = intent.getStringExtra("CPF_EXTRA")

        val intent = Intent(this, CreateSolicitationChooseLineActivity::class.java)

        intent.putExtra("CPF_EXTRA", cpf)

        startActivity(intent)
    }

    override fun onSuccess(solicitations: List<Solicitation>) {
        this.solicitations = solicitations
        displayBillings()
    }

    override fun onFailure(errorMessage: String) {
        errorTextView.text = errorMessage;
    }

    private fun closeApp(){
        finishAffinity()
    }
}