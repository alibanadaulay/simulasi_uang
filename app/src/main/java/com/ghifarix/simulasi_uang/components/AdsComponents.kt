package com.ghifarix.simulasi_uang.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun BannerAds(addUnitId: String = "ca-app-pub-3940256099942544/63009781111") {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = {
            AdView(it).apply {
                adUnitId = addUnitId
                setAdSize(AdSize.BANNER)
                loadAd(AdRequest.Builder().build())
            }
        })
}