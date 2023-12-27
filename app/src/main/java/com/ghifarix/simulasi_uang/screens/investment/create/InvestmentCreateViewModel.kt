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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvestmentCreateViewModel @Inject constructor() : ViewModel() {
    private var _baseInvestment: Double = 0.0
    private var _investmentRate: Double = 0.0
    private var _baseInvestmentIncrease: Double = 0.0
    private var _years: Int = 0
    private var _tax: Double = 0.0
    private var _incereaseBaseYear: Int = 0
    private val _state = MutableStateFlow<InvestmentCreateState>(InvestmentCreateState.Idle)
    val state: StateFlow<InvestmentCreateState> = _state

    fun updateYears(years: String) {
        viewModelScope.launch {
            _years = if (years.isBlank()) {
                0
            } else {
                years.convertToInt()
            }
        }
    }

    fun updateIncreaseYear(increaseYear: String) {
        viewModelScope.launch {
            _incereaseBaseYear = if (increaseYear.isBlank()) {
                0
            } else {
                increaseYear.convertToInt()
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

    fun updateInvestmentIncrease(addInvestment: String) {
        viewModelScope.launch {
            _baseInvestmentIncrease = if (addInvestment.isBlank()) {
                0.0
            } else {
                addInvestment.replace(",", "").toDouble()
            }
        }
    }


    fun updateTax(tax: String) {
        viewModelScope.launch {
            _tax = if (tax.isBlank()) {
                0.0
            } else {
                tax.convertToPoint()
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
            var count = _incereaseBaseYear
            var totalInvestment = _baseInvestment
            var investment = _baseInvestment
            var totalInvestmentIncrease = 0.0
            var totalTax = 0.0
            var increaseInvestment = 0.0
            val investmentItems = mutableListOf<InvestmentItem>()
            investmentItems.add(
                InvestmentItem(
                    time = "0",
                    investment = _baseInvestment.roundOffDecimal()
                )
            )
            while (i < _years) {
                val investmentIncrease = investment * _investmentRate / 100
                val tax = investmentIncrease * _tax / 100
                val afterTax = (investmentIncrease - tax)
                totalTax += tax
                totalInvestmentIncrease += afterTax
                investment += afterTax
                if (count > 0) {
                    investment += _baseInvestmentIncrease
                    totalInvestment += _baseInvestmentIncrease
                    count--
                    increaseInvestment = _baseInvestmentIncrease
                }
                val investmentItem = InvestmentItem(
                    time = (i + 1).toString(),
                    tax = tax.roundOffDecimal(),
                    investment = investment.roundOffDecimal(),
                    investmentIncrease = investmentIncrease.roundOffDecimal(),
                    increaseCapital = increaseInvestment.roundOffDecimal()
                )
                increaseInvestment = 0.0
                investmentItems.add(investmentItem)
                i++
            }
            val percentageIncrease = (investment / totalInvestment * 100).roundOffDecimal()
            val amountIncrease = investment - totalInvestment
            investmentItems.add(
                InvestmentItem(
                    time = "Total",
                    investment = investment.roundOffDecimal(),
                    tax = totalTax.roundOffDecimal(),
                    investmentIncrease = totalInvestmentIncrease.roundOffDecimal()
                )
            )
            SingletonModel.getInstance().updateInvestment(
                Investment(
                    baseInvestment = _baseInvestment.roundOffDecimal(),
                    investmentTime = _years,
                    tax = _tax.roundOffDecimal(),
                    increaseInvestment = _baseInvestmentIncrease.roundOffDecimal(),
                    increaseTime = _incereaseBaseYear,
                    totalInvestment = totalInvestment.roundOffDecimal(),
                    investmentRate = _investmentRate.roundOffDecimal(),
                    investmentItem = investmentItems,
                    percentageIncrease = percentageIncrease,
                    amountIncrease = amountIncrease.roundOffDecimal(),
                )
            )
            _state.value = InvestmentCreateState.Submit
        }
    }

    fun reset() {
        viewModelScope.launch {
            _state.value = InvestmentCreateState.Idle
            _baseInvestment = 0.0
            _investmentRate = 0.0
            _baseInvestmentIncrease = 0.0
            _years = 0
            _tax = 0.0
            _incereaseBaseYear = 0
        }
    }
}