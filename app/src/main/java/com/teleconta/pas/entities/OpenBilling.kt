package com.example.teleconta.entities

data class OpenBilling (
    val id: Long,
    val idTerminal: String,
    val dateVenc: String,
    val status: String,
    val value: Double
)