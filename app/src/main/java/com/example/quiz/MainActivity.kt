package com.example.quiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.quiz.ui.screens.boasvindas.BoasVindasScreen
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
    BoasVindasScreen(modifier = modifier)
}
