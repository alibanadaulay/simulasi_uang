package com.ghifarix.simulasi_uang.screens.pinjol.detail

import android.graphics.Bitmap
import android.graphics.pdf.PdfDocument
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ghifarix.simulasi_uang.SingletonModel
import com.ghifarix.simulasi_uang.screens.pinjol.model.Pinjol
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PinjolDetailViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow<PinjolDetailState>(PinjolDetailState.Idle)
    val state: StateFlow<PinjolDetailState> = _state
    private var _pinjol: Pinjol? = null

    fun getPinjol() {
        viewModelScope.launch {
            _pinjol = SingletonModel.getInstance().getPinjol()
            _pinjol?.let {
                _state.value = PinjolDetailState.LoadPinjolDetails(
                    pinjol = it
                )
            }
        }
    }

    fun generatePdf() {
        viewModelScope.launch {
            val pdfDocument = PdfDocument()
            var pageHeight = 1120
            var pageWidth = 792
            lateinit var bmp: Bitmap
            lateinit var scaledbmp: Bitmap
        }
    }
}