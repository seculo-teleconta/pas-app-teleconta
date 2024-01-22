package com.example.firstapp_tutorial.entities

import java.util.Date

data class OpenBilling (
    val id: Long,
    val idTerminal: String,
    val dateVenc: String,
    val status: String,
    val value: Double
)