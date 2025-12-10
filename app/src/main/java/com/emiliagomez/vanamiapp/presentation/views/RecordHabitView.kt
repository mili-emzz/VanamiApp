package com.emiliagomez.vanamiapp.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emiliagomez.vanamiapp.components.habits.HabitCategoryComponent
import com.emiliagomez.vanamiapp.components.habits.HabitOption
import com.emiliagomez.vanamiapp.presentation.viewmodels.RecordViewModel
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordHabitView(recordViewModel: RecordViewModel, onBackClick: () -> Unit, onSaveSuccess: () -> Unit) {
    val habitCategories = listOf(
        "sleep" to "Horas de sueño",
        "food" to "Calidad de alimentación",
        "social" to "Conexión social",
        "energy" to "Energía",
        "thoughts" to "Pensamientos"
    )

    val dateFormatter = DateTimeFormatter.ofPattern("EEEE, d 'de' MMMM", Locale("es", "ES"))
    val formattedDate = recordViewModel.currentDate.format(dateFormatter)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "¿Qué has estado haciendo hoy?",
                        color = Color(0xFF8B4545),
                        fontSize = 16.sp
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFFFF0F0)
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color(0xFF8B4545)
                        )
                    }
                }
            )
        },
        containerColor = Color(0xFFFFF0F0)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Text(
                text = formattedDate,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF757575),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            HabitCategoryComponent(
                categoryTitle = "Horas de sueño",
                options = listOf(
                    HabitOption(Icons.Default.Bedtime, "+8 horas"),
                    HabitOption(Icons.Default.AccessTime, "6-8 horas"),
                    HabitOption(Icons.Default.SentimentNeutral, "3-5 horas"),
                    HabitOption(Icons.Default.SentimentDissatisfied, "-2 horas")
                ),
                selectedIndex = recordViewModel.selectedHabits["sleep"],
                onOptionSelected = { index ->
                    recordViewModel.selectHabit("sleep", index)
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            HabitCategoryComponent(
                categoryTitle = "Calidad de alimentación",
                options = listOf(
                    HabitOption(Icons.Default.FoodBank, "Saludable"),
                    HabitOption(Icons.Default.Restaurant, "Regular"),
                    HabitOption(Icons.Default.Fastfood, "Chatarra"),
                    HabitOption(Icons.Default.Close, "Poco o nada")
                ),
                selectedIndex = recordViewModel.selectedHabits["food"],
                onOptionSelected = { index ->
                    recordViewModel.selectHabit("food", index)
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            HabitCategoryComponent(
                categoryTitle = "Conexión social",
                options = listOf(
                    HabitOption(Icons.Default.Chat, "Sociable"),
                    HabitOption(Icons.Default.Message, "Introvertido"),
                    HabitOption(Icons.Default.ThumbUp, "Apoyo"),
                    HabitOption(Icons.Default.Close, "Conflicto")
                ),
                selectedIndex = recordViewModel.selectedHabits["social"],
                onOptionSelected = { index ->
                    recordViewModel.selectHabit("social", index)
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            HabitCategoryComponent(
                categoryTitle = "Energía",
                options = listOf(
                    HabitOption(Icons.Default.Star, "Mucha"),
                    HabitOption(Icons.Default.CheckCircle, "Suficiente"),
                    HabitOption(Icons.Default.Remove, "Poca"),
                    HabitOption(Icons.Default.Cancel, "Nada")
                ),
                selectedIndex = recordViewModel.selectedHabits["energy"],
                onOptionSelected = { index ->
                    recordViewModel.selectHabit("energy", index)
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            HabitCategoryComponent(
                categoryTitle = "Pensamientos",
                options = listOf(
                    HabitOption(Icons.Default.SentimentSatisfied, "Positivos"),
                    HabitOption(Icons.Default.ThumbDown, "Negativos"),
                    HabitOption(Icons.Default.Cloud, "Intrusivos"),
                    HabitOption(Icons.Default.Psychology, "Niebla mental")
                ),
                selectedIndex = recordViewModel.selectedHabits["thoughts"],
                onOptionSelected = { index ->
                    recordViewModel.selectHabit("thoughts", index)
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Nota",
                color = Color(0xFF8B4545),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            TextField(
                value = recordViewModel.note,
                onValueChange = { recordViewModel.updateNote(it) },
                placeholder = { Text("Agregar nota...", color = Color(0xFFD4B5B5)) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color(0xFF8B4545),
                    unfocusedTextColor = Color(0xFF8B4545)
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    recordViewModel.saveRecord(
                        onSuccess = onSaveSuccess,
                        onError = {}
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF4C2C2)
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    "Guardar",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    if (recordViewModel.showAlert) {
        AlertDialog(
            onDismissRequest = { recordViewModel.closeAlert() },
            title = { Text("Información") },
            text = { Text(recordViewModel.alertMessage) },
            confirmButton = {
                TextButton(onClick = { recordViewModel.closeAlert() }) {
                    Text("OK")
                }
            }
        )
    }
}