package com.example.healthpredict

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MedicalImagingViewModel : ViewModel() {
    private val _axialSlice = MutableLiveData(50)
    val axialSlice: LiveData<Int> = _axialSlice

    private val _sagittalSlice = MutableLiveData(50)
    val sagittalSlice: LiveData<Int> = _sagittalSlice

    private val _coronalSlice = MutableLiveData(50)
    val coronalSlice: LiveData<Int> = _coronalSlice

    fun updateAxialSlice(slice: Int) {
        _axialSlice.value = slice
    }

    fun updateSagittalSlice(slice: Int) {
        _sagittalSlice.value = slice
    }

    fun updateCoronalSlice(slice: Int) {
        _coronalSlice.value = slice
    }
}
