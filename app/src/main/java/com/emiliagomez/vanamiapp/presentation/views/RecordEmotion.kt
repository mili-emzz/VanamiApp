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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.emiliagomez.vanamiapp.R
import com.emiliagomez.vanamiapp.presentation.viewmodels.EmotionData
import com.emiliagomez.vanamiapp.presentation.viewmodels.RecordViewModel
import com.emiliagomez.vanamiapp.ui.theme.BackgroundColor
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun RecordEmotionView(
    recordViewModel: RecordViewModel,
    onContinueClick: () -> Unit = {}
) {
    var currentPage by remember { mutableStateOf(0) }

    val emotionPages = listOf(
        listOf(
            EmotionData("1", "Feliz", R.drawable.happy.toString()),
            EmotionData("2", "Enojado", R.drawable.angry.toString()),
            EmotionData("3", "Triste", R.drawable.sad.toString()),
            EmotionData("4", "Preocupado", R.drawable.worried.toString()),
            EmotionData("5", "Neutral", R.drawable.neutral.toString()),
            EmotionData("6", "Molesto", R.drawable.upset.toString()),
            EmotionData("7", "Calmado", R.drawable.calm.toString()),
            EmotionData("8", "Alegre", R.drawable.joyful.toString())
        )
    )

    val currentEmotions = emotionPages[currentPage]
    val dateFormatter = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM", Locale("es", "ES"))
    val formattedDate = recordViewModel.currentDate.format(dateFormatter)

    Scaffold(
        containerColor = BackgroundColor,
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(20.dp))

                    Text(
                        text = "¿Cómo te sientes hoy?",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color(0xFF2C2C2C),
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = formattedDate,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF757575),
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Spacer(Modifier.height(48.dp))

                    // Círculo de emociones
                    Box(
                        modifier = Modifier.size(320.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        EmotionCircle(
                            emotions = currentEmotions,
                            selectedEmotion = recordViewModel.selectedEmotion,
                            onEmotionSelected = { emotion ->
                                recordViewModel.selectEmotion(emotion)
                            }
                        )
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Navegación entre páginas de emociones
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                if (currentPage > 0) currentPage--
                            },
                            enabled = currentPage > 0
                        ) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Anterior",
                                tint = if (currentPage > 0) Color(0xFF424242) else Color(0xFFCCCCCC)
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            emotionPages.indices.forEach { index ->
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (index == currentPage) Color(0xFFFEB4A7)
                                            else Color(0xFFE0E0E0)
                                        )
                                )
                            }
                        }

                        IconButton(
                            onClick = {
                                if (currentPage < emotionPages.size - 1) currentPage++
                            },
                            enabled = currentPage < emotionPages.size - 1
                        ) {
                            Icon(
                                Icons.Default.ArrowForward,
                                contentDescription = "Siguiente",
                                tint = if (currentPage < emotionPages.size - 1) Color(0xFF424242) else Color(0xFFCCCCCC)
                            )
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    // Botón continuar
                    Button(
                        onClick = onContinueClick,
                        enabled = recordViewModel.selectedEmotion != null,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFEB4A7),
                            disabledContainerColor = Color(0xFFE0E0E0)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(horizontal = 32.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(
                            "Continuar",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(Modifier.height(32.dp))
                }
            }
        }
    )
}

@Composable
fun EmotionCircle(
    emotions: List<EmotionData>,
    selectedEmotion: EmotionData?,
    onEmotionSelected: (EmotionData) -> Unit
) {
    val radius = 140f
    val angleStep = 360f / emotions.size

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        emotions.forEachIndexed { index, emotion ->
            val angle = Math.toRadians((angleStep * index).toDouble())
            val x = (radius * cos(angle)).toFloat()
            val y = (radius * sin(angle)).toFloat()

            val isSelected = selectedEmotion?.id == emotion.id
            val iconResId = emotion.imgUrl.toIntOrNull() ?: R.drawable.happy

            Box(
                modifier = Modifier
                    .offset(x = x.dp, y = y.dp)
                    .size(if (isSelected) 64.dp else 56.dp)
                    .clip(CircleShape)
                    .background(
                        if (isSelected) Color(0xFFFEB4A7) else Color.White
                    )
                    .clickable { onEmotionSelected(emotion) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = emotion.name,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(if (isSelected) 44.dp else 40.dp)
                )
            }
        }

        if (selectedEmotion != null) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val iconResId = selectedEmotion.imgUrl.toIntOrNull() ?: R.drawable.happy
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = selectedEmotion.name,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(48.dp)
                )
                Text(
                    text = selectedEmotion.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF2C2C2C),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}