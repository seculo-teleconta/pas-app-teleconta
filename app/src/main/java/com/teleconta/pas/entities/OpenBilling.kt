package com.teleconta.pas.entities

data class OpenBilling (
    val id: Long,
    val idTerminal: String,
    val dateVenc: String,
    val value: Double,
    val status: String,
    val code: String
)