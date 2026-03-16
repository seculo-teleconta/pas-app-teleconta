package com.teleconta.pas.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.teleconta.pas.R
import com.teleconta.pas.managers.ResetPasswordManager

class ResetPasswordActivity : AppCompatActivity(), ResetPasswordManager.ResetPasswordCallback {

    private lateinit var resetInput: EditText
    private lateinit var sendButton: Button
    private lateinit var cancelButton: Button
    private lateinit var resetMessage: TextView
    private lateinit var resetPasswordManager: ResetPasswordManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        resetInput = findViewById(R.id.resetInput)
        sendButton = findViewById(R.id.sendButton)
        cancelButton = findViewById(R.id.cancelButton)
        resetMessage = findViewById(R.id.resetMessage)

        sendButton.isEnabled = false
        resetPasswordManager = ResetPasswordManager(this)

        resetInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val formatted = formatCpfCnpj(s.toString())
                resetInput.removeTextChangedListener(this)
                resetInput.setText(formatted)
                resetInput.setSelection(formatted.length)
                resetInput.addTextChangedListener(this)

                sendButton.isEnabled = formatted.length == 14 || formatted.length == 18
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        sendButton.setOnClickListener {
            val cpf = resetInput.text.toString().replace("[./-]".toRegex(), "")
            if (cpf.isNotBlank()) {
                setLoading(true)
                resetMessage.text = ""
                resetPasswordManager.requestNewPassword(cpf)
            }
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun setLoading(loading: Boolean) {
        sendButton.isEnabled = !loading
        cancelButton.isEnabled = !loading
        resetInput.isEnabled = !loading
    }

    private fun formatCpfCnpj(input: String): String {
        val cleaned = input.replace("[./-]".toRegex(), "")

        if (cleaned.length > 3) {
            if (cleaned.length <= 11) { // CPF path
                if (cleaned.length < 7) {
                    return cleaned.substring(0, 3) + "." + cleaned.substring(3)
                } else if (cleaned.length < 10) {
                    return cleaned.substring(0, 3) + "." + cleaned.substring(3, 6) + "." + cleaned.substring(6)
                } else {
                    return cleaned.substring(0, 3) + "." + cleaned.substring(3, 6) + "." + cleaned.substring(6, 9) + "-" + cleaned.substring(9)
                }
            } else { // CNPJ path or transitioned
                if (cleaned.length < 13) {
                    return cleaned.substring(0, 2) + "." + cleaned.substring(2, 5) + "." +
                            cleaned.substring(5, 8) + "/" + cleaned.substring(8)
                } else {
                    return cleaned.substring(0, 2) + "." + cleaned.substring(2, 5) + "." +
                            cleaned.substring(5, 8) + "/" + cleaned.substring(8, 12) + "-" +
                            cleaned.substring(12)
                }
            }
        }
        return cleaned
    }

    override fun onResetSuccess(message: String) {
        setLoading(false)
        resetMessage.text = message
        resetMessage.setTextColor(ContextCompat.getColor(this, R.color.green))
    }

    override fun onResetFailure(errorMessage: String) {
        setLoading(false)
        resetMessage.text = errorMessage
        resetMessage.setTextColor(ContextCompat.getColor(this, R.color.red))
    }
}
