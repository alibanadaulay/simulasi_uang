package com.ghifarix.simulasi_uang.screens.pinjol.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghifarix.simulasi_uang.extensions.convertToInt
import com.ghifarix.simulasi_uang.screens.pinjol.model.PinjolType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PinjolCreateViewModel @Inject constructor() : ViewModel() {
    private var _serviceCostInterest: Double = 0.0
    private var _interest: Double = 0.0
    private var _baseLoan: Double = 0.0
    private var _loanTime: Int = 0
    private val _state: MutableStateFlow<PinjolCreateState> =
        MutableStateFlow(PinjolCreateState.Idle)
    val state: StateFlow<PinjolCreateState> = _state
    private val _serviceCost: MutableStateFlow<String> = MutableStateFlow("0.0")
    val serviceCost: StateFlow<String> = _serviceCost
    private val _pinjolType: MutableStateFlow<PinjolType> = MutableStateFlow(PinjolType.Harian)
    val pinjolType: StateFlow<PinjolType> = _pinjolType

    fun updatePinjolType(pinjolType: PinjolType) {
        viewModelScope.launch {
            _pinjolType.value = pinjolType
        }
    }

    fun updateBaseLoan(loan: String) {
        viewModelScope.launch {
            _baseLoan = if (loan.isBlank()) {
                0.0
            } else {
                loan.replace(",", "").toDouble()
            }
        }
    }

    fun updateInterest(interest: String) {
        viewModelScope.launch {
            _interest = if (interest.isBlank()) {
                0.0
            } else {
                interest.toDouble()
            }
        }
    }

    fun updateServiceCostInterest(costServiceInterest: String) {
        viewModelScope.launch {
            _interest = if (costServiceInterest.isBlank()) {
                0.0
            } else {
                costServiceInterest.toDouble()
            }
        }
    }

    fun updateLoanTime(loan: String) {
        viewModelScope.launch {
            _loanTime = if (loan.isBlank()) {
                0
            } else {
                loan.convertToInt()
            }
        }
    }

    fun submit() {
        viewModelScope.launch {
            when (_pinjolType.value) {
                PinjolType.Harian -> {

                }

                PinjolType.Bulanan -> {

                }
            }
        }
    }

    private fun calculateDays() {

    }

    private fun calculateMonths() {

    }
}