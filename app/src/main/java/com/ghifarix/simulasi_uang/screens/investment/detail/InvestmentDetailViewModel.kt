package com.ghifarix.simulasi_uang.screens.investment.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghifarix.simulasi_uang.SingletonModel
import com.google.android.gms.ads.rewarded.RewardedAd
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvestmentDetailViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow<InvestmentDetailState>(InvestmentDetailState.Idle)
    val state: StateFlow<InvestmentDetailState> = _state
    private val _rewardAd = MutableStateFlow<RewardedAd?>(null)
    val rewardAd: StateFlow<RewardedAd?> = _rewardAd

    fun updateInterstitialAds(rewardAd: RewardedAd?) {
        viewModelScope.launch {
            _rewardAd.value = rewardAd
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