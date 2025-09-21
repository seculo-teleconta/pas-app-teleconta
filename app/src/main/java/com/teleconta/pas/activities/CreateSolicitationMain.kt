package com.teleconta.pas.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.teleconta.pas.managers.CreateSolicitationManager
import com.teleconta.pas.R
import com.teleconta.pas.entities.SolicitationDAO

class CreateSolicitationMain : AppCompatActivity(), CreateSolicitationManager.CreateSolicitationCallBack {

    private lateinit var titleInput: EditText
    private lateinit var descriptionInput: EditText
    private lateinit var submitButton: Button
    private lateinit var backButton: Button
    private lateinit var closeAppButton: Button
    private lateinit var manager: CreateSolicitationManager
    private lateinit var titleInputText: String
    private lateinit var descriptionInputText: String
    private lateinit var cpf: String
    private lateinit var line: String
    private var idOperator: Long = 0
    private var type: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_solicitation_main)

        titleInput = findViewById(R.id.createSolicitationTitleInput)
        descriptionInput = findViewById(R.id.createSolicitationDescriptionInput)
        submitButton = findViewById(R.id.createSolicitationSubmitButton)
        backButton = findViewById(R.id.backBtnCreateSolicitationMain)
        closeAppButton = findViewById(R.id.closeAppButton)

        manager = CreateSolicitationManager(this)

        cpf = intent.getStringExtra("CPF_EXTRA").toString()
        line = intent.getStringExtra("LINE_EXTRA").toString()
        idOperator = intent.getLongExtra("ID_OPERATOR", 0)
        type = intent.getLongExtra("TYPE_ID", 0)

        submitButton.setOnClickListener {
            submitSolicitation()
        }

        titleInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                titleInputText = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        descriptionInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                descriptionInputText = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }

        closeAppButton.setOnClickListener {
            closeApp()
        }
    }

    private fun submitSolicitation(){
        val intent = Intent(this, SolicitationMainActivity::class.java)

        Log.d("Main", "Está no main")

        //val line = intent.getStringExtra("LINE_EXTRA")
        //val cpf = intent.getStringExtra("CPF_EXTRA")
        //val idOperator: Long = intent.getLongExtra("ID_OPERATOR", 0)
        //val type: Long = intent.getLongExtra("TYPE_ID", 0)
        val title = titleInput.text.toString()
        val description = descriptionInput.text.toString()

        Log.d("Main", "Está no criando")
        Log.d("Main", "cpf: " + cpf)
        Log.d("Main", "IdOperator: " + idOperator)
        Log.d("Main", "Type: " + type)
        Log.d("Main", "title: " + title)
        Log.d("Main", "description: " + description)
        Log.d("Main", "line: " + line)

        if(cpf != "" && line != "") {
            val newSolicitation = SolicitationDAO(
                cpf = cpf,
                line = line,
                idOperator = idOperator,
                cpf2 = cpf,
                type = type,
                title = title,
                description = description
            )

            Log.d("Main", "NewSolicitation idOperator: " + newSolicitation.idOperator)

            Toast.makeText(this, newSolicitation.cpf, Toast.LENGTH_SHORT).show()
            manager.createSolicitation(newSolicitation)

            intent.putExtra("CPF_EXTRA", cpf)
            onBackPressedDispatcher.onBackPressed()
            finish()
        }
    }


    override fun onSuccess(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onFailure(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun closeApp(){
        finishAffinity()
    }
}