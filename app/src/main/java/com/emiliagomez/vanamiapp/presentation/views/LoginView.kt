package com.emiliagomez.vanamiapp.presentation.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emiliagomez.vanamiapp.ui.theme.BackgroundColor
import com.emiliagomez.vanamiapp.components.register.ButtonContainers
import com.emiliagomez.vanamiapp.components.register.FormTextField
import com.emiliagomez.vanamiapp.components.register.LoginImage
import com.emiliagomez.vanamiapp.presentation.viewmodels.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(
    loginViewModel: LoginViewModel,
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            "Bienvenido de nuevo",
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(modifier = Modifier.size(160.dp)) {
            LoginImage()
        }

        Spacer(modifier = Modifier.height(32.dp))

        FormLoginView(
            email = email,
            onEmailChange = { email = it },
            password = password,
            onPasswordChange = { password = it }
        )

        Spacer(modifier = Modifier.height(72.dp))

        ButtonContainers(
            "Iniciar Sesión",
            isRegister = false,
            onButtonClick = {
                loginViewModel.login(
                    email = email,
                    password = password,
                    onSuccess = onLoginSuccess
                )
            },
            onNavigateClick = onNavigateToRegister
        )
    }
}


@Composable
fun FormLoginView(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        FormTextField(
            email,
            onEmailChange,
            label = "Correo eléctronico"
        )

        FormTextField(
            password,
            onPasswordChange,
            isPassword = true,
            label = "Contraseña",
            keyboardType = KeyboardType.Password,
            isPasswordVisible = isPasswordVisible,
            onVisibilityChange = { isPasswordVisible = !isPasswordVisible }
        )
    }
}