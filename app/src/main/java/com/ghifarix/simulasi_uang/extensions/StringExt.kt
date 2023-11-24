package com.ghifarix.simulasi_uang.extensions

fun String.convertToInt(): Int {
    val double = this.toDouble()
    return double.toInt()
}

fun String.convertToDouble(): Double {
    return this.replace(",", "").toDouble()
}