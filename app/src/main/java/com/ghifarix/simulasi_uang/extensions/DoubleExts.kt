package com.ghifarix.simulasi_uang.extensions

import java.math.RoundingMode
import java.text.DecimalFormat

fun Double.roundOffDecimal():String{
    val df = DecimalFormat("#,###.##")
    df.roundingMode = RoundingMode.CEILING
    return df.format(this)
}