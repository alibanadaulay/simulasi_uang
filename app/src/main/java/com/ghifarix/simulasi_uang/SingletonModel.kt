package com.ghifarix.simulasi_uang

import android.content.Context
import com.ghifarix.simulasi_uang.model.GeneratePdf
import com.ghifarix.simulasi_uang.model.Pdf
import com.ghifarix.simulasi_uang.model.PdfItem
import com.ghifarix.simulasi_uang.screens.investment.model.Investment
import com.ghifarix.simulasi_uang.screens.kpr.model.Kpr
import com.ghifarix.simulasi_uang.screens.kpr.model.getTitle
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
            GeneratePdf.PINJOL -> getPdfByPinjol(context = context)
            GeneratePdf.INVESTASI -> getPdfByInvestment(context = context)
        }
    }

    fun getPdf() = _pdf
    private fun getPdfByPinjol(context: Context): Pdf {
        val map = mutableMapOf<String, String>()
        val items: MutableList<PdfItem> = mutableListOf()
        _pinjol?.let {
            map[context.getString(R.string.installment_type)] = it.installmentsType.name
            map[context.getString(R.string.loan)] = it.totalLoan
            map["${context.getString(R.string.down_payment)} (${it.dp})"] = it.dpAmount
            map[context.getString(R.string.loan_paid)] = it.loanToPay
            map["${context.getString(R.string.interest)} (${context.getString(R.string.riba)})"] =
                it.interest.toString()
            map["${context.getString(R.string.loan_duration)} (${
                if (it.installmentsType == PinjolType.Harian) context.getString(
                    R.string.daily
                ) else context.getString(R.string.monthly)
            })"] =
                it.loanTime.toString()
            map["${context.getString(R.string.increase)} (${it.interestAtPercentage})"] =
                it.interestAmount
            items.add(
                PdfItem(
                    type = if (it.installmentsType == PinjolType.Harian) context.getString(
                        R.string.day
                    ) else context.getString(R.string.month)
                )
            )
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

    private fun getPdfByKpr(context: Context): Pdf {
        val map = mutableMapOf<String, String>()
        val items: MutableList<PdfItem> = mutableListOf()
        _kpr?.let {
            map[context.getString(R.string.installment_type)] = it.getTitle(context)
            map[context.getString(R.string.loan)] = it.totalLoan
            map["${context.getString(R.string.service_fee)} (${it.dp}%)"] = it.dpAmount
            map[context.getString(R.string.loan_paid)] = it.loanToPay
            map["${context.getString(R.string.interest)} (${context.getString(R.string.riba)})"] =
                it.interest.toString()
            map["${context.getString(R.string.loan_duration)} (${context.getString(R.string.year)})"] =
                it.years.toString()
            map["${context.getString(R.string.interest)} (${it.interestAtPercentage}%)"] =
                it.interestAmount
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

    private fun getPdfByInvestment(context: Context): Pdf {
        val map = mutableMapOf<String, String>()
        val items: MutableList<PdfItem> = mutableListOf()
        _investment?.let {
            map[context.getString(R.string.base_capital)] = it.baseInvestment
            map[context.getString(R.string.total_investment)] = it.totalInvestment
            map[context.getString(R.string.investment_duration)] = it.investmentTime.toString()
            map[context.getString(R.string.investment_duration)] =
                "${it.increaseTime} ${context.getString(R.string.year)}"
            map["${context.getString(R.string.return_rate)} (${it.investmentRate}%)"] =
                it.amountIncrease
            map[context.getString(R.string.total_increase)] = it.amountIncrease
            map[context.getString(R.string.tax)] = "${it.tax} %"
            items.add(
                PdfItem(
                    type = context.getString(R.string.year),
                    interest = context.getString(R.string.increase),
                    capital = context.getString(R.string.tax),
                    instalments = context.getString(R.string.capital_increase),
                    remainingLoan = context.getString(R.string.investment)
                )
            )
            for (i in 0 until it.investmentItem.size) {
                val item = it.investmentItem[i]
                items.add(
                    PdfItem(
                        type = item.time,
                        interest = item.investmentIncrease,
                        capital = item.tax,
                        instalments = item.increaseCapital,
                        remainingLoan = item.investment
                    )
                )
            }
        }
        return Pdf(model = map, items = items)
    }
}