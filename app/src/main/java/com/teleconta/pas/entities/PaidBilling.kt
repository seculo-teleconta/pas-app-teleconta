package com.teleconta.pas.entities

data class PaidBilling(
    val ourNumber: Long,
    val idTerminal: String,
    val vencDate: String,
    val value: Double,
    val status: String,
    val dataQuit: String,
    val paidValue: Double,
    val feesValue: Double
)
