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

@Composable
fun QuizApp(modifier: Modifier = Modifier) {
    var mostrandoPergunta by rememberSaveable { mutableStateOf(false) }

    if (mostrandoPergunta) {
        PerguntaScreen(
            modifier = modifier,
            onFechar = { mostrandoPergunta = false },
            onConfirmar = { mostrandoPergunta = false },
        )
    } else {
        TrilhaScreen(
            modifier = modifier,
            onComecar = { mostrandoPergunta = true },
        )
    }
}
