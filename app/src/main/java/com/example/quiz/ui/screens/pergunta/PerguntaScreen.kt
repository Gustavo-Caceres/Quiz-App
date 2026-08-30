package com.example.quiz.ui.screens.pergunta

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.compositeOver
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
private val TrackColor = Color(0xFF21262D)

private val AccentColor = Color(0xFF7D9BFF)
private val SelectedColor = Color(0xFF58A6FF)
private val CloseColor = Color(0xFFF85149)
private val EnergyColor = Color(0xFFE3B341)
private val EnergyEmptyColor = Color(0xFF30363D)

private val CodePlainColor = Color(0xFFC9D1D9)
private val CodeKeywordColor = Color(0xFFBC8CFF)
private val CodeNumberColor = Color(0xFF79C0FF)
private val CodeGlobalColor = Color(0xFFD2A8FF)
private val CodePropertyColor = Color(0xFF79C0FF)
private val CodeLineNumberColor = Color(0xFF484F58)

/** Como um trecho de codigo e pintado. O highlight vem pronto nos dados da pergunta. */
private enum class CodeStyle { PLAIN, KEYWORD, NUMBER, GLOBAL, PROPERTY }

private data class CodeToken(val text: String, val style: CodeStyle = CodeStyle.PLAIN)

private data class Pergunta(
    val numero: Int,
    val total: Int,
    val energia: Int,
    val energiaMaxima: Int,
    val enunciado: String,
    val codigo: List<List<CodeToken>>,
    val alternativas: List<String>,
)

private val PerguntaExemplo = Pergunta(
    numero = 7,
    total = 12,
    energia = 2,
    energiaMaxima = 3,
    enunciado = "Qual é a saída no console?",
    codigo = listOf(
        listOf(
            CodeToken("const", CodeStyle.KEYWORD),
            CodeToken(" arr = ["),
            CodeToken("1", CodeStyle.NUMBER),
            CodeToken(", "),
            CodeToken("2", CodeStyle.NUMBER),
            CodeToken(", "),
            CodeToken("3", CodeStyle.NUMBER),
            CodeToken("];"),
        ),
        listOf(
            CodeToken("arr.push("),
            CodeToken("4", CodeStyle.NUMBER),
            CodeToken(");"),
        ),
        listOf(
            CodeToken("console", CodeStyle.GLOBAL),
            CodeToken(".log(arr."),
            CodeToken("length", CodeStyle.PROPERTY),
            CodeToken(");"),
        ),
    ),
    alternativas = listOf("3", "4", "undefined", "Error"),
)

