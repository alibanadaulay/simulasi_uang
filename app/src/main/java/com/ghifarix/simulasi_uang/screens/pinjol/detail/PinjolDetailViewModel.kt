package com.ghifarix.simulasi_uang.screens.pinjol.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghifarix.simulasi_uang.SingletonModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PinjolDetailViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow<PinjolDetailState>(PinjolDetailState.Idle)
    val state: StateFlow<PinjolDetailState> = _state

    fun getPinjol() {
        viewModelScope.launch {
            val pinjol = SingletonModel.getInstance().getPinjol()
            if (pinjol != null) {
                _state.value = PinjolDetailState.LoadPinjolDetails(
                    pinjol = pinjol
                )
            }
        }
    }
}