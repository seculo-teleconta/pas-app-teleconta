package com.teleconta.pas.entities

data class Solicitation(
    val id: Long,
    val idOperator: Long,
    val line: String,
    val solicitationType: String,
    val solicitationDate: String,
    val solicitationStatus: String,
    val operator: String
)
