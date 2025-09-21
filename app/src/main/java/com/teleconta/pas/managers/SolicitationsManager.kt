package com.teleconta.pas.managers

import com.teleconta.pas.api.RetrofitInstance
import com.teleconta.pas.entities.PhoneLine
import com.teleconta.pas.entities.Solicitation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class SolicitationsManager (private val callback: SolicitationsManager.SolicitationsCallBack) {

    fun getSolicitations(cpf: String){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response: Response<List<Solicitation>> = RetrofitInstance.api.getSolicitations(cpf)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val solicitations = response.body()
                        if (solicitations != null) {
                            callback.onSuccess(solicitations)
                        }
                    } else {
                        callback.onFailure("Solicitations fetch error")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    callback.onFailure("Error: ${e.message}")
                }
            }
        }
    }

    interface SolicitationsCallBack {
        fun onSuccess(solicitations: List<Solicitation>)
        fun onFailure(errorMessage: String)
    }
}