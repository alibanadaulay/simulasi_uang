package com.ghifarix.simulasi_uang.screens.kpr.input

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghifarix.simulasi_uang.SingletonModel
import com.ghifarix.simulasi_uang.extensions.convertToInt
import com.ghifarix.simulasi_uang.extensions.roundOffDecimal
import com.ghifarix.simulasi_uang.screens.kpr.model.Kpr
import com.ghifarix.simulasi_uang.screens.kpr.model.KprItem
import com.ghifarix.simulasi_uang.screens.kpr.model.KprType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.pow


@HiltViewModel
class KprInputViewModel @Inject constructor() : ViewModel() {
    private var _kprType: KprType = KprType.ANUITAS
    private var _baseLoan = 0.0
    private var _years = 0
    private var _interest = 0.0
    private var _dp = 0.0
    private val _state: MutableStateFlow<KprInputState> = MutableStateFlow(KprInputState.Idle)
    val state: StateFlow<KprInputState> = _state
    private val _dpAmount: MutableStateFlow<String> = MutableStateFlow("0.0")
    val dpAmount: StateFlow<String> = _dpAmount

    fun updateBaseLoan(loan: String) {
        viewModelScope.launch {
            _baseLoan = if (loan.isBlank()) {
                0.0
            } else {
                loan.replace(",", "").toDouble()
            }
            calculateDp()
        }
    }

    fun updateKprType(kprType: KprType) {
        viewModelScope.launch {
            _kprType = kprType
        }
    }

    fun updateDp(dp: String) {
        viewModelScope.launch {
            _dp = if (dp.isBlank()) {
                0.0
            } else {
                dp.replace(",", "").toDouble()
            }
            calculateDp()
        }
    }


    fun updateYears(years: String) {
        viewModelScope.launch {
            _years = if (years.isBlank()) {
                0
            } else {
                years.convertToInt()
            }
        }
    }

    fun updateRate(rate: String) {
        viewModelScope.launch {
            _interest = if (rate.isBlank()) {
                0.0
            } else {
                rate.toDouble()
            }
        }
    }

    fun calculate() {
        viewModelScope.launch {
            when (_kprType) {
                KprType.ANUITAS -> {
                    calculateAnuitas()
                }

                KprType.EFEKTIF -> {
                    calculateEfektif()
                }

                KprType.FLAT -> {
                    calculateFlat()
                }
            }
        }
    }

    private fun calculateDp() {
        _dpAmount.value = (_baseLoan * _dp / 100).roundOffDecimal()
    }

    private fun calculateAnuitas() {
        var remainingLoan = _baseLoan - (_baseLoan * _dp / 100)
        val interest = _interest
        val installments =
            remainingLoan.times(interest / 100 / 12) / (1.0 - 1.0 / (1.0 + (interest / 100) / 12).pow(
                _years.times(12)
            ))
        var totalInterest = 0.0
        val kprItems = mutableListOf<KprItem>()
        kprItems.add(KprItem(remainingLoan = remainingLoan.roundOffDecimal()))

        for (i in 0 until _years * 12) {
            val interestPay = remainingLoan.times(interest / 100 / 12)
            val capitalPay = installments - interestPay
            totalInterest += interestPay
            remainingLoan -= capitalPay
            if (remainingLoan < 0F) {
                remainingLoan = 0.0
            }
            kprItems.add(
                KprItem(
                    month = (i + 1).toString(),
                    capital = capitalPay.roundOffDecimal(),
                    interest = interestPay.roundOffDecimal(),
                    installments = installments.roundOffDecimal(),
                    remainingLoan = remainingLoan.roundOffDecimal()
                )
            )
        }
        kprItems.add(
            KprItem(
                "Total",
                capital = _baseLoan.roundOffDecimal(),
                interest = totalInterest.roundOffDecimal(),
                installments = _baseLoan.plus(totalInterest).roundOffDecimal()
            )
        )
        val kpr = Kpr(
            installmentsType = _kprType,
            interest = _interest,
            years = _years,
            kprItems = kprItems,
            totalLoan = _baseLoan.roundOffDecimal(),
            loanToPay = (_baseLoan * (100 - _dp) / 100).roundOffDecimal(),
            dp = _dp,
            dpAmount = _dpAmount.value,
            interestAtPercentage = (totalInterest / _baseLoan * 100).roundOffDecimal(),
            interestAmount = totalInterest.roundOffDecimal()
        )
        SingletonModel.getInstance().updateKpr(kpr)
        _state.value = KprInputState.Submit
    }

