package com.ghifarix.simulasi_uang

import com.ghifarix.simulasi_uang.screens.kpr.model.Kpr

class SingletonModel {
    companion object {
        @Volatile
        private var instance: SingletonModel? = null
        private var _kpr:Kpr? = null

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
    fun updateKpr(kpr: Kpr){
        _kpr = kpr
    }

    fun getKpr() = _kpr

}