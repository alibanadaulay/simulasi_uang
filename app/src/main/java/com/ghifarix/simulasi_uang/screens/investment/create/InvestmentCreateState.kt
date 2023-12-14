package com.ghifarix.simulasi_uang.screens.investment.create

sealed class InvestmentCreateState {
    data object Submit : InvestmentCreateState()
    data object SubmitProgress : InvestmentCreateState()
    data object Idle : InvestmentCreateState()
}
