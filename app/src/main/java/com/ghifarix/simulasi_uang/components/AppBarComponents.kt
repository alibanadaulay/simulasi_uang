@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.ghifarix.simulasi_uang.components

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ghifarix.simulasi_uang.SingletonModel
import com.ghifarix.simulasi_uang.extensions.generatePdf
import com.ghifarix.simulasi_uang.extensions.getActivity
import com.ghifarix.simulasi_uang.extensions.rewardAd
import com.ghifarix.simulasi_uang.model.GeneratePdf
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.rewarded.RewardedAd

@Composable
fun TopAppBarHamburgerMenu(
    title: String = "this title",
    onClickHamburger: () -> Unit = {}
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = { onClickHamburger() }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "hamburger menu")
            }
        })
}

@Composable
fun TopAppBack(
    context: Context,
    title: String = "title",
    onBack: () -> Unit = {},
    rewardAds: State<RewardedAd?>,
    initAds: (RewardedAd?) -> Unit = {},
    showingAds: (Activity) -> Unit = {},
    generatePdf: GeneratePdf = GeneratePdf.KPR
) {
    val tag = "TopAppBack_$title"
    LaunchedEffect(key1 = true) {
        context.rewardAd {
            initAds(it)
            it?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Log.d(tag, "Ad dismissed fullscreen content.");
                    initAds(it)
                    val filename = toastGeneratePdfSuccess(context)
                    openPdf(context = context, fileName = filename)
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Log.d(tag, "Ad failed to show fullscreen content.");
                    initAds(null)
                }

                override fun onAdImpression() {
                    Log.d(tag, "Ad recorded an impression.");
                }

                override fun onAdClicked() {
                    Log.d(tag, "Ad was clicked.");
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d(tag, "Ad showed fullscreen content.");
                    SingletonModel.getInstance()
                        .generatePdf(context = context, generatePdf = generatePdf)
                }
            }
        }
    }
    TopAppBar(title = {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                onBack()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = title
                )
            }
            Text(text = title)
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {
                if (rewardAds.value == null) {
                    SingletonModel.getInstance()
                        .generatePdf(context = context, generatePdf = GeneratePdf.KPR)
                    val result = context.generatePdf()
                    if (result != null) {
                        Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    showingAds(context.getActivity())
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Download,
                    contentDescription = title
                )
            }
        }
    })
}