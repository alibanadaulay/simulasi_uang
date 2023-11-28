package com.ghifarix.simulasi_uang.screens.pinjol.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghifarix.simulasi_uang.SingletonModel
import com.ghifarix.simulasi_uang.extensions.convertToDouble
import com.ghifarix.simulasi_uang.extensions.convertToInt
import com.ghifarix.simulasi_uang.extensions.convertToPoint
import com.ghifarix.simulasi_uang.extensions.roundOffDecimal
import com.ghifarix.simulasi_uang.screens.pinjol.model.Pinjol
import com.ghifarix.simulasi_uang.screens.pinjol.model.PinjolItem
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
            getDp()
        }
    }

    fun updateInterest(interest: String) {
        viewModelScope.launch {
            _interest = if (interest.isBlank()) {
                0.0
            } else {
                interest.convertToPoint()
            }
        }
    }

    fun updateServiceCostInterest(costServiceInterest: String) {
        viewModelScope.launch {
            _serviceCostInterest = if (costServiceInterest.isBlank()) {
                0.0
            } else {
                costServiceInterest.toDouble()
            }
            getDp()
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
                    calculateDays()
                }

                PinjolType.Bulanan -> {
                    calculateMonths()
                }
            }
        }
    }

    private fun getDp(): Double {
        val dp = _baseLoan * _serviceCostInterest / 100
        _serviceCost.value = dp.roundOffDecimal()
        return dp
    }

    private fun calculateDays() {
        var totalLoan = _baseLoan + _serviceCost.value.convertToDouble()
        val capital = totalLoan / _loanTime
        val interest = totalLoan * _interest / 100
        var totalInterest = 0.0
        val pinjolItems = mutableListOf<PinjolItem>()
        val item = PinjolItem(
            remainingLoan = totalLoan.roundOffDecimal()
        )
        pinjolItems.add(item)
        for (i in 0 until _loanTime) {
            totalLoan -= capital
            totalInterest += interest
            val pinjolItem = PinjolItem(
                monthOrDay = i.plus(1).toString(),
                capital = capital.roundOffDecimal(),
                interest = interest.roundOffDecimal(),
                installments = capital.plus(interest).roundOffDecimal(),
                remainingLoan = totalLoan.roundOffDecimal()
            )
            pinjolItems.add(pinjolItem)
        }
        pinjolItems.add(
            PinjolItem(
                monthOrDay = "Total",
                capital = _baseLoan.roundOffDecimal(),
                interest = totalInterest.roundOffDecimal(),
                installments = _baseLoan.plus(totalInterest).roundOffDecimal(),
                remainingLoan = 0.0.roundOffDecimal()
            )
        )
        val pinjol = Pinjol(
            pinjolItems = pinjolItems,
            interest = _interest,
            loanTime = _loanTime,
            dp = _serviceCostInterest,
            dpAmount = _serviceCost.value,
            totalLoan = _baseLoan.roundOffDecimal(),
            loanToPay = (_baseLoan + _serviceCost.value.convertToDouble()).roundOffDecimal(),
            installmentsType = _pinjolType.value,
            interestAtPercentage = (totalInterest / _baseLoan * 100).roundOffDecimal(),
            interestAmount = totalInterest.roundOffDecimal()
        )
        SingletonModel.getInstance().updatePinjol(pinjol)
        _state.value = PinjolCreateState.Submit
    }

    private fun calculateMonths() {
        var totalLoan = _baseLoan + _serviceCost.value.convertToDouble()
        val capital = totalLoan / _loanTime
        val interest = totalLoan * _interest / 100
        var totalInterest = 0.0
        val pinjolItems = mutableListOf<PinjolItem>()
        val item = PinjolItem(
            remainingLoan = totalLoan.roundOffDecimal()
        )
        pinjolItems.add(item)
        for (i in 0 until _loanTime) {
            totalLoan -= capital
            totalInterest += interest
            val pinjolItem = PinjolItem(
                monthOrDay = i.plus(1).toString(),
                capital = capital.roundOffDecimal(),
                interest = interest.roundOffDecimal(),
                installments = capital.plus(interest).roundOffDecimal(),
                remainingLoan = totalLoan.roundOffDecimal()
            )
            pinjolItems.add(pinjolItem)
        }
        pinjolItems.add(
            PinjolItem(
                monthOrDay = "Total",
                capital = _baseLoan.roundOffDecimal(),
                interest = totalInterest.roundOffDecimal(),
                installments = _baseLoan.plus(totalInterest).roundOffDecimal(),
                remainingLoan = 0.0.roundOffDecimal()
            )
        )
        val pinjol = Pinjol(
            pinjolItems = pinjolItems,
            interest = _interest,
            loanTime = _loanTime,
            dp = _serviceCostInterest,
            dpAmount = _serviceCost.value,
            totalLoan = _baseLoan.roundOffDecimal(),
            loanToPay = (_baseLoan + _serviceCost.value.convertToDouble()).roundOffDecimal(),
            installmentsType = _pinjolType.value,
            interestAtPercentage = (totalInterest / _baseLoan * 100).roundOffDecimal(),
            interestAmount = totalInterest.roundOffDecimal()
        )
        SingletonModel.getInstance().updatePinjol(pinjol)
        _state.value = PinjolCreateState.Submit
    }

    fun resetState() {
        viewModelScope.launch {
            _serviceCostInterest = 0.0
            _interest = 0.0
            _baseLoan = 0.0
            _loanTime = 0
            _state.value = PinjolCreateState.Idle
            _pinjolType.value = PinjolType.Harian
            _serviceCost.value = "0.0"
        }
    }
}