package com.ghifarix.simulasi_uang

import androidx.activity.ComponentActivity
import kotlin.math.pow

class MainActivity: ComponentActivity() {
}

fun calculate(){
    val pinjamanPokok =100000000F
    var sisaPinjaman = pinjamanPokok
    val tenor = 10
    val bunga = 10F
    val angsuran = pinjamanPokok.times(bunga/100/12) / (1F-1F/((1F + (bunga / 100) / 12)).pow(tenor.times(12)))
    println("angsuran $angsuran ")
    for(i in 0 until 10*12){
        val bungaBayar = sisaPinjaman.times(bunga/100/12)
        val pokok = angsuran-bungaBayar
        sisaPinjaman -= pokok
        if(sisaPinjaman < 0F){
            sisaPinjaman = 0F
        }
        println("$i bunga $bungaBayar, pokok $pokok, sisa $sisaPinjaman")
    }
}