package com.teleconta.pas.managers

import com.teleconta.pas.api.RetrofitInstance
import com.teleconta.pas.entities.CreateSolicitationResponse
import android.util.Log
import com.teleconta.pas.entities.SolicitationDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class CreateSolicitationManager (private val callback: CreateSolicitationCallBack) {

    fun createSolicitation(solicitation: SolicitationDAO){
        GlobalScope.launch(Dispatchers.IO){
            try{
                Log.d("Manager", "Está no manager")
                val response: Response<CreateSolicitationResponse> = RetrofitInstance.api.createSolicitation(solicitation);
                Log.d("Manager", "fez o request")
                Log.d("Manager", "Message: " + (response.body()?.message ?: "Nothing"))
                withContext(Dispatchers.Main){
                    if(response.isSuccessful){
                        Log.d("Manager", "Success")
                        val body = response.body();
                        if(body != null) {
                            val message = body.message;
                            callback.onSuccess(message);
                        }
                    }
                }
            }
            catch (e: Exception){
                withContext(Dispatchers.Main) {
                    callback.onFailure("Error: ${e.message}")
                }
            }
        }
    }

    interface CreateSolicitationCallBack {
        fun onSuccess(message: String)
        fun onFailure(errorMessage: String)
    }
}