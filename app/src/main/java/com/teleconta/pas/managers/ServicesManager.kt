package com.teleconta.pas.managers

import com.teleconta.pas.api.RetrofitInstance
import com.teleconta.pas.entities.PhoneServices
import com.teleconta.pas.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class ServicesManager(private val callback: ServicesManagerCallback) {

    fun getServices(line: String) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response: Response<PhoneServices> = RetrofitInstance.api.getUserServices(line)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val services = response.body()
                        if (services != null) {
                            callback.onSuccess(services)
                        } else {
                            // Handle null user response
                        }
                    } else {
                        // ihoihreio
                    }
                }
            } catch (e: Exception) {
                // Handle network or other errors
                withContext(Dispatchers.Main) {
                }
            }
        }
    }

    interface ServicesManagerCallback{
        fun onSuccess(phoneServices: PhoneServices)
    }
}