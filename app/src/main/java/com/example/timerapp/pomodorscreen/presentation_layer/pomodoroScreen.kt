package com.example.timerapp.pomodorscreen.presentation_layer

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.timerapp.pomodorscreen.PomodoroVM

@Composable
fun pomodoro(viewModel: PomodoroVM) {
    val remainingMinutes by viewModel.minutesRemaining.collectAsState()
    val remainingSeconds by viewModel.remainingSecond.collectAsState()
    
    // Calculate seconds within the current minute
    val secondsInMinute = (remainingSeconds % 60).toInt()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display time in MM:SS format
        Text(
            text = String.format("%02d:%02d", remainingMinutes, secondsInMinute),
            style = MaterialTheme.typography.displayLarge.copy(
                fontSize = 72.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Control buttons
        Button(
            onClick = {
                viewModel.timer.start()
                Log.d("Time is " ,"$remainingSeconds" + "$remainingMinutes")
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Start")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun precs() {
    val viewModel = PomodoroVM()
    pomodoro(viewModel = viewModel)
}