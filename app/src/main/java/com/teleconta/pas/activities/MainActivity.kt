package com.teleconta.pas.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.teleconta.pas.R
import com.teleconta.pas.entities.User
import com.teleconta.pas.managers.LoginManager

class MainActivity : AppCompatActivity(), LoginManager.LoginCallback {

    private lateinit var loginInput: EditText
    private lateinit var loginInputPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var showPasswordButton: Button
    private lateinit var welcomeTextView: TextView
    private lateinit var passwordErrorText: TextView
    private lateinit var loginManager: LoginManager
    private lateinit var closeAppButton: Button
    private lateinit var forgotPasswordButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        loginInput = findViewById(R.id.loginInput)
        loginInputPassword = findViewById(R.id.loginInputPassword)
        loginButton = findViewById(R.id.loginButton)
        showPasswordButton = findViewById(R.id.showPassword)
        loginButton.isEnabled = false
        welcomeTextView = findViewById(R.id.textHello)
        passwordErrorText = findViewById(R.id.passwordError)
        closeAppButton = findViewById(R.id.closeAppButton)
        forgotPasswordButton = findViewById(R.id.forgotPasswordButton)

        showAd()

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
            val password = loginInputPassword.text.toString()

            if (cpf.isNotBlank() && password.isNotBlank()) {
                loginButton.isEnabled = false
                welcomeTextView.text = ""
                passwordErrorText.text = ""  // clear previous error
                loginManager.performLoginWithPassword(cpf, password)
            } else {
                loginButton.isEnabled = false
                if (cpf.isBlank()) {
                    welcomeTextView.text = "Digite o CPF ou CNPJ"
                }
                if (password.isBlank()) {
                    passwordErrorText.text = "Digite a senha"
                }
            }
        }

        var isPasswordVisible = false

        showPasswordButton.setOnClickListener {
            if (isPasswordVisible) {
                // Hide password
                loginInputPassword.inputType =
                    android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
                showPasswordButton.text = "Mostrar senha"
            } else {
                // Show password
                loginInputPassword.inputType =
                    android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                showPasswordButton.text = "Ocultar senha"
            }

            // Keep cursor at the end
            loginInputPassword.setSelection(loginInputPassword.text.length)

            isPasswordVisible = !isPasswordVisible
        }

        closeAppButton.setOnClickListener {
            closeApp()
        }

        forgotPasswordButton.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showAd(){
        val adFragment = AdFragment()
        adFragment.show(supportFragmentManager, "AdFragment")
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

    override fun onLoginSuccess(user: User) {
        val intent = Intent(this, HomeActivity::class.java)

        intent.putExtra("CPF_DATA", user.cpf)
        intent.putExtra("NAME_DATA", user.name)
        intent.putExtra("NICK_DATA", user.nick)

        startActivity(intent)

        finish()
    }

    override fun onLoginFailure(errorMessage: String) {
        // If API says it's about password, show in passwordErrorText
        if (errorMessage.contains("Senha", ignoreCase = true)) {
            passwordErrorText.text = errorMessage
        } else {
            welcomeTextView.text = errorMessage
        }
        loginButton.isEnabled = true
    }


    private fun closeApp(){
        finishAffinity()
    }
}
