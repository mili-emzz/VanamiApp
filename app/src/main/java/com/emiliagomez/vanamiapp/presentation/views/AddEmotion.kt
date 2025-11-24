package com.emiliagomez.vanamiapp.presentation.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emiliagomez.vanamiapp.ui.theme.Pink40
import com.emiliagomez.vanamiapp.ui.theme.Pink80

sealed class UploadState {
    object ImageNotSelected : UploadState()
    data class UploadingImage(val progress: Float) : UploadState()
    object UploadCompleted : UploadState()
}

@Composable
fun AddEmotionScreen() {
    var uploadState by remember { mutableStateOf<UploadState>(UploadState.UploadingImage(0.76f)) } // Changed for preview

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Pink80)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Publica más emociones",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = "Comparte imágenes para representar más emociones",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uploadState) {
                is UploadState.ImageNotSelected -> ImageNotSelectedContent()
                is UploadState.UploadingImage -> UploadingImageContent(state.progress)
                is UploadState.UploadCompleted -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(vertical = 64.dp)
                    ) {
                        // Aquí irá el contenido para el estado de subida completada
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { /* Lógica para subir la emoción */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Pink40)
        ) {
            Text(text = "Subir emoción", color = Color.White, fontSize = 18.sp)
        }
    }
}

@Composable
fun ImageNotSelectedContent() {
    var saveToFavorites by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(vertical = 64.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.CloudUpload,
            contentDescription = "Upload Icon",
            tint = Color.Gray,
            modifier = Modifier.size(48.dp)
        )
        Text(
            text = "Sube tu archivos aquí",
            color = Pink40,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = "PNG o JPG",
            color = Color.Gray,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 4.dp, bottom = 32.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Switch(
                checked = saveToFavorites,
                onCheckedChange = { saveToFavorites = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Pink40,
                    uncheckedThumbColor = Color.Gray
                )
            )
            Text(
                text = "Guardar en favoritos",
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun UploadingImageContent(progress: Float) {
    var saveToFavorites by remember { mutableStateOf(true) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(vertical = 64.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Image,
            contentDescription = "Uploading Image",
            tint = Color.Gray,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            color = Pink40,
            trackColor = Color.LightGray
        )
        Text(
            text = "${(progress * 100).toInt()}% completed",
            color = Color.Gray,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 4.dp)
        )
        Text(
            text = "Subiendo imagen...",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Switch(
                checked = saveToFavorites,
                onCheckedChange = { saveToFavorites = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Pink40,
                    uncheckedThumbColor = Color.Gray
                )
            )
            Text(
                text = "Guardar en favoritos",
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}