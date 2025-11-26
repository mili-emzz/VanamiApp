package com.emiliagomez.vanamiapp.presentation.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emiliagomez.vanamiapp.ui.theme.BackgroundColor
import com.emiliagomez.vanamiapp.components.BottomNav
import com.emiliagomez.vanamiapp.components.register.ButtonContainers
import com.emiliagomez.vanamiapp.components.register.FormTextField
import com.emiliagomez.vanamiapp.components.register.LoginImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = BackgroundColor,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Bienvenido de nuevo",
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = Color.Black
                    )
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
                .padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Spacer(modifier = Modifier.height(2.dp))

            LoginImage()

            Spacer(modifier = Modifier.height(64.dp))

            FormLoginView()

            Spacer(modifier = Modifier.height(72.dp))

            ButtonContainers("Iniciar Sesión", isRegister = false)
        }
    }
}

@Composable
fun FormLoginView() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

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
            onVisibilityChange = { isPasswordVisible = !isPasswordVisible }
        )

    }
}

@Preview(showBackground = true)
@Composable
fun LoginViewPreview() {
    LoginView()
}