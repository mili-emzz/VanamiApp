package com.emiliagomez.vanamiapp.components.habits

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class HabitOption(
    val icon: ImageVector,
    val label: String
)

@Composable
fun HabitCategoryComponent(categoryTitle: String, options: List<HabitOption>, selectedIndex: Int?, onOptionSelected: (Int) -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = categoryTitle,
            color = Color(0xFF8B4545),
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEachIndexed { index, option ->
                HabitOptionCard(
                    icon = option.icon,
                    label = option.label,
                    isSelected = selectedIndex == index,
                    onClick = { onOptionSelected(index) }
                )
            }
        }
    }
}