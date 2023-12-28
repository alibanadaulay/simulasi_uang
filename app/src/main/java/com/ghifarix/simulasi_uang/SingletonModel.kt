package com.ghifarix.simulasi_uang

import android.content.Context
import com.ghifarix.simulasi_uang.model.GeneratePdf
import com.ghifarix.simulasi_uang.model.Pdf
import com.ghifarix.simulasi_uang.model.PdfItem
import com.ghifarix.simulasi_uang.screens.investment.model.Investment
import com.ghifarix.simulasi_uang.screens.kpr.model.Kpr
import com.ghifarix.simulasi_uang.screens.pinjol.model.Pinjol
import com.ghifarix.simulasi_uang.screens.pinjol.model.PinjolType

class SingletonModel {
    companion object {
        @Volatile
        private var instance: SingletonModel? = null
        private var _kpr: Kpr? = null
        private var _pinjol: Pinjol? = null
        private var _pdf: Pdf? = null
        private var _investment: Investment? = null

        fun getInstance(): SingletonModel {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = SingletonModel()
                    }
                }
            }
            return instance!!
        }
    }

    fun updateKpr(kpr: Kpr) {
        _kpr = kpr
    }

    fun getKpr() = _kpr

    fun updatePinjol(pinjol: Pinjol) {
        _pinjol = pinjol
    }

    fun getPinjol() = _pinjol

    fun updateInvestment(investment: Investment) {
        _investment = investment
    }

    fun getInvestment() = _investment
    fun generatePdf(context: Context, generatePdf: GeneratePdf) {
        _pdf = when (generatePdf) {
            GeneratePdf.KPR -> getPdfByKpr(context = context)
            GeneratePdf.PINJOL -> getPdfByPinjol()
            GeneratePdf.INVESTASI -> getPdfByPinjol()
        }
    }

    fun getPdf() = _pdf
    fun getPdfByPinjol(): Pdf {
        val map = mutableMapOf<String, String>()
        val items: MutableList<PdfItem> = mutableListOf()
        _pinjol?.let {
            map["Jenis Angsuran"] = it.installmentsType.name
            map["Pinjaman"] = "Rp ${it.totalLoan}"
            map["DP (${it.dp})"] = "Rp ${it.dpAmount}"
            map["Pinjaman Dibayar"] = "Rp ${it.loanToPay}"
            map["Bunga (Riba)"] = it.interest.toString()
            map["Lama Pinjaman (${if (it.installmentsType == PinjolType.Harian) "Harian" else "Bulanan"})"] =
                it.loanTime.toString()
            map["Pertambahan (${it.interestAtPercentage})"] = "Rp ${it.interestAmount}"
            items.add(PdfItem(type = if (it.installmentsType == PinjolType.Harian) "Hari" else "Bulan"))
            for (i in 0 until it.pinjolItems.size) {
                val item = it.pinjolItems[i]
                items.add(
                    PdfItem(
                        type = item.monthOrDay,
                        interest = item.interest,
                        capital = item.capital,
                        instalments = item.installments,
                        remainingLoan = item.remainingLoan
                    )
                )
            }
        }
        return Pdf(model = map, items = items)
    }

    fun getPdfByKpr(context: Context): Pdf {
        val map = mutableMapOf<String, String>()
        val items: MutableList<PdfItem> = mutableListOf()
        _kpr?.let {
            map[context.getString(R.string.installment_type)] = it.installmentsType.name
            map[context.getString(R.string.loan)] = "Rp ${it.totalLoan}"
            map["${context.getString(R.string.service_fee)} (${it.dp})"] = "Rp ${it.dpAmount}"
            map["Pinjaman Dibayar"] = "Rp ${it.loanToPay}"
            map["Bunga (Riba)"] = it.interest.toString()
            map["Lama Pinjaman (Tahun)"] = it.years.toString()
            map["Pertambahan (${it.interestAtPercentage})"] = "Rp ${it.interestAmount}"
            items.add(PdfItem())
            for (i in 0 until it.kprItems.size) {
                val item = it.kprItems[i]
                items.add(
                    PdfItem(
                        type = item.month,
                        interest = item.interest,
                        capital = item.capital,
                        instalments = item.installments,
                        remainingLoan = item.remainingLoan
                    )
                )
            }
        }
        return Pdf(model = map, items = items)
    }


}