    private fun calculateFlat() {
        val totalMonth = _years*12
        var remainingLoan = _baseLoan - (_baseLoan * _dp / 100)
        val capitalPay = remainingLoan/totalMonth
        val interestPay = remainingLoan*(_interest/100)*_years/totalMonth
        var totalInterest = 0.0
        val kprItems = mutableListOf<KprItem>()
        kprItems.add(KprItem(remainingLoan = remainingLoan.roundOffDecimal()))
        for (i in 0 until  totalMonth) {
            totalInterest += interestPay
            remainingLoan -= capitalPay
            if (remainingLoan < 0F) {
                remainingLoan = 0.0
            }
            kprItems.add(
                KprItem(
                    month = (i + 1).toString(),
                    capital = capitalPay.roundOffDecimal(),
                    interest = interestPay.roundOffDecimal(),
                    installments = (capitalPay+interestPay).roundOffDecimal(),
                    remainingLoan = remainingLoan.roundOffDecimal()
                )
            )
        }
        kprItems.add(
            KprItem(
                "Total",
                capital = _baseLoan.roundOffDecimal(),
                interest = totalInterest.roundOffDecimal(),
                installments = _baseLoan.plus(totalInterest).roundOffDecimal()
            )
        )
        val kpr = Kpr(
            installmentsType = _kprType,
            interest = _interest,
            years = _years,
            kprItems = kprItems,
            totalLoan = _baseLoan.roundOffDecimal(),
            loanToPay = (_baseLoan * (100 - _dp) / 100).roundOffDecimal(),
            dp = _dp,
            dpAmount = _dpAmount.value,
            interestAtPercentage = (totalInterest / _baseLoan * 100).roundOffDecimal(),
            interestAmount = totalInterest.roundOffDecimal()
        )
        SingletonModel.getInstance().updateKpr(kpr)
        _state.value = KprInputState.Submit
    }

    fun resetState() {
        viewModelScope.launch {
            _state.value = KprInputState.Idle
            _baseLoan = 0.0
            _years = 0
            _interest = 0.0
            _dp = 0.0
            _kprType = KprType.ANUITAS
        }
    }

    private fun calculateEfektif() {
        val totalMonth = _years*12
        var remainingLoan = _baseLoan - (_baseLoan * _dp / 100)
        val capitalPay = remainingLoan/totalMonth
        var totalInterest = 0.0
        val kprItems = mutableListOf<KprItem>()
        kprItems.add(KprItem(remainingLoan = remainingLoan.roundOffDecimal()))
        for (i in 0 until  totalMonth) {
            val interestPay = remainingLoan*_interest/100*30/360
            totalInterest += interestPay
            remainingLoan -= capitalPay
            if (remainingLoan < 0F) {
                remainingLoan = 0.0
            }
            kprItems.add(
                KprItem(
                    month = (i + 1).toString(),
                    capital = capitalPay.roundOffDecimal(),
                    interest = interestPay.roundOffDecimal(),
                    installments = (capitalPay+ interestPay).roundOffDecimal(),
                    remainingLoan = remainingLoan.roundOffDecimal()
                )
            )
        }
        kprItems.add(
            KprItem(
                "Total",
                capital = _baseLoan.roundOffDecimal(),
                interest = totalInterest.roundOffDecimal(),
                installments = _baseLoan.plus(totalInterest).roundOffDecimal()
            )
        )
        val kpr = Kpr(
            installmentsType = _kprType,
            interest = _interest,
            years = _years,
            kprItems = kprItems,
            totalLoan = _baseLoan.roundOffDecimal(),
            loanToPay = (_baseLoan * (100 - _dp) / 100).roundOffDecimal(),
            dp = _dp,
            dpAmount = _dpAmount.value,
            interestAtPercentage = (totalInterest / _baseLoan * 100).roundOffDecimal(),
            interestAmount = totalInterest.roundOffDecimal()
        )
        SingletonModel.getInstance().updateKpr(kpr)
        _state.value = KprInputState.Submit
    }
}