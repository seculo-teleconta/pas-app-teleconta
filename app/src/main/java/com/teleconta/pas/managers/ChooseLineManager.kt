package com.teleconta.pas.managers

import com.teleconta.pas.api.RetrofitInstance
import com.teleconta.pas.entities.PaidBilling
import com.teleconta.pas.entities.PhoneLine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class ChooseLineManager (private val callback: ChooseLineManager.ChooseLinesCallBack){

    fun getLines(cpf: String){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response: Response<List<PhoneLine>> = RetrofitInstance.api.getUserPhoneLines(cpf)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val lines = response.body()
                        if (lines != null) {
                            callback.onSuccess(lines)
                        }
                    } else {
                        callback.onFailure("Login failed")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    callback.onFailure("Error: ${e.message}")
                }
            }
        }
    }

    interface ChooseLinesCallBack {
        fun onSuccess(lines: List<PhoneLine>)
        fun onFailure(errorMessage: String)
    }
}