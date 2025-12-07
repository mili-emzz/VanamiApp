package com.emiliagomez.vanamiapp.presentation.views

import android.util.Log
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
import com.emiliagomez.vanamiapp.components.NavManager.BottomNav
import com.emiliagomez.vanamiapp.components.register.ButtonContainers
import com.emiliagomez.vanamiapp.components.register.FormTextField
import com.emiliagomez.vanamiapp.components.register.LoginImage
import com.emiliagomez.vanamiapp.presentation.viewmodels.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterView(
    loginViewModel: LoginViewModel,
    onNavigateToLogin: () -> Unit = {},
    onRegisterSuccess: () -> Unit = {}
) {
    // para capturar los valores de los TextFields
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

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

            FormsView(
                name = name,
                onNameChange = { name = it },
                username = username,
                onUsernameChange = { username = it },
                email = email,
                onEmailChange = { email = it },
                password = password,
                onPasswordChange = { password = it },
                confirmPassword = confirmPassword,
                onConfirmPasswordChange = { confirmPassword = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ButtonContainers(
                text = "Registrarse",
                isRegister = true,
                onButtonClick = {
                    if (password == confirmPassword) {
                        loginViewModel.createUser(
                            email = email,
                            password = password,
                            name = name,
                            username = username,
                            onSuccess = onRegisterSuccess
                        )
                    } else {
                        Log.d("RegisterView", "Las contraseñas no coinciden")
                    }
                },
                onNavigateClick = onNavigateToLogin
            )
        }
    }
}

@Composable
fun FormsView(
    name: String,
    onNameChange: (String) -> Unit,
    username: String,
    onUsernameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        FormTextField(
            value = name,
            onValueChange = onNameChange,
            label = "Nombre"
        )

        FormTextField(
            value = username,
            onValueChange = onUsernameChange,
            label = "Nombre de usuario"
        )

        FormTextField(
            value = email,
            onValueChange = onEmailChange,
            label = "Correo electrónico"
        )

        FormTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = "Contraseña",
            keyboardType = KeyboardType.Password,
            isPassword = true,
            isPasswordVisible = isPasswordVisible,
            onVisibilityChange = { isPasswordVisible = !isPasswordVisible }
        )

        FormTextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            label = "Confirmar Contraseña",
            keyboardType = KeyboardType.Password,
            isPassword = true,
            isPasswordVisible = isConfirmPasswordVisible,
            onVisibilityChange = { isConfirmPasswordVisible = !isConfirmPasswordVisible }
        )
    }
}
