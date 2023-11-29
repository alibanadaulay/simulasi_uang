package com.ghifarix.simulasi_uang.extensions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.util.Log
import androidx.core.content.ContextCompat
import com.ghifarix.simulasi_uang.BuildConfig
import com.ghifarix.simulasi_uang.R
import com.ghifarix.simulasi_uang.SingletonModel
import com.ghifarix.simulasi_uang.model.Pdf
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import java.io.File
import java.io.FileOutputStream
import java.util.Date
import kotlin.math.roundToInt


internal fun Context.getActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Permissions should be called in the context of an Activity")
}

internal fun Context.interstitialAd(onCallback: (InterstitialAd?) -> Unit) {
    val adRequest = AdRequest.Builder().build()
    val unitId = if (BuildConfig.BUILD_TYPE == "debug") {
        "ca-app-pub-3940256099942544/1033173712"
    } else {
        "ca-app-pub-3134124105900068/6991287904"
    }
    InterstitialAd.load(this, unitId, adRequest, object :
        InterstitialAdLoadCallback() {
        override fun onAdLoaded(interstitialAd: InterstitialAd) {
            super.onAdLoaded(interstitialAd)
            onCallback(interstitialAd)
        }

        override fun onAdFailedToLoad(interstitialAd: LoadAdError) {
            super.onAdFailedToLoad(interstitialAd)
            Log.e("KprDetailScreenF", "$interstitialAd")
            onCallback(null)
        }
    })

}

fun Context.generatePdf(pdf: Pdf? = SingletonModel.getInstance().getPdf()): String? {
    if (pdf == null) {
        return null
    }
    val pageHeight = 1120
    val pageWidth = 792

    val pdfDocument = PdfDocument()

    val paint = Paint()
    val title = Paint()

    val bmp: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.icon)
    val scaledbmp: Bitmap = Bitmap.createScaledBitmap(bmp, 140, 140, false)

    val totalPage = (pdf.items.size / 27.0).roundToInt()

    var listShow = pdf.items.size
    for (i in 0 until totalPage) {
        val myPageInfo: PdfDocument.PageInfo? =
            PdfDocument.PageInfo.Builder(pageWidth, pageHeight, i + 1).create()
        val myPage: PdfDocument.Page = pdfDocument.startPage(myPageInfo)
        val canvas: Canvas = myPage.canvas
        if (i == 0) {
            listShow = if (pdf.items.size > 20) 20 else pdf.items.size
            canvas.drawBitmap(scaledbmp, 56F, 40F, paint)
            title.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            title.textSize = 24F
            title.color = ContextCompat.getColor(this, android.R.color.black)
            canvas.drawText("Simulasi Uang", 209F, 100F, title)
            title.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            title.color = ContextCompat.getColor(this, android.R.color.black)
            title.textSize = 18F

            title.textAlign = Paint.Align.LEFT
            val titlePositionXKey = 100f
            val titlePositionXValue = 675f
            var titleYPosition = 250f
            pdf.model.forEach { (t, u) ->
                canvas.drawText(t, titlePositionXKey, titleYPosition, title)
                title.textAlign = Paint.Align.RIGHT
                canvas.drawText(u, titlePositionXValue, titleYPosition, title)
                titleYPosition += 20
                title.textAlign = Paint.Align.LEFT
            }
            titleYPosition += 50

            for (j in 0 until listShow) {
                title.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                title.textSize = 15F
                val item = pdf.items[j]
                if (item.type == "Total") {
                    title.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                }
                canvas.drawText(item.type, 75f, titleYPosition, title)
                canvas.drawText(item.interest, 200f, titleYPosition, title)
                canvas.drawText(item.capital, 350f, titleYPosition, title)
                canvas.drawText(item.instalments, 500f, titleYPosition, title)
                canvas.drawText(item.remainingLoan, 650f, titleYPosition, title)
                titleYPosition += 30
                title.textAlign = Paint.Align.CENTER
            }
        } else {
            var titleYPosition = 100f
            val newList = if (pdf.items.size > 20 + 30 * (i)) 20 + 30 * (i) else pdf.items.size
            for (j in listShow until newList) {
                val item = pdf.items[j]
                if (item.type == "Total") {
                    title.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                }
                canvas.drawText(item.type, 75f, titleYPosition, title)
                canvas.drawText(item.interest, 200f, titleYPosition, title)
                canvas.drawText(item.capital, 350f, titleYPosition, title)
                canvas.drawText(item.instalments, 500f, titleYPosition, title)
                canvas.drawText(item.remainingLoan, 650f, titleYPosition, title)
                titleYPosition += 30
            }
            listShow = newList
        }
        pdfDocument.finishPage(myPage)
    }
    val downloadsDirectory =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)

    val date = Date().time
    val file = File(downloadsDirectory, "$date.pdf")

    try {
        pdfDocument.writeTo(FileOutputStream(file))

    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
    pdfDocument.close()
    return file.absolutePath
}