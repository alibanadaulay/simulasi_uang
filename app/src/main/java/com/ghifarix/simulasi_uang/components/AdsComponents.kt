package com.ghifarix.simulasi_uang.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.ghifarix.simulasi_uang.BuildConfig
import com.ghifarix.simulasi_uang.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun BannerAdsView(
    adSize: AdSize = AdSize.BANNER
) {
    val unitId = if (BuildConfig.BUILD_TYPE == "debug") {
        stringResource(id = R.string.ad_mob_test_banner_id)
    } else {
        stringResource(id = R.string.ad_mob_banner_id)
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        AndroidView(
            factory = {
                AdView(it).apply {
                    setAdSize(adSize)
                    adUnitId = unitId
                    loadAd(AdRequest.Builder().build())
                }
            },
        )
    }
}