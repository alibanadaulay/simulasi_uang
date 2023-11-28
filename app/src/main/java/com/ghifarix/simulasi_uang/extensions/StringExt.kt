package com.ghifarix.simulasi_uang.extensions

fun String.convertToInt(): Int {
    val double = this.convertToPoint()
    return double.toInt()
}

fun String.convertToDouble(): Double {
    return this.replace(",", "").toDouble()
}

fun String.convertToPoint(): Double {
    return this.replace(",", ".").toDouble()
}