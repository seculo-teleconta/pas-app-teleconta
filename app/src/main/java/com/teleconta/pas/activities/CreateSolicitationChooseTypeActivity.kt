package com.teleconta.pas.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teleconta.pas.R
import com.teleconta.pas.adapters.SolicitationsTypesAdapter
import com.teleconta.pas.entities.SolicitationType
import com.teleconta.pas.managers.SolicitationsTypeManager

class CreateSolicitationChooseTypeActivity : AppCompatActivity(), SolicitationsTypeManager.SolicitaionsCallBack,
    SolicitationsTypesAdapter.ChooseTypeCallback {

    private lateinit var manager: SolicitationsTypeManager
    private lateinit var types: List<SolicitationType>
    private lateinit var recyclerView: RecyclerView
    private lateinit var backBtn: Button
    private lateinit var errorText: TextView
    private lateinit var closeAppButton: Button
    private lateinit var cpf: String
    private lateinit var line: String
    private var idOperator: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_solicitation_choose_type)

        initializeViews()

        cpf = intent.getStringExtra("CPF_EXTRA").toString()
        line = intent.getStringExtra("LINE_EXTRA").toString()
        idOperator = intent.getLongExtra("ID_OPERATOR", 0)

        manager.getSolicitationsTypes()
    }

    private fun initializeViews() {
        manager = SolicitationsTypeManager(this)
        closeAppButton = findViewById(R.id.closeAppButton)
        backBtn = findViewById(R.id.backBtnCreateSolicitationChooseTypes)
        recyclerView = findViewById(R.id.solicitationsTypeRecyclerView)

        backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }

        closeAppButton.setOnClickListener {
            closeApp()
        }
    }

    override fun onSuccess(types: List<SolicitationType>) {
        this.types = types
        displayTypes()
    }

    private fun displayTypes(){
        if(types.size > 0) {
            val adapter = SolicitationsTypesAdapter(types, this)

            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }
        else {
            errorText.text = "Nenhum tipo encontrado"
        }
    }

    override fun onFailure(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onSelectLine(id: Long) {
        val intent = Intent(this, CreateSolicitationMain::class.java)

        //val line = intent.getStringExtra("LINE_EXTRA")
        //val cpf = intent.getStringExtra("CPF_EXTRA")
        //val idOperator: Long = intent.getLongExtra("ID_OPERATOR", 0)

        Log.d("Main", "cpf: " + cpf)
        Log.d("Main", "IdOperator: " + idOperator)
        Log.d("Main", "line: " + line)
        Log.d("Main", "type: " + id)

        intent.putExtra("TYPE_ID", id)
        intent.putExtra("LINE_EXTRA", line)
        intent.putExtra("ID_OPERATOR", idOperator)
        intent.putExtra("CPF_EXTRA", cpf)

        startActivity(intent)
        onBackPressedDispatcher.onBackPressed()
        finish()
    }

    private fun closeApp(){
        finishAffinity()
    }
}