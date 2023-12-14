package com.ghifarix.simulasi_uang.screens.investment.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghifarix.simulasi_uang.SingletonModel
import com.ghifarix.simulasi_uang.extensions.convertToInt
import com.ghifarix.simulasi_uang.extensions.convertToPoint
import com.ghifarix.simulasi_uang.extensions.roundOffDecimal
import com.ghifarix.simulasi_uang.screens.investment.model.Investment
import com.ghifarix.simulasi_uang.screens.investment.model.InvestmentItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvestmentCreateViewModel @Inject constructor() : ViewModel() {
    private var _baseInvestment: Double = 0.0
    private var _years: Int = 0
    private var _investmentRate: Double = 0.0

    fun updateYears(years: String) {
        viewModelScope.launch {
            _years = if (years.isBlank()) {
                0
            } else {
                years.convertToInt()
            }
        }
    }

    fun updateInvestment(investment: String) {
        viewModelScope.launch {
            _investmentRate = if (investment.isBlank()) {
                0.0
            } else {
                investment.convertToPoint()
            }
        }
    }

    fun updateBaseLoan(investment: String) {
        viewModelScope.launch {
            _baseInvestment = if (investment.isBlank()) {
                0.0
            } else {
                investment.replace(",", "").toDouble()
            }
        }
    }

    fun calculate() {
        viewModelScope.launch {
            var i = 0
            var investment = _baseInvestment
            var totalInvestmentIncrease = "0.0"
            val investmentItems = mutableListOf<InvestmentItem>()
            while (i < _years) {
                val investmentIncrease = _baseInvestment * _investmentRate / 100
                totalInvestmentIncrease += investmentIncrease
                investment += investmentIncrease
                val investmentItem = InvestmentItem(
                    time = i.toString(),
                    investment = investment.roundOffDecimal(),
                    investmentIncrease = investmentIncrease.roundOffDecimal()
                )
                investmentItems.add(investmentItem)
                i++
            }
            SingletonModel.getInstance().updateInvestment(
                Investment(
                    baseInvestment = _baseInvestment.toString(),
                    investmentTime = _years,
                    investmentRate = _investmentRate.toString(),
                    investmentItem = investmentItems
                )
            )
        }
    }
}