package com.ghifarix.simulasi_uang.screens.pinjol.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghifarix.simulasi_uang.screens.pinjol.model.PinjolType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PinjolCreateViewModel @Inject constructor() : ViewModel() {
    private var _pinjolType = PinjolType.Harian
    private var _serviceCost: String = "0.0"
    private var _serviceCostInterest: Double = 0.0
    private var _interest: Double = 0.0
    private var _baseLoan: Double = 0.0

    fun updatePinjolType(pinjolType: PinjolType) {
        viewModelScope.launch {
            _pinjolType = pinjolType
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

    fun submit() {
        when (_pinjolType) {
            PinjolType.Harian -> {

            }

            PinjolType.Bulanan -> {

            }
        }
    }

    private fun calculateDays() {

    }

    private fun calculateMonths() {

    }
}