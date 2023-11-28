package com.ghifarix.simulasi_uang.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Environment
import androidx.core.content.ContextCompat
import com.ghifarix.simulasi_uang.R
import com.ghifarix.simulasi_uang.SingletonModel
import com.ghifarix.simulasi_uang.model.Pdf
import java.io.File
import java.io.FileOutputStream
import java.util.Date
import kotlin.math.roundToInt


fun Context.generatePdf(pdf: Pdf = SingletonModel.getInstance().getPdfByPinjol()): String {
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
    }
    pdfDocument.close()
    return "$date.pdf"
}