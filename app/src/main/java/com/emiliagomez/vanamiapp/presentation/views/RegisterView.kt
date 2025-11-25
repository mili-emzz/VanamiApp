package com.emiliagomez.vanamiapp.presentation.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emiliagomez.vanamiapp.ui.theme.BackgroundColor
import com.emiliagomez.vanamiapp.ui.theme.MainColor
import com.emiliagomez.vanamiapp.R
import com.emiliagomez.vanamiapp.components.BottomNav
import com.emiliagomez.vanamiapp.components.ButtonContainers
import com.emiliagomez.vanamiapp.components.FormTextField
import com.emiliagomez.vanamiapp.components.LoginImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterView(
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = BackgroundColor,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Únete a la comunidad",
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = Color.Black)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 2.dp)
            )
        },
        bottomBar = {
            BottomNav()
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 40.dp, vertical = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LoginImage()

            Spacer(modifier = Modifier.height(8.dp))

            FormsView()

            Spacer(modifier = Modifier.height(16.dp))

            ButtonContainers("Registrarse", isRegister = true)
        }
    }
}

@Composable
fun FormsView() {
    var name by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
    ){
        FormTextField(
            name,
            { name = it },
            label = "Nombre"
        )

        FormTextField(
            lastname,
            { lastname = it },
            label = "Apellidos"
        )

        FormTextField(
            email,
            { email = it },
            label = "Correo eléctronico"
        )

        FormTextField(
            password,
            { password = it },
            label = "Contraseña",
            keyboardType = KeyboardType.Password,
            isPasswordVisible = isPasswordVisible,
            onVisibilityChange = {isPasswordVisible = !isPasswordVisible}
        )

        FormTextField(
            confirmPassword,
            { confirmPassword = it },
            label = "Confirmar Contraseña",
            keyboardType = KeyboardType.Password,
            isPasswordVisible = isConfirmPasswordVisible,
            onVisibilityChange = {isConfirmPasswordVisible = !isConfirmPasswordVisible}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterViewPreview() {
    RegisterView()
}