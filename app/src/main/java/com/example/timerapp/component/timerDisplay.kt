package com.example.timerapp.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.timerapp.ui.theme.TimerAppTheme
@Composable
fun TimerDisplay(
    hour: Int,
    second:Int,
    minutes:Int,
    remainingSeconds: Long,
    onStartTimer: () -> Unit,
    onPauseTimer: () -> Unit,
    onResetTimer: () -> Unit,
    isRunning: Boolean,
    modifier: Modifier = Modifier
){
    Column(modifier = modifier.fillMaxWidth().padding(16.dp)) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Timer Display
            Text(
                text = String.format("%02d:%02d:%02d", hour, minutes, second),
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Controls Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Reset Button
                Button(
                    onClick = onResetTimer,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Reset")
                }

                // Start/Pause Button
                Button(
                    onClick = {
                        if (isRunning) {
                            onPauseTimer()
                        } else {
                            onStartTimer()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isRunning) MaterialTheme.colorScheme.secondary
                        else MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(if (isRunning) "Pause" else "Start")
                }
            }

            // Progress Indicator
            if (remainingSeconds > 0) {
                Spacer(modifier = Modifier.height(32.dp))
                LinearProgressIndicator(
                    progress = {
                        val total = (hour * 3600 + minutes * 60 + second).toFloat()
                        if (total > 0) remainingSeconds.toFloat() / total else 0f
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }


}
@Preview(showBackground = true)
@Composable
fun TimerDisplayPreview() {
    TimerAppTheme {
        TimerDisplay(
            hour = 1,
            minutes = 30,
            second = 45,
            remainingSeconds = 3600 + 1800 + 45, // 1h 30m 45s in seconds
            isRunning = false,
            onStartTimer = { /* Handle start */ },
            onPauseTimer = { /* Handle pause */ },
            onResetTimer = { /* Handle reset */ },
            modifier = Modifier
        )
    }
}