package com.example.timerapp.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.timerapp.ui.theme.TimerAppTheme

@Composable
fun TimerDisplay(
    hour: Int,
    second: Int,
    minutes: Int,
    remainingSeconds: Long,
    onStartTimer: () -> Unit,
    onPauseTimer: () -> Unit,
    onResetTimer: () -> Unit,
    isRunning: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().padding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = String.format("%02d:%02d:%02d", hour, minutes, second % 60),
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onResetTimer) {
                    Text("Reset")
                }
                Button(onPauseTimer) {
                    Text("Pause")
                }
                Button(onStartTimer) {
                    Text("Start")
                }
            }

        }
    }
}


@Preview(showSystemUi = true)
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