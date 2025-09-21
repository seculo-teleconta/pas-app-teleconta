package com.teleconta.pas.entities

data class SolicitationDAO(
    val cpf: String,
    val line: String,
    val idOperator: Long,
    val cpf2: String,
    val type: Long,
    val title: String,
    val description: String
)
