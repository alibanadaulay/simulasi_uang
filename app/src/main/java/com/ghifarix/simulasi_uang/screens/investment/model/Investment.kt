package com.ghifarix.simulasi_uang.screens.investment.model

data class Investment(
    val baseInvestment: String = "0.0",
    val totalInvestment: String = "0.0",
    val investmentTime: Int = 0,
    val increaseTime: Int = 0,
    val increaseInvestment: String = "0.0",
    val investmentRate: String = "0.0",
    val tax: String = "0.0",
    val percentageIncrease: String = "0.0",
    val amountIncrease: String = "0.0",
    val investmentItem: List<InvestmentItem> = ArrayList()
)
