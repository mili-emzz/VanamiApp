package com.emiliagomez.vanamiapp.presentation.views

import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp



@Composable


@Preview
fun CalendarView() {
    val daysOfWeek = listOf("Dom", "Lun", "Mar", "Mie", "Jue", "Vie", "Sab")
    val daysInMonth = 30   // O calcula dinámicamente
    val daysList = (1..daysInMonth).map { it.toString() }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Elemento arriba del calendario
        Text(
            text = "Buen día, Vanessa",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            daysOfWeek.forEach { day ->
                Text(text = day)
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)
        ) {
            items(daysList) { day ->
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(4.dp)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                        .clickable { /* Aquí navegas o seleccionas el día */ },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = day)
                }
            }
        }

        Text(
            text = "¿Cómo te sientes hoy?",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
    }
}
