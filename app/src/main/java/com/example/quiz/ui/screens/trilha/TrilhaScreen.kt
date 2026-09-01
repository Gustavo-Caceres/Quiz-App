package com.example.quiz.ui.screens.trilha

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val BackgroundColor = Color(0xFF0D1117)
private val SurfaceColor = Color(0xFF161B22)
private val TextMutedColor = Color(0xFF8B949E)
private val BorderColor = Color(0xFF262D38)
private val WarningColor = Color(0xFFE3B341)

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
fun TrilhaScreen(modifier: Modifier = Modifier, onComecar: () -> Unit = {}) {
    val context = LocalContext.current
    var tentativasBloqueadas by remember { mutableStateOf(0) }
    var busca by remember { mutableStateOf("") }

    val nodes = listOf(
        LessonNode(label = "arrays", state = NodeState.COMPLETED),
        LessonNode(label = "loops aninhados", state = NodeState.COMPLETED),
        LessonNode(label = "for", state = NodeState.CURRENT),
        LessonNode(label = "while", state = NodeState.LOCKED),
        LessonNode(label = "break/continue", state = NodeState.LOCKED),
        LessonNode(label = "revisão", state = NodeState.LOCKED),
    )
    val nodesFiltrados = nodes.filter { it.label.contains(busca, ignoreCase = true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(20.dp),
    ) {
        StatsBar(streakDays = 12, xp = 340)
        Spacer(modifier = Modifier.height(20.dp))
        UnitHeader(unitNumber = 4, progressText = "6/10", subtitle = "LAÇOS & ITERAÇÃO — JS")
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = busca,
            onValueChange = { busca = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Buscar lição", color = TextMutedColor, fontSize = 14.sp) },
            leadingIcon = { Text(text = "🔍", fontSize = 14.sp) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color(0xFF58A6FF),
                unfocusedBorderColor = BorderColor,
                cursorColor = Color(0xFF58A6FF),
            ),
        )
        Spacer(modifier = Modifier.height(20.dp))
        if (nodesFiltrados.isEmpty()) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = SurfaceColor,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, BorderColor),
            ) {
                Text(
                    text = "Nenhuma lição encontrada para \"$busca\"",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp),
                    color = TextMutedColor,
                    fontSize = 13.sp,
                )
            }
        } else {
            LearningPath(
                nodes = nodesFiltrados,
                onComecar = onComecar,
                onNodeBloqueadoClick = {
                    tentativasBloqueadas++
                    Toast.makeText(context, "Essa lição ainda está bloqueada!", Toast.LENGTH_SHORT).show()
                },
            )
        }
        if (tentativasBloqueadas > 0) {
            Spacer(modifier = Modifier.height(16.dp))
            Surface(
                color = WarningColor.copy(alpha = 0.12f),
                shape = RoundedCornerShape(50),
                border = BorderStroke(1.dp, WarningColor.copy(alpha = 0.4f)),
            ) {
                Text(
                    text = "🔒 lição bloqueada — $tentativasBloqueadas tentativa(s)",
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                    color = WarningColor,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TrilhaScreenPreview() {
    TrilhaScreen()
}

@Composable
private fun StatsBar(streakDays: Int, xp: Int, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = SurfaceColor,
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, BorderColor),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CommitGraph()
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "$streakDays", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "dias", color = TextMutedColor, fontSize = 12.sp)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "$xp", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "xp", color = TextMutedColor, fontSize = 12.sp)
            }
        }
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
        border = BorderStroke(1.dp, BorderColor),
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Column {
                Text(
                    text = "Unidade $unitNumber",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp,
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = subtitle,
                    color = Color(0xFF7D9BFF),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    letterSpacing = 0.8.sp,
                )
            }

            Surface(
                modifier = Modifier.align(Alignment.TopEnd),
                color = BackgroundColor,
                shape = RoundedCornerShape(50),
                border = BorderStroke(1.dp, BorderColor),
            ) {
                Text(
                    text = progressText,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    color = TextMutedColor,
                    fontWeight = FontWeight.Medium,
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
    val borderColor = when (node.state) {
        NodeState.CURRENT -> Color(0xFFA9C7FF)
        else -> BorderColor
    }
    val content = when (node.state) {
        NodeState.COMPLETED -> "✓"
        NodeState.CURRENT -> node.label
        NodeState.LOCKED -> "🔒"
    }
    val size = if (node.state == NodeState.CURRENT) 60.dp else 56.dp

    Box(
        modifier = Modifier
            .size(size)
            .background(background, CircleShape)
            .border(BorderStroke(2.dp, borderColor), CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = content,
            color = if (node.state == NodeState.LOCKED) TextMutedColor else Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = if (node.state == NodeState.CURRENT) 14.sp else 16.sp,
        )
    }
}

@Composable
private fun LearningPath(
    nodes: List<LessonNode>,
    onComecar: () -> Unit,
    onNodeBloqueadoClick: () -> Unit,
) {
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
                val columnModifier = if (node.state == NodeState.LOCKED) {
                    Modifier.clickable(onClick = onNodeBloqueadoClick)
                } else {
                    Modifier
                }
                Column(
                    modifier = columnModifier,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    LessonNodeCircle(node = node)
                    if (node.state != NodeState.CURRENT) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = node.label, color = TextMutedColor, fontSize = 11.sp)
                    }
                    if (node.state == NodeState.CURRENT) {
                        Spacer(modifier = Modifier.height(8.dp))
                        StartPill(onClick = onComecar)
                    }
                }
            }
        }
    }
}

@Composable
private fun StartPill(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF58A6FF)),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
    ) {
        Text(text = "COMEÇAR", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
    }
}