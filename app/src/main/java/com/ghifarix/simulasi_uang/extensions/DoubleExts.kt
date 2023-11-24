package com.ghifarix.simulasi_uang.extensions

import java.math.RoundingMode
import java.text.DecimalFormat

fun Double.roundOffDecimal():String{
    val df = DecimalFormat("#,###.##")
    df.roundingMode = RoundingMode.CEILING
    if (this < 0) {
        return "0.0"
    }
    return df.format(this)
}