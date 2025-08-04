package com.teleconta.pas.managers

import com.teleconta.pas.api.RetrofitInstance
import com.teleconta.pas.entities.OpenBilling
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class OpenBillingsManager (private val callback: OpenBillingsCallback){

    fun getOpenBillings(cpf: String){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response: Response<List<OpenBilling>> = RetrofitInstance.api.getOpenBillings(cpf)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val billings = response.body()
                        if (billings != null) {
                            callback.onSuccess(billings)
                        }
                    } else {
                        callback.onFailure("Login failed")
                    }
                }
            } catch (e: Exception) {
                // Handle network or other errors
                withContext(Dispatchers.Main) {
                    callback.onFailure("Error: ${e.message}")
                }
            }
        }
    }

    interface OpenBillingsCallback{
        fun onSuccess(billings: List<OpenBilling>)
        fun onFailure(errorMessage: String)
    }
}