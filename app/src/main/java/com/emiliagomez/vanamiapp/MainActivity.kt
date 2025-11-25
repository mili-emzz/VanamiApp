package com.emiliagomez.vanamiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.emiliagomez.vanamiapp.presentation.views.CalendarView
import com.emiliagomez.vanamiapp.ui.theme.VanamiAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VanamiAppTheme {
                CalendarView(
                    onDayClick = { selectedDate ->
                        // Lógica al pulsar día
                    },
                    imageResId = R.drawable.info_content,
                    onDiaryClick = {

                    }
                )
            }
        }
    }
}
