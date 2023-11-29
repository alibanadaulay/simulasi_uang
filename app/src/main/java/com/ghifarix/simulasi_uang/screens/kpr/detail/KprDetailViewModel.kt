package com.ghifarix.simulasi_uang.screens.kpr.detail

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
class KprDetailViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow<KprDetailState>(KprDetailState.Idle)
    val state: StateFlow<KprDetailState> = _state
    private var interstitialAd: InterstitialAd? = null

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

    fun interstitialAd(interstitialAd: InterstitialAd?) {
        this.interstitialAd = interstitialAd
    }

    fun getInterstitialAd(): InterstitialAd? = interstitialAd
}