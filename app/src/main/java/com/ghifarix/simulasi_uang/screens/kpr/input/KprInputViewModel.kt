package com.ghifarix.simulasi_uang.screens.kpr.input

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.pow


@HiltViewModel
class KprInputViewModel @Inject constructor():ViewModel() {
    fun calculateAnuitas() {
        val pinjamanPokok = 100000000F
        var sisaPinjaman = pinjamanPokok
        val tenor = 10
        val bunga = 10F
        val angsuran = pinjamanPokok.times(bunga / 100 / 12) / (1F - 1F / (1F + (bunga / 100) / 12).pow(
            tenor.times(12)
        ))
        println("angsuran $angsuran ")
        for (i in 0 until 10 * 12) {
            val bungaBayar = sisaPinjaman.times(bunga / 100 / 12)
            val pokok = angsuran - bungaBayar
            sisaPinjaman -= pokok
            if (sisaPinjaman < 0F) {
                sisaPinjaman = 0F
            }
            println("$i bunga $bungaBayar, pokok $pokok, sisa $sisaPinjaman")
        }
    }

    fun calculateEfektif() {
        val pinjamanPokok = 100000000F
        var sisaPinjaman = pinjamanPokok
        val tenor = 10
        val bunga = 10F
        val angsuran = pinjamanPokok.times(bunga / 100 / 12) / (1F - 1F / (1F + (bunga / 100) / 12).pow(
            tenor.times(12)
        ))
        println("angsuran $angsuran ")
        for (i in 0 until 10 * 12) {
            val bungaBayar = sisaPinjaman.times(bunga / 100 / 12)
            val pokok = angsuran - bungaBayar
            sisaPinjaman -= pokok
            if (sisaPinjaman < 0F) {
                sisaPinjaman = 0F
            }
            println("$i bunga $bungaBayar, pokok $pokok, sisa $sisaPinjaman")
        }
    }

    fun calculateFlat() {
        val pinjamanPokok = 100000000F
        var sisaPinjaman = pinjamanPokok
        val tenor = 10
        val bunga = 10F
        val angsuran = pinjamanPokok.times(bunga / 100 / 12) / (1F - 1F / (1F + (bunga / 100) / 12).pow(
            tenor.times(12)
        ))
        println("angsuran $angsuran ")
        for (i in 0 until 10 * 12) {
            val bungaBayar = sisaPinjaman.times(bunga / 100 / 12)
            val pokok = angsuran - bungaBayar
            sisaPinjaman -= pokok
            if (sisaPinjaman < 0F) {
                sisaPinjaman = 0F
            }
            println("$i bunga $bungaBayar, pokok $pokok, sisa $sisaPinjaman")
        }
    }
}