package com.ghifarix.simulasi_uang.screens.kpr.detail

import com.ghifarix.simulasi_uang.screens.kpr.model.Kpr

sealed class KprDetailState {
    data object Idle:KprDetailState()
    data class LoadKprDetails(val kpr: Kpr):KprDetailState()
}
