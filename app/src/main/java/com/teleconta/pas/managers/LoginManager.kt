package com.example.teleconta.managers

import com.example.teleconta.api.RetrofitInstance
import com.example.teleconta.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class LoginManager(private val callback: LoginCallback) {

    fun performLogin(cpf: String) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response: Response<User> = RetrofitInstance.api.login(cpf)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val user = response.body()
                        if (user != null) {
                            callback.onLoginSuccess(user)
                        } else {
                            // Handle null user response
                        }
                    } else {
                        callback.onLoginFailure("Login failed")
                    }
                }
            } catch (e: Exception) {
                // Handle network or other errors
                withContext(Dispatchers.Main) {
                    callback.onLoginFailure("Error: ${e.message}")
                }
            }
        }
    }

    interface LoginCallback {
        fun onLoginSuccess(user: User)
        fun onLoginFailure(errorMessage: String)
    }
}