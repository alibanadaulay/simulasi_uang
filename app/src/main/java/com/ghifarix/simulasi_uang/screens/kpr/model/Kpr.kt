package com.ghifarix.simulasi_uang.screens.kpr.model

data class Kpr(
    val totalCapital: Double,
    val totalInterest: Double,
    val installments: Double,
    val kprItems: List<KprItem>,
    val interest: Int,
    val years: Int,
)
