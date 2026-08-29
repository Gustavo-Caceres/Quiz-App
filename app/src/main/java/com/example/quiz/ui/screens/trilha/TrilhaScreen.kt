package com.example.quiz.ui.screens.trilha

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val BackgroundColor = Color(0xFF0D1117)
private val SurfaceColor = Color(0xFF161B22)
private val TextMutedColor = Color(0xFF8B949E)

private val CommitGraphColors = listOf(
    Color(0xFF161B22),
    Color(0xFF0E4429),
    Color(0xFF006D32),
    Color(0xFF26A641),
    Color(0xFF39D353),
)

private enum class NodeState { COMPLETED, CURRENT, LOCKED }

private data class LessonNode(val label: String, val state: NodeState)

@Composable
fun TrilhaScreen(modifier: Modifier = Modifier) {
    val nodes = listOf(
        LessonNode(label = "", state = NodeState.COMPLETED),
        LessonNode(label = "", state = NodeState.COMPLETED),
        LessonNode(label = "for", state = NodeState.CURRENT),
        LessonNode(label = "", state = NodeState.LOCKED),
        LessonNode(label = "", state = NodeState.LOCKED),
        LessonNode(label = "", state = NodeState.LOCKED),
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(20.dp),
    ) {
        StatsBar(streakDays = 12, xp = 340)
        Spacer(modifier = Modifier.height(20.dp))
        UnitHeader(unitNumber = 4, progressText = "6/10", subtitle = "LAÇOS & ITERAÇÃO — JS")
        Spacer(modifier = Modifier.height(20.dp))
        LearningPath(nodes = nodes)
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
            Text(text = "$streakDays", color = Color.White, fontWeight = FontWeight.Bold)
        }
        Text(text = "$xp", color = Color.White, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun CommitGraph() {
    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
        val levels = listOf(1, 4, 2, 3, 4)
        levels.forEach { level ->
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        color = CommitGraphColors[level],
                        shape = RoundedCornerShape(2.dp),
                    )
            )
        }
    }
}

@Composable
private fun UnitHeader(unitNumber: Int, progressText: String, subtitle: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = SurfaceColor,
        shape = RoundedCornerShape(14.dp),
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Column {
                Text(
                    text = "Unidade $unitNumber",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = subtitle, color = TextMutedColor, fontSize = 12.sp)
            }

            Surface(
                modifier = Modifier.align(Alignment.TopEnd),
                color = BackgroundColor,
                shape = RoundedCornerShape(50),
            ) {
                Text(
                    text = progressText,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    color = TextMutedColor,
                    fontSize = 12.sp,
                )
            }
        }
    }
}

@Composable
private fun LessonNodeCircle(node: LessonNode) {
    val background = when (node.state) {
        NodeState.COMPLETED -> Color(0xFF3FB950)
        NodeState.CURRENT -> Color(0xFF58A6FF)
        NodeState.LOCKED -> SurfaceColor
    }
    val content = when (node.state) {
        NodeState.COMPLETED -> "✓"
        NodeState.CURRENT -> node.label
        NodeState.LOCKED -> "🔒"
    }

    Box(
        modifier = Modifier
            .size(56.dp)
            .background(background, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = content,
            color = if (node.state == NodeState.LOCKED) TextMutedColor else Color.White,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun LearningPath(nodes: List<LessonNode>) {
    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        nodes.forEachIndexed { index, node ->
            val alignment = when {
                node.state == NodeState.CURRENT -> Alignment.Center
                index % 2 == 0 -> Alignment.CenterEnd
                else -> Alignment.CenterStart
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                contentAlignment = alignment,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    LessonNodeCircle(node = node)
                    if (node.state == NodeState.CURRENT) {
                        Spacer(modifier = Modifier.height(8.dp))
                        StartPill()
                    }
                }
            }
        }
    }
}

@Composable
private fun StartPill() {
    Surface(
        color = Color(0xFF58A6FF),
        shape = RoundedCornerShape(50),
    ) {
        Text(
            text = "COMEÇAR",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
        )
    }
}