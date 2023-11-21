package com.ghifarix.simulasi_uang.screens.kpr.model

import androidx.compose.ui.unit.Dp

data class Kpr(
    val kprItems: List<KprItem> = ArrayList(),
    val interest: Double = 0.0,
    val years: Int = 0,
    val dp: Double = 0.0,
    val dpAmount:String = "0.0",
    val totalLoan:String = "0.0",
    val loanToPay:String = "0.0",
    val installmentsType:KprType = KprType.ANUITAS,
    val interestAtPercentage:String = "0.0",
    val interestAmount:String = "0.0"
)
