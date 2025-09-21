package com.teleconta.pas.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teleconta.pas.R
import com.teleconta.pas.adapters.CreateSolicitationChooseLinesAdapter
import com.teleconta.pas.entities.PhoneLine
import com.teleconta.pas.managers.ChooseLineManager

class CreateSolicitationChooseLineActivity : AppCompatActivity(), ChooseLineManager.ChooseLinesCallBack, CreateSolicitationChooseLinesAdapter.ChooseLinesCallback  {

    private lateinit var chooseLineManager: ChooseLineManager
    private lateinit var lines: List<PhoneLine>
    private lateinit var recyclerView: RecyclerView
    private lateinit var backBtn: Button
    private lateinit var errorText: TextView
    private lateinit var closeAppButton: Button
    private lateinit var cpf: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_solicitation_choose_line)

        initializeViews()
        cpf = intent.getStringExtra("CPF_EXTRA").toString()
        Log.d("Main", "cpf: " + cpf)
        if(cpf != null){
            chooseLineManager.getLines(cpf)
        }
    }

    private fun initializeViews() {
        chooseLineManager = ChooseLineManager(this)
        closeAppButton = findViewById(R.id.closeAppButton)
        errorText = findViewById(R.id.createSolicitationChooseLineError)
        backBtn = findViewById(R.id.backBtnCreateSolicitationChooseLines)
        recyclerView = findViewById(R.id.createSolicitationChooseLine)

        backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }

        closeAppButton.setOnClickListener {
            closeApp()
        }
    }

    private fun displayLines(){
        if(lines.size > 0) {
            val adapter = CreateSolicitationChooseLinesAdapter(lines, this)

            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }
        else {
            errorText.text = "Nenhuma linha encontrada"
        }
    }

    override fun onSuccess(lines: List<PhoneLine>) {
        this.lines = lines
        displayLines()
    }

    override fun onFailure(errorMessage: String) {
    }

    override fun onSelectLine(line: String, idOperator: Long) {
        val intent = Intent(this, CreateSolicitationChooseTypeActivity::class.java)

        Log.d("Main", "cpf: " + cpf)
        Log.d("Main", "IdOperator: " + idOperator)
        Log.d("Main", "line: " + line)

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