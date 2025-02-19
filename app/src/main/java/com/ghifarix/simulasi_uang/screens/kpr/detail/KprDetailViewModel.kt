package com.ghifarix.simulasi_uang.screens.kpr.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghifarix.simulasi_uang.SingletonModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KprDetailViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow<KprDetailState>(KprDetailState.Idle)
    val state: StateFlow<KprDetailState> = _state

    fun getKpr() {
        viewModelScope.launch {
            val kpr = SingletonModel.getInstance().getKpr()
            if (kpr != null) {
                _state.value = KprDetailState.LoadKprDetails(
                    kpr = kpr
                )
            }
        }
    }
}