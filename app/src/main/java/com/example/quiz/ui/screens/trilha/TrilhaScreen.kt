package com.example.quiz.ui.screens.trilha

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TrilhaScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize().padding(20.dp)) {
        StatsBar(streakDays = 12, xp = 340)
    }
}

@Preview(showBackground = true)
@Composable
fun TrilhaScreenPreview() {
    TrilhaScreen()
}

@Composable
private fun StatsBar(streakDays: Int, xp: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            CommitGraph()
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "$streakDays")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "$xp")
        }
    }
}

@Composable
private fun CommitGraph() {
    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
        val intensities = listOf(0.2f, 0.9f, 0.4f, 1f, 0.6f)
        intensities.forEach { intensity ->
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        color = Color(0xFF58A6FF).copy(alpha = intensity),
                        shape = RoundedCornerShape(2.dp),
                    )
            )
        }
    }
}
