package com.example.quiz.ui.screens.boasvindas

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val BackgroundColor = Color(0xFF0D1117)
private val SurfaceColor = Color(0xFF161B22)
private val TextMutedColor = Color(0xFF8B949E)
private val BorderColor = Color(0xFF262D38)
private val AccentColor = Color(0xFF7D9BFF)
private val DotColor = Color(0xFF30363D)

private val CodeCommentColor = Color(0xFF6E7681)
private val CodeKeywordColor = Color(0xFFBC8CFF)
private val CodePlainColor = Color(0xFFC9D1D9)
private val CodeStringColor = Color(0xFFE3B341)

@Composable
fun BoasVindasScreen(
    modifier: Modifier = Modifier,
    onContinuarComGithub: () -> Unit = {},
    onCriarContaComEmail: () -> Unit = {},
    onEntrar: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .safeDrawingPadding()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(1f))

        LogoBadge()
        Spacer(modifier = Modifier.height(28.dp))
        Titulo()
        Spacer(modifier = Modifier.height(16.dp))
        Subtitulo()
        Spacer(modifier = Modifier.height(28.dp))
        CodeBlock()

        Spacer(modifier = Modifier.weight(1f))

        BotaoGithub(onClick = onContinuarComGithub)
        Spacer(modifier = Modifier.height(12.dp))
        BotaoEmail(onClick = onCriarContaComEmail)
        Spacer(modifier = Modifier.height(20.dp))
        RodapeEntrar(onEntrar = onEntrar)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BoasVindasScreenPreview() {
    BoasVindasScreen()
}

@Composable
private fun LogoBadge() {
    Box(
        modifier = Modifier
            .size(72.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(SurfaceColor),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = AccentColor)) { append("</") }
                withStyle(SpanStyle(color = CodeStringColor)) { append(">") }
            },
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
        )
    }
}

@Composable
private fun Titulo() {
    Text(
        text = buildAnnotatedString {
            append("Programar vira ")
            withStyle(SpanStyle(color = AccentColor)) { append("hábito") }
            append(" diário.")
        },
        color = Color.White,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun Subtitulo() {
    Text(
        text = "Lições curtas, tipo quiz, para treinar lógica, sintaxe e boas práticas todo dia.",
        color = TextMutedColor,
        fontSize = 14.sp,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun CodeBlock() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(SurfaceColor)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            repeat(3) {
                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .clip(CircleShape)
                        .background(DotColor),
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "// hoje, 2 min",
            color = CodeCommentColor,
            fontFamily = FontFamily.Monospace,
            fontSize = 13.sp,
        )
        CodeLine(
            buildAnnotatedString {
                withStyle(SpanStyle(color = CodeKeywordColor)) { append("function") }
                withStyle(SpanStyle(color = CodePlainColor)) { append(" streak() {") }
            },
        )
        CodeLine(
            buildAnnotatedString {
                withStyle(SpanStyle(color = CodePlainColor)) { append("  ") }
                withStyle(SpanStyle(color = CodeKeywordColor)) { append("return") }
                withStyle(SpanStyle(color = CodePlainColor)) { append(" ") }
                withStyle(SpanStyle(color = CodeStringColor)) { append("\"dia 12\"") }
                withStyle(SpanStyle(color = CodePlainColor)) { append(";") }
            },
        )
        CodeLine(
            buildAnnotatedString {
                withStyle(SpanStyle(color = CodePlainColor)) { append("}") }
            },
        )
    }
}

@Composable
private fun CodeLine(texto: AnnotatedString) {
    Text(
        text = texto,
        fontFamily = FontFamily.Monospace,
        fontSize = 13.sp,
    )
}

@Composable
private fun BotaoGithub(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = AccentColor,
            contentColor = BackgroundColor,
        ),
        contentPadding = PaddingValues(vertical = 16.dp),
    ) {
        Text(text = "Continuar com GitHub", fontWeight = FontWeight.Bold, fontSize = 15.sp)
    }
}

@Composable
private fun BotaoEmail(onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, BorderColor),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
        contentPadding = PaddingValues(vertical = 16.dp),
    ) {
        Text(text = "Criar conta com e-mail", fontWeight = FontWeight.Bold, fontSize = 15.sp)
    }
}

@Composable
private fun RodapeEntrar(onEntrar: () -> Unit) {
    Row {
        Text(text = "já tem conta? ", color = TextMutedColor, fontSize = 13.sp)
        Text(
            text = "Entrar",
            color = AccentColor,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            modifier = Modifier.clickable(onClick = onEntrar),
        )
    }
}
