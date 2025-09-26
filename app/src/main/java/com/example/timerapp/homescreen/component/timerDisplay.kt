package com.example.timerapp.homescreen.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.timerapp.ui.theme.TimerAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerDisplay(
    hour: Int,
    minutes: Int,
    second: Int,
    onHourChange: (Int) -> Unit,
    onMinutesChange: (Int) -> Unit,
    onSecondChange: (Int) -> Unit,
    onStartTimer: () -> Unit,
    onPauseTimer: () -> Unit,
    onResetTimer: () -> Unit,
    modifier: Modifier = Modifier,
    isRunning: Boolean = false,
    remainingSeconds: Long = 0,
    totalDuration: Long = 0
) {

    val progress = if (totalDuration > 0) {
        (totalDuration - remainingSeconds).toFloat() / totalDuration.toFloat()
    } else 0f

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        label = "progress"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(250.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = String.format("%02d:%02d:%02d", hour, minutes, second),
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.onBackground
                    )
                )
                
                LinearProgressIndicator(
                    progress = animatedProgress,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(vertical = 16.dp)
                        .height(8.dp),
                    color = colorScheme.primary,
                    trackColor = colorScheme.surfaceVariant
                )
                
                if (isRunning) {
                    Text(
                        "Time Remaining: ${formatTime(remainingSeconds)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Input Fields
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            TimeInputField(
                value = hour,
                onValueChange = onHourChange,
                label = "H",
                enabled = !isRunning,
                modifier = Modifier.weight(1f)
            )
            Text(":", style = MaterialTheme.typography.headlineMedium)
            TimeInputField(
                value = minutes,
                onValueChange = onMinutesChange,
                label = "M",
                enabled = !isRunning,
                modifier = Modifier.weight(1f)
            )
            Text(":", style = MaterialTheme.typography.headlineMedium)
            TimeInputField(
                value = second,
                onValueChange = onSecondChange,
                label = "S",
                enabled = !isRunning,
                modifier = Modifier.weight(1f)
            )
        }

        // Control Buttons
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // Reset Button
            Button(
                onClick = onResetTimer,
                enabled = !isRunning && (hour > 0 || minutes > 0 || second > 0),
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.surfaceVariant,
                    contentColor = colorScheme.onSurfaceVariant
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Reset", style = MaterialTheme.typography.titleMedium)
            }
            
            // Start/Resume Button
            Button(
                onClick = onStartTimer,
                enabled = !isRunning && (hour > 0 || minutes > 0 || second > 0),
                modifier = Modifier
                    .weight(1.5f)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.primary,
                    contentColor = colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    if (remainingSeconds > 0) "Resume" else "Start",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
            
            // Pause Button
            Button(
                onClick = onPauseTimer,
                enabled = isRunning,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.secondaryContainer,
                    contentColor = colorScheme.onSecondaryContainer
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Pause", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimeInputField(
    value: Int,
    onValueChange: (Int) -> Unit,
    label: String,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = if (value == 0) "" else value.toString(),
        onValueChange = { 
            if (it.isEmpty() || it.all { char -> char.isDigit() }) {
                onValueChange(it.toIntOrNull() ?: 0)
            }
        },
        modifier = modifier.padding(horizontal = 4.dp),
        label = { Text(label) },
        singleLine = true,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        textStyle = MaterialTheme.typography.headlineMedium.copy(
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        ),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorScheme.primary,
            unfocusedBorderColor = colorScheme.outlineVariant,
            disabledBorderColor = colorScheme.outlineVariant.copy(alpha = 0.5f),
            disabledTextColor = colorScheme.onSurfaceVariant,
            disabledLabelColor = colorScheme.onSurfaceVariant
        )
    )
}

private fun formatTime(seconds: Long): String {
    val h = seconds / 3600
    val m = (seconds % 3600) / 60
    val s = seconds % 60
    return if (h > 0) {
        String.format("%d h %02d m %02d s", h, m, s)
    } else if (m > 0) {
        String.format("%d m %02d s", m, s)
    } else {
        String.format("%d s", s)
    }
}

@Preview(showSystemUi = true)
@Composable
fun TimerDisplayPreview() {
    TimerAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = colorScheme.background
        ) {
            TimerDisplay(
                hour = 1,
                minutes = 30,
                second = 45,
                onHourChange = {},
                onMinutesChange = {},
                onSecondChange = {},
                onStartTimer = {},
                onPauseTimer = {},
                onResetTimer = {}
            )
        }
    }
}