package com.ghifarix.simulasi_uang.screens.investment.detail

sealed class InvestmentDetailState {
    data object Submit : InvestmentDetailState()
    data object SubmitProgress : InvestmentDetailState()
    data object Idle : InvestmentDetailState()
}
