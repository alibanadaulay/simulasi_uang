package com.ghifarix.simulasi_uang.screens.kpr.input

sealed class KprInputState{
    data object Submit:KprInputState()
    data object SubmitProgress:KprInputState()
    data object Idle:KprInputState()
}
