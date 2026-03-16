package com.teleconta.pas.managers

import com.teleconta.pas.api.RetrofitInstance
import com.teleconta.pas.entities.ResetPasswordResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class ResetPasswordManager(private val callback: ResetPasswordCallback) {

    fun requestNewPassword(cpf: String) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response: Response<ResetPasswordResponse> = RetrofitInstance.api.requestNewPassword(cpf)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            callback.onResetSuccess(body.message)
                        } else {
                            callback.onResetFailure("Erro ao processar resposta.")
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        val errorMessage = try {
                            val gson = com.google.gson.Gson()
                            val errorResponse = gson.fromJson(errorBody, com.teleconta.pas.entities.ErrorResponse::class.java)
                            errorResponse.detail
                        } catch (e: Exception) {
                            "Erro ao solicitar nova senha."
                        }
                        callback.onResetFailure(errorMessage)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    callback.onResetFailure("Erro de conexão: ${e.message}")
                }
            }
        }
    }

    interface ResetPasswordCallback {
        fun onResetSuccess(message: String)
        fun onResetFailure(errorMessage: String)
    }
}
