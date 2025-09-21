package com.teleconta.pas.managers

import com.teleconta.pas.api.RetrofitInstance
import com.teleconta.pas.entities.SolicitationType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class SolicitationsTypeManager (private val callback: SolicitationsTypeManager.SolicitaionsCallBack){

    fun getSolicitationsTypes(){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response: Response<List<SolicitationType>> = RetrofitInstance.api.getSolicitationsTypes()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val types = response.body()
                        if (types != null) {
                            callback.onSuccess(types)
                        }
                    } else {
                        callback.onFailure("Error to fetch solicitations types")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    callback.onFailure("Error: ${e.message}")
                }
            }
        }
    }

    interface SolicitaionsCallBack {
        fun onSuccess(types: List<SolicitationType>)
        fun onFailure(errorMessage: String)
    }
}