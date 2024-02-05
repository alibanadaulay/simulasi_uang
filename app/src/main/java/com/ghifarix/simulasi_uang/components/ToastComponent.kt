package com.ghifarix.simulasi_uang.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import com.ghifarix.simulasi_uang.R
import com.ghifarix.simulasi_uang.SingletonModel
import com.ghifarix.simulasi_uang.extensions.generatePdf
import java.io.File

fun toastGeneratePdfSuccess(context: Context): String {
    val pdfName = context.generatePdf(pdf = SingletonModel.getInstance().getPdf())
    val infoName = "${context.getString(R.string.file_name)} $pdfName"
    Toast.makeText(context, infoName, Toast.LENGTH_SHORT).show()
    return infoName
}

fun openPdf(context: Context, fileName: String) {
    val file = File(Environment.DIRECTORY_DOCUMENTS, fileName)
    val path = Uri.fromFile(file)
    val intent = Intent(Intent.ACTION_VIEW)
    intent.setDataAndType(path, "application/pdf")
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
    }
}