package com.example.timerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.timerapp.component.TimerDisplay
import com.example.timerapp.homescreen.timerViewModel
import com.example.timerapp.ui.theme.TimerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimerAppTheme {
                    val viewModel = timerViewModel()
                    Timers(viewModel)
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Timers(timerViewModel: timerViewModel) {
    val hours by timerViewModel.hours.collectAsState()
    val minutes by timerViewModel.minutes.collectAsState()
    val seconds by timerViewModel.seconds.collectAsState()
    val isRunning by timerViewModel.isRunning.collectAsState()
    val remainingSeconds by timerViewModel.remainingTimeInSeconds.collectAsState()
    val totalDuration by timerViewModel.totalDuration.collectAsState()



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Timer App") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            TimerDisplay(
                hour = hours,
                minutes = minutes,
                second = seconds,
                onStartTimer = { timerViewModel.startTimer() },
                onPauseTimer = { timerViewModel.pauseTimer() },
                onResetTimer = { timerViewModel.resetTimer() },
                modifier = Modifier.padding(16.dp),
                onHourChange = { timerViewModel.setHours(it) },
                onSecondChange = { timerViewModel.setSeconds(it) },
                onMinutesChange = { timerViewModel.setMinutes(it) },
                isRunning = isRunning,
                remainingSeconds = remainingSeconds,
                totalDuration = totalDuration
            )
        }
    }
}