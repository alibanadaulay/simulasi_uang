package com.ghifarix.simulasi_uang.screens.pinjol.model

data class PinjolItem(
    val monthOrDay: String = "0",
    val capital: String = "0.0",
    val interest: String = "0.0",
    val installments: String = "0.0",
    val remainingLoan: String = "0.0"
) {
}