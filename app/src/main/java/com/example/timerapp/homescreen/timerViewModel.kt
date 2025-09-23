package com.example.timerapp.homescreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class timerViewModel : ViewModel(){
    private val _hours = MutableStateFlow<Int>(0)
    val hours: StateFlow<Int> = _hours.asStateFlow()

    private val _minutes = MutableStateFlow<Int>(0)
    val minutes: StateFlow<Int> = _minutes.asStateFlow()

    private val _seconds = MutableStateFlow<Int>(0)
    val seconds: StateFlow<Int> = _seconds.asStateFlow()

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()



    fun setHours(value: Int) {
        _hours.value = value
    }

    fun setMinutes(value: Int) {
        _minutes.value = value
    }

    fun setSeconds(value: Int) {
        _seconds.value = value
    }

    fun getTotalSeconds(): Long {
        val h = _hours.value
        val m = _minutes.value
        val s = _seconds.value
        return ((h * 3600 + m * 60 + s).toLong())
    }


}