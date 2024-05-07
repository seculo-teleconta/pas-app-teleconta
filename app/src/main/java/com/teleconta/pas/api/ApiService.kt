package com.example.teleconta.api

import com.example.teleconta.entities.OpenBilling
import com.example.teleconta.entities.PaidBilling
import com.example.teleconta.entities.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/user/{cpf}")
    suspend fun login(@Path("cpf") cpf: String): Response<User>

    @GET("/billing/open-billings/{cpf}")
    suspend fun getOpenBillings(@Path("cpf") cpf: String): Response<List<OpenBilling>>

    @GET("/billing/paid-billings/{cpf}")
    suspend fun getPaidBillings(@Path("cpf") cpf: String): Response<List<PaidBilling>>
}