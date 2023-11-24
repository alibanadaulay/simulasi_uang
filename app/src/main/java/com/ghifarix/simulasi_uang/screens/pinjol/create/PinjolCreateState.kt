package com.ghifarix.simulasi_uang.screens.pinjol.create

sealed class PinjolCreateState {
    data object Submit : PinjolCreateState()
    data object SubmitProgress : PinjolCreateState()
    data object Idle : PinjolCreateState()
}