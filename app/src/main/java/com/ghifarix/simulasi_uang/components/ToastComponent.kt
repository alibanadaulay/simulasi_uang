package com.ghifarix.simulasi_uang.components

import android.content.Context
import android.widget.Toast
import com.ghifarix.simulasi_uang.R
import com.ghifarix.simulasi_uang.SingletonModel
import com.ghifarix.simulasi_uang.extensions.generatePdf

fun toastGeneratePdfSuccess(context: Context) {
    val pdfName = context.generatePdf(pdf = SingletonModel.getInstance().getPdf())
    val infoName = "${context.getString(R.string.file_name)} $pdfName"
    Toast.makeText(context, infoName, Toast.LENGTH_SHORT).show()
}