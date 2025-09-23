package com.example.timerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.example.timerapp.component.TimerDisplay
import com.example.timerapp.homescreen.timerViewModel
import com.example.timerapp.ui.theme.TimerAppTheme
import java.util.Timer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimerAppTheme {
                Surface (modifier = Modifier.fillMaxSize()) {
                    val viewModel = timerViewModel()
                    Timers(viewModel)
                }
            }
        }
    }
}

@Composable
fun Timers(timerViewModel: timerViewModel){
    val hours by timerViewModel.hours.collectAsState()
    val minutes by timerViewModel.minutes.collectAsState()
    val seconds by timerViewModel.seconds.collectAsState()
    val isRunning by timerViewModel.isRunning.collectAsState()
    val remainingSeconds by timerViewModel.TotalSecond.collectAsState()

// In your Composable
    TimerDisplay(
        hour = hours,
        minutes = minutes,
        second = seconds,
        remainingSeconds = remainingSeconds,
        isRunning = isRunning,
        onStartTimer = { timerViewModel.startTimer() },
        onPauseTimer = { timerViewModel.pauseTimer() },
        onResetTimer = { timerViewModel.resetTimer() }
    )
}