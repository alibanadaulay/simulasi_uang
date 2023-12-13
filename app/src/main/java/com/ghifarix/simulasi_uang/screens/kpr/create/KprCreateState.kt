package com.ghifarix.simulasi_uang.screens.kpr.create

sealed class KprCreateState {
    data object Submit : KprCreateState()
    data object SubmitProgress : KprCreateState()
    data object Idle : KprCreateState()
}