@Composable
fun PerguntaScreen(
    modifier: Modifier = Modifier,
    onFechar: () -> Unit = {},
    onConfirmar: (Int) -> Unit = {},
) {
    var selecionada by rememberSaveable { mutableStateOf<Int?>(null) }

    PerguntaContent(
        pergunta = PerguntaExemplo,
        selecionada = selecionada,
        onSelecionar = { selecionada = it },
        onFechar = onFechar,
        onConfirmar = { selecionada?.let(onConfirmar) },
        modifier = modifier,
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PerguntaScreenPreview() {
    PerguntaScreen()
}

@Composable
private fun PerguntaContent(
    pergunta: Pergunta,
    selecionada: Int?,
    onSelecionar: (Int) -> Unit,
    onFechar: () -> Unit,
    onConfirmar: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .safeDrawingPadding()
            .padding(horizontal = 20.dp, vertical = 16.dp),
    ) {
        TopBar(
            progresso = pergunta.numero.toFloat() / pergunta.total.toFloat(),
            energia = pergunta.energia,
            energiaMaxima = pergunta.energiaMaxima,
            onFechar = onFechar,
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "questão ${pergunta.numero} de ${pergunta.total}",
                color = TextMutedColor,
                fontFamily = FontFamily.Monospace,
                fontSize = 12.sp,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = pergunta.enunciado,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
            Spacer(modifier = Modifier.height(20.dp))
            CodeBlock(linhas = pergunta.codigo)
            Spacer(modifier = Modifier.height(24.dp))
            pergunta.alternativas.forEachIndexed { index, alternativa ->
                if (index > 0) Spacer(modifier = Modifier.height(12.dp))
                AlternativaCard(
                    letra = 'A' + index,
                    texto = alternativa,
                    selecionada = index == selecionada,
                    onClick = { onSelecionar(index) },
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        Button(
            onClick = onConfirmar,
            enabled = selecionada != null,
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AccentColor,
                contentColor = BackgroundColor,
                disabledContainerColor = SurfaceColor,
                disabledContentColor = TextMutedColor,
            ),
            contentPadding = PaddingValues(horizontal = 28.dp, vertical = 14.dp),
        ) {
            Text(text = "Confirmar", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
    }
}

@Composable
private fun TopBar(
    progresso: Float,
    energia: Int,
    energiaMaxima: Int,
    onFechar: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(50))
                .clickable(onClick = onFechar),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "✕",
                color = CloseColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .clip(RoundedCornerShape(50))
                .background(TrackColor),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progresso.coerceIn(0f, 1f))
                    .fillMaxHeight()
                    .background(AccentColor, RoundedCornerShape(50)),
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
            repeat(energiaMaxima) { index ->
                EnergyBolt(ativa = index < energia)
            }
        }
    }
}

@Composable
private fun EnergyBolt(ativa: Boolean) {
    val color = if (ativa) EnergyColor else EnergyEmptyColor
    Canvas(modifier = Modifier.size(width = 11.dp, height = 15.dp)) {
        val w = size.width
        val h = size.height
        val bolt = Path().apply {
            moveTo(0.62f * w, 0f)
            lineTo(0.08f * w, 0.58f * h)
            lineTo(0.45f * w, 0.58f * h)
            lineTo(0.38f * w, h)
            lineTo(0.92f * w, 0.40f * h)
            lineTo(0.55f * w, 0.40f * h)
            close()
        }
        drawPath(path = bolt, color = color)
    }
}

@Composable
private fun CodeBlock(linhas: List<List<CodeToken>>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceColor)
            .padding(horizontal = 14.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        linhas.forEachIndexed { index, tokens ->
            Row {
                Text(
                    text = "${index + 1}",
                    modifier = Modifier.width(16.dp),
                    color = CodeLineNumberColor,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 13.sp,
                    textAlign = TextAlign.End,
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = tokens.toAnnotatedString(),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 13.sp,
                )
            }
        }
    }
}

private fun List<CodeToken>.toAnnotatedString(): AnnotatedString = buildAnnotatedString {
    forEach { token ->
        withStyle(SpanStyle(color = token.style.color())) {
            append(token.text)
        }
    }
}

private fun CodeStyle.color(): Color = when (this) {
    CodeStyle.PLAIN -> CodePlainColor
    CodeStyle.KEYWORD -> CodeKeywordColor
    CodeStyle.NUMBER -> CodeNumberColor
    CodeStyle.GLOBAL -> CodeGlobalColor
    CodeStyle.PROPERTY -> CodePropertyColor
}

@Composable
private fun AlternativaCard(
    letra: Char,
    texto: String,
    selecionada: Boolean,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(12.dp)
    val containerColor = if (selecionada) {
        SelectedColor.copy(alpha = 0.10f).compositeOver(SurfaceColor)
    } else {
        SurfaceColor
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(containerColor)
            .border(
                width = if (selecionada) 2.dp else 1.dp,
                color = if (selecionada) SelectedColor else BorderColor,
                shape = shape,
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(if (selecionada) SelectedColor else TrackColor),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = letra.toString(),
                color = if (selecionada) BackgroundColor else TextMutedColor,
                fontFamily = FontFamily.Monospace,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Text(
            text = texto,
            color = Color.White,
            fontFamily = FontFamily.Monospace,
            fontSize = 15.sp,
        )
    }
}
