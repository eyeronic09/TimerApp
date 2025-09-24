package com.example.timerapp.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class timerViewModel : ViewModel(){
    private val _hours = MutableStateFlow<Int>(0)
    val hours: StateFlow<Int> = _hours.asStateFlow()

    private val _minutes = MutableStateFlow<Int>(0)
    val minutes: StateFlow<Int> = _minutes.asStateFlow()

    private val _seconds = MutableStateFlow<Int>(0)
    val seconds: StateFlow<Int> = _seconds.asStateFlow()

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

    private val _remainingTimeInSeconds  = MutableStateFlow(0L)
    val remainingTimeInSeconds: StateFlow<Long> = _remainingTimeInSeconds.asStateFlow()

    private val _totalDuration = MutableStateFlow(0L)
    val totalDuration : StateFlow<Long> = _totalDuration.asStateFlow()



    fun setHours(value: Int) {
        val hoursInRange = value in 0..24
        if (hoursInRange) {
            _hours.value = value
        }
    }

    fun setMinutes(value: Int) {
        val minutesInRange = value in 0 .. 60
        if (minutesInRange) {
            _minutes.value = value
        }
    }

    fun setSeconds(value: Int) {
        val secondsInRange = value in 0..60
        if (secondsInRange) {
            _seconds.value = value
        }
    }

    private var timerJob : Job? = null

    fun getTotalSeconds(): Long {
        val h = _hours.value
        val m = _minutes.value
        val s = _seconds.value
        return ((h * 3600 + m * 60 + s).toLong())
    }
    fun startTimer(){
        if (!_isRunning.value && _remainingTimeInSeconds.value == 0L) {
            val total = getTotalSeconds()
            _totalDuration.value = total
            _remainingTimeInSeconds.value = total
        }
        if (_remainingTimeInSeconds.value > 0) {
            _isRunning.value = true
            timerJob = viewModelScope.launch {
                while (_isRunning.value && _remainingTimeInSeconds.value > 0) {
                    delay(1000L)
                    if (_isRunning.value) {
                        _remainingTimeInSeconds.value--
                        updateTimeDisplay()
                    }
                }
                _isRunning.value = false
            }
        }
    }

    fun getRemainingTimeInSeconds() : Float {
        val totalSecounds = _hours.value * 3600L + _minutes.value * 60L + _hours.value

        if (totalSecounds == 0L) {
            return 100f
        }else{
            val remainingSeconds  = _remainingTimeInSeconds.value
            val elapsedSeconds  = totalSecounds - remainingSeconds
            return (elapsedSeconds .toFloat() / totalSecounds) * 100
        }

    }

    fun pauseTimer() {
        _isRunning.value = false
    }

    fun resetTimer() {
        _isRunning.value = false
        timerJob?.cancel()
        _remainingTimeInSeconds.value = 0
        _hours.value = 0
        _minutes.value = 0
        _seconds.value = 0
        updateTimeDisplay()
    }
    fun updateTimeDisplay() {
        val totalSeconds = _remainingTimeInSeconds.value
        _hours.value = (totalSeconds / 3600).toInt()
        _minutes.value = ((totalSeconds % 3600) / 60).toInt()
        _seconds.value = (totalSeconds % 60).toInt()
    }
}
