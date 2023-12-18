package com.ghifarix.simulasi_uang.screens.investment.model

data class Investment(
    val baseInvestment: String,
    val investmentTime: Int,
    val investmentRate: String,
    val tax: String,
    val investmentItem: List<InvestmentItem> = ArrayList()
)
