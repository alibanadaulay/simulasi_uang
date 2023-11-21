package com.ghifarix.simulasi_uang.screens.kpr.model

data class Kpr(
    val totalCapital: String = "0.0",
    val totalInterest: String = "0.0",
    val totalInstallments: String = "0.0",
    val kprItems: List<KprItem>,
    val interest: Double = 0.0,
    val years: Int = 0,
    val totalLoan:String = "0.0",
    val installmentsType:String = "Flat"
)
