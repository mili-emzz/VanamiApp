package com.emiliagomez.vanamiapp.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.YearMonth




@Composable
fun CalendarView(
    month: Int = LocalDate.now().monthValue,
    year: Int = LocalDate.now().year,
    onDayClick: (LocalDate) -> Unit = {},
    imageResId: Int,
    onDiaryClick: () -> Unit
) {
    Scaffold(
        containerColor = Color(0xFFF7F6F4),
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 12.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ///saludo, pendiente cambiar con nombre del usuario
                Text(
                    text = "Buen día, Vanessa",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFF2C2C2C),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                )

                //meses
                Row(
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    listOf("Noviembre", "Diciembre", "Enero").forEach { mes ->
                        Button(
                            onClick = { /**/ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (mes == "Noviembre") Color(0xFFF4B2B7) else Color(0xFFEAEAEA)
                            ),
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier.weight(1f).height(36.dp)
                        ) {
                            Text(mes, color = Color.Black)
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Días de la semana
                val daysOfWeek = listOf("Dom", "Lun", "Mar", "Mie", "Jue", "Vie", "Sab")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp, start = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    daysOfWeek.forEach { day ->
                        Text(
                            text = day,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF424242),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(Modifier.height(4.dp))

                // Calendario grid
                val firstDayOfMonth = LocalDate.of(year, month, 1)
                val yearMonth = YearMonth.of(year, month)
                val daysInMonth = yearMonth.lengthOfMonth()
                val firstDayOfWeekIndex = firstDayOfMonth.dayOfWeek.value % 7
                val today = LocalDate.now()
                val daysGrid = buildList {
                    repeat(firstDayOfWeekIndex) { add(null) }
                    for (day in 1..daysInMonth) {
                        add(LocalDate.of(year, month, day))
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, shape = MaterialTheme.shapes.medium)
                        .padding(16.dp)
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(7),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                    ) {
                        items(daysGrid) { date ->
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(
                                        color = if (date == today) Color(0xFFF4B2B7) else Color(0xFFF2F2F2),
                                        shape = MaterialTheme.shapes.large
                                    )
                                    .clickable(enabled = date != null) {
                                        date?.let { onDayClick(it) }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                date?.let {
                                    Text(it.dayOfMonth.toString(), color = Color.Black)
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                InfoContent(imageResId = imageResId, onDiaryClick = onDiaryClick)
                Spacer(Modifier.height(16.dp))
            }
        }
    )
}





@Composable
fun InfoContent(imageResId: Int, onDiaryClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFE6E6), shape = MaterialTheme.shapes.large)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text("¿Cómo te sientes hoy?", fontWeight = FontWeight.Bold)
            Text(
                "Registra cómo te sientes hoy para una mejor calidad de vida",
                style = MaterialTheme.typography.bodySmall
            )
            Button(
                onClick = onDiaryClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFB7B7), contentColor = Color.White
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Diario")
            }
        }
        Spacer(Modifier.width(12.dp))
        // Espacio para imagen
        Box(
            Modifier
                .size(64.dp)
        ) {
            Icon(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(64.dp)
            )
        }
    }
}
