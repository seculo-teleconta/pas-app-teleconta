package com.teleconta.pas.managers

import com.teleconta.pas.api.RetrofitInstance
import com.teleconta.pas.entities.PaidBilling
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class PaidBillingsManager (private val callback: PaidBillingsCallBack){

    fun getPaidBillings(cpf: String){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response: Response<List<PaidBilling>> = RetrofitInstance.api.getPaidBillings(cpf)
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
                withContext(Dispatchers.Main) {
                    callback.onFailure("Error: ${e.message}")
                }
            }
        }
    }

    interface PaidBillingsCallBack {
        fun onSuccess(billings: List<PaidBilling>)
        fun onFailure(errorMessage: String)
    }
}