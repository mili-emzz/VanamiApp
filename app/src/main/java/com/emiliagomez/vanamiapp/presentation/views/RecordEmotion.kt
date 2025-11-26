package com.emiliagomez.vanamiapp.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.emiliagomez.vanamiapp.R
import com.emiliagomez.vanamiapp.ui.theme.VanamiAppTheme
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun AddEmotionView(
    onEmotionSelected: (String) -> Unit = {},
    onBackClick: () -> Unit = {},
    onNextClick: () -> Unit = {}
) {
    Scaffold(
        containerColor = Color(0xFFFFF5F0),
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Título arriba
                Text(
                    text = "¿Cómo te sientes hoy?",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFF2C2C2C),
                    modifier = Modifier.padding(top = 32.dp)
                )

                // Círculo de emociones
                Box(
                    modifier = Modifier
                        .size(320.dp)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    EmotionCircle(onEmotionSelected = onEmotionSelected)
                }

                // navegar entre emociones
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Atrás",
                            tint = Color(0xFF424242)
                        )
                    }
                    IconButton(onClick = onNextClick) {
                        Icon(
                            Icons.Default.ArrowForward,
                            contentDescription = "Siguiente",
                            tint = Color(0xFF424242)
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun EmotionCircle(onEmotionSelected: (String) -> Unit) {
    // lista de emociones
    val emotions = listOf(
        "Feliz" to R.drawable.ic_launcher_foreground,
        "Triste" to R.drawable.ic_launcher_foreground,
        "Enojado" to R.drawable.ic_launcher_foreground,
        "Sorprendido" to R.drawable.ic_launcher_foreground,
        "Cansado" to R.drawable.ic_launcher_foreground,
        "Relajado" to R.drawable.ic_launcher_foreground,
        "Ansioso" to R.drawable.ic_launcher_foreground,
        "Emocionado" to R.drawable.ic_launcher_foreground
    )

    val radius = 140f // Radio del círculo en el que se distribuyen los iconos
    val angleStep = 360f / emotions.size

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        emotions.forEachIndexed { index, (emotion, iconRes) ->
            val angle = Math.toRadians((angleStep * index).toDouble())
            val x = (radius * cos(angle)).toFloat()
            val y = (radius * sin(angle)).toFloat()

            Box(
                modifier = Modifier
                    .offset(x = x.dp, y = y.dp)
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable { onEmotionSelected(emotion) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = emotion,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddEmotionViewPreview() {
    VanamiAppTheme {
        AddEmotionView()
    }
}
