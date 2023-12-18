package com.ghifarix.simulasi_uang.screens.investment.detail

import com.ghifarix.simulasi_uang.screens.investment.model.Investment

sealed class InvestmentDetailState {
    data class Load(val investment: Investment) : InvestmentDetailState()
    data object Idle : InvestmentDetailState()
}
