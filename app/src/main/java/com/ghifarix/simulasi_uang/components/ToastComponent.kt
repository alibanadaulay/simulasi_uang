package com.ghifarix.simulasi_uang.components

import android.content.Context
import android.content.Intent
import android.os.Environment
import android.widget.Toast
import androidx.core.content.FileProvider
import com.ghifarix.simulasi_uang.SingletonModel
import com.ghifarix.simulasi_uang.extensions.generatePdf
import java.io.File

fun toastGeneratePdfSuccess(context: Context): String {
    val pdfName = context.generatePdf(pdf = SingletonModel.getInstance().getPdf())
    return pdfName ?: ""
}

fun openPdf(context: Context, fileName: String) {
    val downloadsDirectory =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
    val file = File(downloadsDirectory, "$fileName.pdf")
    val path = FileProvider.getUriForFile(
        context,
        context.applicationContext.packageName + ".provider",
        file
    )
    val intent = Intent(Intent.ACTION_VIEW)
    intent.setDataAndType(path, "application/pdf")
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
    }
}