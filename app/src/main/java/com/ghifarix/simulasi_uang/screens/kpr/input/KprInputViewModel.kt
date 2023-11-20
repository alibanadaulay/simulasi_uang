package com.ghifarix.simulasi_uang.screens.kpr.input

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.pow


@HiltViewModel
class KprInputViewModel @Inject constructor() : ViewModel() {
    private var _baseLoan = 0L
    private var _dp = 0L
    private var _years = 0L
    private var _rate = 1L

    fun updateBaseLoan(loan: String) {
        viewModelScope.launch {
            _baseLoan = loan.replace(",", "").toLong()
        }
    }

    fun updateDp(dp: String) {
        viewModelScope.launch {
            _dp = dp.toLong()
        }
    }

    fun updateYears(years: String) {
        viewModelScope.launch {
            _years = years.toLong()
        }
    }

    fun updateRate(rate: String) {
        viewModelScope.launch {
            _rate = rate.toLong()
        }
    }

    fun calculate() {
        calculateAnuitas()
    }

    fun calculateAnuitas() {
        var sisaPinjaman = _baseLoan.toDouble()
        val tenor = _years.toDouble()
        val bunga = _rate.toDouble()
        val angsuran =
            _baseLoan.times(bunga / 100 / 12) / (1.0 - 1.0 / (1.0 + (bunga / 100) / 12).pow(
                tenor.times(12)
            ))
        var totalPokok = 0.0
        var totalRiba = 0.0
        for (i in 0 until 10 * 12) {
            val bungaBayar = sisaPinjaman.times(bunga / 100 / 12)
            val pokok = angsuran - bungaBayar
            totalRiba += bungaBayar
            totalPokok += pokok
            sisaPinjaman -= pokok
            if (sisaPinjaman < 0F) {
                sisaPinjaman = 0.0
            }
            println("$i bunga $bungaBayar, pokok $pokok, sisa $sisaPinjaman")
        }
    }

    fun calculateEfektif() {
        val pinjamanPokok = 100000000F
        var sisaPinjaman = pinjamanPokok
        val tenor = 10
        val bunga = 10F
        val angsuran =
            pinjamanPokok.times(bunga / 100 / 12) / (1F - 1F / (1F + (bunga / 100) / 12).pow(
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
        val angsuran =
            pinjamanPokok.times(bunga / 100 / 12) / (1F - 1F / (1F + (bunga / 100) / 12).pow(
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