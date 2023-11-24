package com.ghifarix.simulasi_uang.screens.pinjol.model


data class Pinjol(
    val pinjolItems: List<PinjolItem> = ArrayList(),
    val interest: Double = 0.0,
    val loanTime: Int = 0,
    val dp: Double = 0.0,
    val dpAmount: String = "0.0",
    val totalLoan: String = "0.0",
    val loanToPay: String = "0.0",
    val installmentsType: PinjolType = PinjolType.Harian,
    val interestAtPercentage: String = "0.0",
    val interestAmount: String = "0.0"
) {
}