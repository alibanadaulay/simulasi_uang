package com.ghifarix.simulasi_uang.screens.kpr.input

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghifarix.simulasi_uang.SingletonModel
import com.ghifarix.simulasi_uang.extensions.roundOffDecimal
import com.ghifarix.simulasi_uang.screens.kpr.model.Kpr
import com.ghifarix.simulasi_uang.screens.kpr.model.KprItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.pow


@HiltViewModel
class KprInputViewModel @Inject constructor() : ViewModel() {
    private var _baseLoan = 0.0
    private var _dp = 0L
    private var _years = 0
    private var _interest = 0.0
    private val _state: MutableStateFlow<KprInputState> = MutableStateFlow(KprInputState.Idle)
    val state: StateFlow<KprInputState> = _state

    fun updateBaseLoan(loan: String) {
        viewModelScope.launch {
            _baseLoan = if (loan.isNullOrBlank()) {
                0.0
            } else {
                loan.replace(",", "").toDouble()
            }
        }
    }

    fun updateDp(dp: String) {
        viewModelScope.launch {
            _dp = if (dp.isNullOrBlank()) {
                0L
            } else {
                dp.toLong()
            }
        }
    }

    fun updateYears(years: String) {
        viewModelScope.launch {
            _years = if (years.isNullOrBlank()) {
                0
            } else {
                years.toInt()
            }
        }
    }

    fun updateRate(rate: String) {
        viewModelScope.launch {
            _interest = if (rate.isNullOrBlank()) {
                0.0
            } else {
                rate.toDouble()
            }
        }
    }

    fun calculate() {
        viewModelScope.launch {
            calculateAnuitas()
        }
    }

    private suspend fun calculateAnuitas() {
        var remainingLoan = _baseLoan
        val interest = _interest.toDouble()
        val installments =
            _baseLoan.times(interest / 100 / 12) / (1.0 - 1.0 / (1.0 + (interest / 100) / 12).pow(
                _years.times(12)
            ))
        var totalCapital = 0.0
        var totalInterest = 0.0
        val kprItems = mutableListOf<KprItem>()
        kprItems.add(KprItem(remainingLoan = _baseLoan.roundOffDecimal()))

        for (i in 0 until 10 * 12) {
            val interestPay = remainingLoan.times(interest / 100 / 12)
            val capitalPay = installments - interestPay
            totalInterest += interestPay
            totalCapital += capitalPay
            remainingLoan -= capitalPay
            if (remainingLoan < 0F) {
                remainingLoan = 0.0
            }
            kprItems.add(
                KprItem(
                    month = i + 1,
                    capital = capitalPay.roundOffDecimal(),
                    interest = interestPay.roundOffDecimal(),
                    installments = installments.roundOffDecimal(),
                    remainingLoan = remainingLoan.roundOffDecimal()
                )
            )
        }
        val kpr = Kpr(
            interest = _interest,
            years = _years,
            kprItems = kprItems,
            totalCapital = totalCapital.roundOffDecimal(),
            totalInterest = totalInterest.roundOffDecimal(),
            totalLoan = _baseLoan.roundOffDecimal(),
            totalInstallments = totalInterest.plus(totalCapital).roundOffDecimal()
        )
        SingletonModel.getInstance().updateKpr(kpr)
        _state.value = KprInputState.Submit
    }

    fun resetState(){
        viewModelScope.launch {
            _state.value = KprInputState.Idle
        }
    }

    fun calculateEfektif() {
        val pinjamanPokok = 100000000F
        var sisaPinjaman = pinjamanPokok
        val tenor = 10
        val bunga = 10F
        val angsuran =
            pinjamanPokok.times(bunga / 100 / 12) / (1F - 1F / (1F + (bunga / 100) / 12).pow(
                tenor.times(12)
            ))
        println("angsuran $angsuran ")
        for (i in 0 until 10 * 12) {
            val bungaBayar = sisaPinjaman.times(bunga / 100 / 12)
            val pokok = angsuran - bungaBayar
            sisaPinjaman -= pokok
            if (sisaPinjaman < 0F) {
                sisaPinjaman = 0F
            }
            println("$i bunga $bungaBayar, pokok $pokok, sisa $sisaPinjaman")
        }
    }

    fun calculateFlat() {
        val pinjamanPokok = 100000000F
        var sisaPinjaman = pinjamanPokok
        val tenor = 10
        val bunga = 10F
        val angsuran =
            pinjamanPokok.times(bunga / 100 / 12) / (1F - 1F / (1F + (bunga / 100) / 12).pow(
                tenor.times(12)
            ))
        println("angsuran $angsuran ")
        for (i in 0 until 10 * 12) {
            val bungaBayar = sisaPinjaman.times(bunga / 100 / 12)
            val pokok = angsuran - bungaBayar
            sisaPinjaman -= pokok
            if (sisaPinjaman < 0F) {
                sisaPinjaman = 0F
            }
            println("$i bunga $bungaBayar, pokok $pokok, sisa $sisaPinjaman")
        }
    }
}