package com.example.teleconta.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.teleconta.managers.LoginManager
import com.example.teleconta.R
import com.example.teleconta.entities.User

class MainActivity : AppCompatActivity(), LoginManager.LoginCallback {

    private lateinit var loginInput: EditText
    private lateinit var loginButton: Button
    private lateinit var welcomeTextView: TextView
    private lateinit var loginManager: LoginManager
    private lateinit var closeAppButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        loginInput = findViewById(R.id.loginInput)
        loginButton = findViewById(R.id.loginButton)
        loginButton.isEnabled = false
        welcomeTextView = findViewById(R.id.textHello)
        closeAppButton = findViewById(R.id.closeAppButton)

        loginManager = LoginManager(this)

        loginInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Format the CPF as the user types
                val formattedCpf = formatCpf(s.toString())
                loginInput.removeTextChangedListener(this)
                loginInput.setText(formattedCpf)
                loginInput.setSelection(formattedCpf.length)
                loginInput.addTextChangedListener(this)

                loginButton.isEnabled = formattedCpf.length == 14 || formattedCpf.length == 18
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        loginButton.setOnClickListener {
            val cpf = loginInput.text.toString().replace("[./-]".toRegex(), "")
            if (cpf.isNotBlank()) {
                loginButton.isEnabled = false
                welcomeTextView.text = ""
                loginManager.performLogin(cpf)
            } else {
                loginButton.isEnabled = false
                welcomeTextView.text = "Digite o cpf ou cnpj"
            }
        }

        closeAppButton.setOnClickListener {
            closeApp()
        }
    }

    private fun formatCpf(cpf: String): String {
        val cleanedCpf = cpf.replace("[./-]".toRegex(), "")

        if(cleanedCpf.length > 3){
            if(cleanedCpf.length < 7){
                return cleanedCpf.substring(0,3) + "." + cleanedCpf.substring(3, cleanedCpf.length)
            }
            else if(cleanedCpf.length < 10){
                return cleanedCpf.substring(0,3) + "." + cleanedCpf.substring(3, 6) + "." +
                        cleanedCpf.substring(6, cleanedCpf.length)
            }
            else if (cleanedCpf.length < 12){
                return cleanedCpf.substring(0,3) + "." + cleanedCpf.substring(3, 6) + "." +
                        cleanedCpf.substring(6, 9) + "-" + cleanedCpf.substring(9,cleanedCpf.length)
            }
            else if(cleanedCpf.length < 13){
                return cleanedCpf.substring(0,2) + "." + cleanedCpf.substring(2,5) + "." +
                        cleanedCpf.substring(5,8) + "/" + cleanedCpf.substring(8, 12)
            }
            else if(cleanedCpf.length < 15){
                return cleanedCpf.substring(0,2) + "." + cleanedCpf.substring(2,5) + "." +
                        cleanedCpf.substring(5,8) + "/" + cleanedCpf.substring(8, 12) + "-" +
                        cleanedCpf.substring(12, cleanedCpf.length)
            }
        }
        return cleanedCpf
    }

    // cpf
    // 0 9 7 9 8 4 4 8 6 0 0
    // 0 1 2 3 4 5 6 7 8 9 0
    // length = 11 (+1)

    // cnpj
    // 0 7 2 8 2 2 7 8 0 0 0 1 4 8
    // 0 1 2 3 4 5 6 7 8 9 0 1 2 3
    // length = 14 (+1)

    override fun onLoginSuccess(user: User) {
        val intent = Intent(this, HomeActivity::class.java)

        intent.putExtra("CPF_DATA", user.cpf)
        intent.putExtra("NAME_DATA", user.name)
        intent.putExtra("NICK_DATA", user.nick)

        startActivity(intent)

        finish()
    }

    override fun onLoginFailure(errorMessage: String) {
        welcomeTextView.text = "Cpf/Cnpj não encontrado"
        //Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun closeApp(){
        finishAffinity()
    }
}
