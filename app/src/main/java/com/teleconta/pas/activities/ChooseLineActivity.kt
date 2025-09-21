package com.teleconta.pas.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teleconta.pas.R
import com.teleconta.pas.adapters.ChooseLinesAdapter
import com.teleconta.pas.entities.PhoneLine
import com.teleconta.pas.managers.ChooseLineManager

class ChooseLineActivity : AppCompatActivity(), ChooseLineManager.ChooseLinesCallBack, ChooseLinesAdapter.ChooseLinesCallback {

    private lateinit var chooseLineManager: ChooseLineManager
    private lateinit var lines: List<PhoneLine>
    private lateinit var recyclerView: RecyclerView
    private lateinit var backBtn: Button
    private lateinit var errorText: TextView
    private lateinit var closeAppButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choose_line_main)

        initializeViews()
        val cpf = intent.getStringExtra("CPF_EXTRA")
        if(cpf != null){
            chooseLineManager.getLines(cpf)
        }
    }

    private fun initializeViews() {
        chooseLineManager = ChooseLineManager(this)
        closeAppButton = findViewById(R.id.closeAppButton)
        backBtn = findViewById(R.id.backBtnChooseLines)
        recyclerView = findViewById(R.id.chooseLineList)
        errorText = findViewById(R.id.errorChooseline)

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
            val adapter = ChooseLinesAdapter(lines, this)

            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }
        else {
            errorText.text = "Nenhuma linha encontrada"
        }
    }

    private fun closeApp(){
        finishAffinity()
    }

    override fun onSuccess(lines: List<PhoneLine>) {
        this.lines = lines
        displayLines()
    }

    override fun onFailure(errorMessage: String) {
        // error
    }

    override fun onSelectLine(line: String, idOperator: Long) {
        val intent = Intent(this, ViewServicesActivity::class.java)

        intent.putExtra("LINE_EXTRA", line)

        startActivity(intent)
    }
}