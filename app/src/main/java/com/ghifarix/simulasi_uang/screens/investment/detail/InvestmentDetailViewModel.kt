package com.ghifarix.simulasi_uang.screens.investment.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghifarix.simulasi_uang.SingletonModel
import com.google.android.gms.ads.interstitial.InterstitialAd
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvestmentDetailViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow<InvestmentDetailState>(InvestmentDetailState.Idle)
    val state: StateFlow<InvestmentDetailState> = _state
    private val _interstitialAd = MutableStateFlow<InterstitialAd?>(null)
    val interstitialAd: StateFlow<InterstitialAd?> = _interstitialAd

    fun updateInterstitialAds(interstitialAd: InterstitialAd?) {
        viewModelScope.launch {
            _interstitialAd.value = interstitialAd
        }
    }

    fun getInvestment() {
        viewModelScope.launch {
            val investment = SingletonModel.getInstance().getInvestment()
            investment?.let {
                _state.value = InvestmentDetailState.Load(investment = investment)
            }
        }
    }
}