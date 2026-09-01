package com.example.quiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.example.quiz.ui.screens.boasvindas.BoasVindasScreen
import com.example.quiz.ui.screens.pergunta.PerguntaScreen
import com.example.quiz.ui.screens.trilha.TrilhaScreen
import com.example.quiz.ui.theme.QuizTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuizApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

private enum class Tela { BOAS_VINDAS, TRILHA, PERGUNTA }

@Composable
fun QuizApp(modifier: Modifier = Modifier) {
    var telaAtual by rememberSaveable { mutableStateOf(Tela.BOAS_VINDAS) }

    when (telaAtual) {
        Tela.BOAS_VINDAS -> BoasVindasScreen(
            modifier = modifier,
            onContinuarComGithub = { telaAtual = Tela.TRILHA },
            onCriarContaComEmail = { telaAtual = Tela.TRILHA },
            onEntrar = { telaAtual = Tela.TRILHA },
        )
        Tela.TRILHA -> TrilhaScreen(
            modifier = modifier,
            onComecar = { telaAtual = Tela.PERGUNTA },
        )
        Tela.PERGUNTA -> PerguntaScreen(
            modifier = modifier,
            onFechar = { telaAtual = Tela.TRILHA },
            onConfirmar = { telaAtual = Tela.TRILHA },
        )
    }
}
