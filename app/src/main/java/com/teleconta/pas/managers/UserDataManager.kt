package com.teleconta.pas.managers

import com.teleconta.pas.api.RetrofitInstance
import com.teleconta.pas.entities.PaidBilling
import com.teleconta.pas.entities.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class UserDataManager(private val callback: UserDataCallback) {

    fun getUserInfo(cpf: String){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response: Response<UserInfo> = RetrofitInstance.api.getUserInfo(cpf)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val userInfo = response.body()
                        if (userInfo != null) {
                            callback.onSuccess(userInfo)
                        }
                    } else {
                        callback.onFailure("No user info found")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    callback.onFailure("Error: ${e.message}")
                }
            }
        }
    }

    interface UserDataCallback {
        fun onSuccess(user: UserInfo)
        fun onFailure(errorMessage: String)
    }
}