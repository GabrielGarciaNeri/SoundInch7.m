package com.example.soundinch7.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.soundinch7.ui.LoginViewModel
import com.example.soundinch7.ui.UserSessionViewModel
import com.example.soundinch7.ui.theme.SoundInch7Theme
import kotlinx.coroutines.launch

@Composable
fun LoginContent(
    paddingValues: PaddingValues,
    email: String,
    password: String,
    rememberSession: Boolean,
    emailError: Boolean,
    passwordError: Boolean,
    // functions to update the state
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onRememberSessionChanged: (Boolean) -> Unit,
    onLoginClick: () -> Unit,
    onNavigateToRegister: () -> Unit

){


    Column( modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(paddingValues)
        .padding(24.dp)
        .verticalScroll(rememberScrollState()), // allow scrolling of the content
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "S",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.primary
        )// end of text
        Text (
            text = "SoundIn",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        ) // end of text
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = {onEmailChanged(it)},
            label = {Text("Email Address")},
            isError = emailError,
            supportingText = {
                if (emailError){
                    Text("Enter a valid email address")
                }
            }, // end of supporting text
            keyboardOptions = KeyboardOptions (
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
            ),
            modifier = Modifier.fillMaxWidth()
        )// end of outlined text field
        var passwordVisible by remember { mutableStateOf(false) }
        OutlinedTextField(
            value = password,
            onValueChange = {onPasswordChanged(it)},
            label = {Text("Password")},
            isError = passwordError,
            supportingText = {
                if (passwordError){
                    Text("Minimum 8 characters") // or any other error message
                }
            }, // end of supporting text
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            visualTransformation = if (passwordVisible){
                VisualTransformation.None
            }else{
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton( onClick = {passwordVisible = !passwordVisible})
                {
                    Icon (
                        imageVector = if (passwordVisible){
                            Icons.Default.Visibility
                        }else{
                            Icons.Default.VisibilityOff
                        },
                        contentDescription = if (passwordVisible) "Hide password" else "Show password"
                    )
                } // end of icon button
            },// end of trailing icon
            modifier = Modifier.fillMaxWidth()
        ) // end of outlined text field
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement =  Arrangement.SpaceBetween
        ) {
            Text (
                text = "Remember session",
                style = MaterialTheme.typography.bodyMedium
            )
            Switch(
                checked = rememberSession,
                onCheckedChange = {onRememberSessionChanged(it)}
            )
        } // end of row
        // Login button with validation
        Button(
            onClick = { onLoginClick()
            }, // end of onclick
            modifier = Modifier.fillMaxWidth()
        ) { Text("Log In") } // end of button
        TextButton(
            onClick = onNavigateToRegister
        ) {
            Text("Don't have an account? Register")
        }

    } // end of column
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel : LoginViewModel = viewModel(),
    sessionViewModel: UserSessionViewModel,
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit
){
    val snackbarHostState = remember { SnackbarHostState () }
    val scope = rememberCoroutineScope()
    // We observe the StateFLow using CollectAsStateWithLifecycle
    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val rememberSession by viewModel.rememberSession.collectAsStateWithLifecycle()
    val emailError by viewModel.emailError.collectAsStateWithLifecycle()
    val passwordError by viewModel.passwordError.collectAsStateWithLifecycle()


    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text ("SoundIn")},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) // end of top app bar
        }, // end of top bar param
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }// end of snackbar host
    ) {
        paddingValues ->
        LoginContent(
            paddingValues = paddingValues,
            email = email,
            password = password,
            rememberSession = rememberSession,
            emailError = emailError,
            passwordError = passwordError,
            onEmailChanged = viewModel:: onEmailChanged,
            onPasswordChanged = viewModel:: onPasswordChanged,
            onRememberSessionChanged = viewModel::onRememberSessionChanged,
            onLoginClick ={
                val isValid = viewModel.validateAndLogin()
                scope.launch {
                    if (isValid){
                        sessionViewModel.login(
                            name = "John Doe",
                            email = email
                        )
                        snackbarHostState.showSnackbar(
                            message = "Welcome to SoundIn",
                            actionLabel = "Go",
                            duration = SnackbarDuration.Short

                        )
                        onLoginSuccess()

                    }else {
                        snackbarHostState.showSnackbar(
                            message = "Please review the marked fields"
                        )
                    } // end of else
                }// end of launch coroutine
            },         // end of on login click function
             onNavigateToRegister = onNavigateToRegister
        )
    }
}




@Preview(showBackground = true)
@Composable
fun LoginContentPreview(){
    SoundInch7Theme() {
        LoginContent(paddingValues = PaddingValues(0.dp),
            email = "",
            password = "",
            rememberSession = false,
            emailError = false,
            passwordError = false,
            onEmailChanged = {_:String ->},
            onPasswordChanged = {_:String ->},
            onRememberSessionChanged = {_:Boolean ->},
            onLoginClick = {},
            onNavigateToRegister = {}

        )
    }
}















