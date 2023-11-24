package com.ghifarix.simulasi_uang.screens.pinjol.detail

import com.ghifarix.simulasi_uang.screens.pinjol.model.Pinjol

sealed class PinjolDetailState {
    data object Idle : PinjolDetailState()
    data class LoadPinjolDetails(val pinjol: Pinjol) : PinjolDetailState()
}
