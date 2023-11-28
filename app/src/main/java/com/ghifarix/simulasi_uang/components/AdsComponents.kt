package com.ghifarix.simulasi_uang.components

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.ghifarix.simulasi_uang.SingletonModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

@Composable
fun BannerAds(
    addUnitId: String = "ca-app-pub-3940256099942544/63009781111",
    adSize: AdSize = AdSize.BANNER
) {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = {
            AdView(it).apply {
                adUnitId = addUnitId
                setAdSize(adSize)
                loadAd(AdRequest.Builder().build())
            }
        })
}

fun loadRewarded(
    context: Context,
    addUnitId: String = "ca-app-pub-3940256099942544/5224354917",
    onReward: (Boolean) -> Unit = {}
) {
    RewardedAd.load(context,
        addUnitId,
        AdRequest.Builder().build(),
        object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                onReward(false)
            }

            override fun onAdLoaded(rewardedAd: RewardedAd) {
                onReward(true)
                SingletonModel.getInstance().resetCountReward()
            }
        })
}