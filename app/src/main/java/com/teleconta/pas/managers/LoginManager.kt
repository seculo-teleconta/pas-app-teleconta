package com.teleconta.pas.managers

import com.teleconta.pas.api.RetrofitInstance
import com.teleconta.pas.entities.LoginDAO
import com.teleconta.pas.entities.User
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

    fun performLoginWithPassword(cpf: String, password: String) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response: Response<User> = RetrofitInstance.api.loginPassword(LoginDAO(cpf, password))
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val user = response.body()
                        if (user != null) {
                            callback.onLoginSuccess(user)
                        } else {
                            callback.onLoginFailure("User not found")
                        }
                    } else {
                        // Parse the error JSON
                        val errorBody = response.errorBody()?.string()
                        val errorMessage = try {
                            val gson = com.google.gson.Gson()
                            val errorResponse = gson.fromJson(errorBody, com.teleconta.pas.entities.ErrorResponse::class.java)
                            errorResponse.detail
                        } catch (e: Exception) {
                            "Login failed"
                        }
                        callback.onLoginFailure(errorMessage)
                    }
                }
            } catch (e: Exception) {
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