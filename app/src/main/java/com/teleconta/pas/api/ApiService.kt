package com.teleconta.pas.api

import com.teleconta.pas.entities.CreateSolicitationResponse
import com.teleconta.pas.entities.OpenBilling
import com.teleconta.pas.entities.PaidBilling
import com.teleconta.pas.entities.PhoneLine
import com.teleconta.pas.entities.PhoneServices
import com.teleconta.pas.entities.Solicitation
import com.teleconta.pas.entities.SolicitationDAO
import com.teleconta.pas.entities.SolicitationType
import com.teleconta.pas.entities.User
import com.teleconta.pas.entities.UserInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("/login/{cpf}")
    suspend fun login(@Path("cpf") cpf: String): Response<User>

    @GET("/open-billings/{cpf}")
    suspend fun getOpenBillings(@Path("cpf") cpf: String): Response<List<OpenBilling>>

    @GET("/paid-billings/{cpf}")
    suspend fun getPaidBillings(@Path("cpf") cpf: String): Response<List<PaidBilling>>

    @GET("/user-info/{cpf}")
    suspend fun getUserInfo(@Path("cpf") cpf: String): Response<UserInfo>

    @GET("/user-phones/{cpf}")
    suspend fun getUserPhoneLines(@Path("cpf") cpf: String): Response<List<PhoneLine>>

    @GET("/internet-use/{line}")
    suspend fun getUserServices(@Path("line") line: String): Response<PhoneServices>

    @GET("/solicitations/{cpf}")
    suspend fun getSolicitations(@Path("cpf") cpf: String): Response<List<Solicitation>>

    @GET("/solicitations-type")
    suspend fun getSolicitationsTypes(): Response<List<SolicitationType>>

    @POST("/create-solicitation")
    suspend fun createSolicitation(@Body solicitationDAO: SolicitationDAO): Response<CreateSolicitationResponse>
}