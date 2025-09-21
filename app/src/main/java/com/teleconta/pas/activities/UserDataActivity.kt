package com.teleconta.pas.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.teleconta.pas.R
import com.teleconta.pas.entities.UserInfo
import com.teleconta.pas.managers.PaidBillingsManager
import com.teleconta.pas.managers.UserDataManager

class UserDataActivity: AppCompatActivity(), UserDataManager.UserDataCallback {

    private lateinit var userDataManager: UserDataManager
    private lateinit var userInfo: UserInfo
    private lateinit var backBtn: Button
    private lateinit var closeAppButton: Button
    private lateinit var name: TextView
    private lateinit var email: TextView
    private lateinit var cpf: TextView
    private lateinit var idTerminal: TextView
    private lateinit var address: TextView
    private lateinit var cep: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_data_main)

        initializeViews()
        val cpf = intent.getStringExtra("CPF_EXTRA")
        if(cpf != null) {
            userDataManager.getUserInfo(cpf)
        }
    }

    private fun initializeViews() {
        userDataManager = UserDataManager(this)
        backBtn = findViewById(R.id.backBtnUserData)
        closeAppButton = findViewById(R.id.closeAppButton)
        name = findViewById(R.id.userDataName)
        email = findViewById(R.id.userDataEmail)
        cpf = findViewById(R.id.userDataCpf)
        idTerminal = findViewById(R.id.userDataIdTerminal)
        address = findViewById(R.id.userDataAddress)
        cep = findViewById(R.id.userDataCep)

        backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }

        closeAppButton.setOnClickListener {
            closeApp()
        }
    }


    override fun onSuccess(user: UserInfo) {
        this.userInfo = user
        displayUserInfo()
    }

    override fun onFailure(errorMessage: String) {
        // error
    }

    private fun displayUserInfo(){
        name.text = userInfo.name
        email.text = userInfo.user
        idTerminal.text = userInfo.idTerminal
        cpf.text = userInfo.cpf
        address.text = userInfo.address
        cep.text = userInfo.cep
    }

    private fun closeApp(){
        finishAffinity()
    }
}