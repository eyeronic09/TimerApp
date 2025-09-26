package com.example.timerapp.pomodorscreen

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


data class TimerState(
    val minutes : Long  = 25,
    val breaks : Long
)
class PomodoroVM : ViewModel() {
    val _givenMinutes = MutableStateFlow(25)
    val Minutes: StateFlow<Int> = _givenMinutes.asStateFlow()

    val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

    val _remainingSecond = MutableStateFlow(0L)
    val remainingSecond : StateFlow<Long>  = _remainingSecond.asStateFlow()

    val minutesRemaining: StateFlow<Long> = remainingSecond
        .map { it / 60 }
        .stateIn(viewModelScope, SharingStarted.Eagerly, getTotalSecond() / 60)

    fun getTotalSecond(): Long{
        val m = _givenMinutes.value
        return m * 60L
    }

    val timer = object : CountDownTimer(getTotalSecond() * 1000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            val secondsLeft = millisUntilFinished / 1000
            Log.d("Time",  "Seconds left: ${secondsLeft % 60}")
            _remainingSecond.value = secondsLeft

        }
        override fun onFinish() {
            println("Time’s up!")
            _remainingSecond.value = 0L
        }
    }



}