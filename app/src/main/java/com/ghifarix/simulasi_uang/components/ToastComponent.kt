package com.ghifarix.simulasi_uang.components

import android.content.Context
import android.widget.Toast
import com.ghifarix.simulasi_uang.R

fun toastGeneratePdfSuccess(context: Context, fileName: String?) {
    val filename = "${context.getString(R.string.file_name)} $fileName"
    Toast.makeText(context, filename, Toast.LENGTH_SHORT).show()
}