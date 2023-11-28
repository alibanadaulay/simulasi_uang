package com.ghifarix.simulasi_uang.model

data class PdfItem(
    val type: String = "Bulan",
    val interest: String = "Bunga",
    val capital: String = "Pokok",
    val instalments: String = "Angsuran",
    val remainingLoan: String = "Sisa Hutang"
)
