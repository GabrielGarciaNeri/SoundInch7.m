package com.example.soundinch7.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
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
import com.example.soundinch7.ui.RegisterViewModel
import com.example.soundinch7.ui.theme.SoundInch7Theme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterContent(
    paddingValues: PaddingValues,
    name: String,
    onNameChanged: (String) -> Unit,
    nameError: Boolean,
    email: String,
    onEmailChanged: (String) -> Unit,
    emailError: Boolean,
    password: String,
    onPasswordChanged: (String) -> Unit,
    passwordError: Boolean,
    passwordConfirm: String,
    onPasswordConfirmChanged: (String) -> Unit,
    passwordConfirmError: Boolean,
    birthDate: String,
    onBirthDateChanged: (String) -> Unit,
    birthDateError: Boolean,
    genre: String,
    onGenreChanged: (String) -> Unit,
    genreError: Boolean,
    acceptedTerms: Boolean,
    onAcceptedTermsChanged: (Boolean) -> Unit,
    acceptedTermsError: Boolean,
    onCreateAccount: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { onNameChanged(it) },
            label = { Text("Full Name") },
            isError = nameError,
            supportingText = {
                if (nameError) {
                    Text("Name cannot be empty")
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        ) // end of outlined text field for name
        OutlinedTextField(
            value = email,
            onValueChange = { onEmailChanged(it) },
            label = { Text("Email Address") },
            isError = emailError,
            supportingText = {
                if (emailError) {
                    Text("Enter a valid email address")
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        ) // end of outlined text field for email
        // Password field with visibility toggle

        var passwordVisible by remember { mutableStateOf(false) }
        OutlinedTextField(
            value = password,
            onValueChange = { onPasswordChanged(it) },
            label = { Text("Password") },
            isError = passwordError,
            supportingText = {
                if (passwordError) {
                    Text(" Minimum 8 characters")// or any other error message
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next

            ),
            visualTransformation = if (passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible })
                {
                    Icon(
                        imageVector =
                            if (passwordVisible) {
                                Icons.Default.Visibility
                            } else {
                                Icons.Default.VisibilityOff
                            },
                        contentDescription = if (passwordVisible)
                            "Hide password"
                        else
                            "Show password"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) // end of outlined text field for password
        // OutlinedTextField for confirm password
        var passwordConfirmVisible by remember { mutableStateOf(false) }
        OutlinedTextField(
            value = passwordConfirm,
            onValueChange = { onPasswordConfirmChanged(it) },
            label = { Text("Confirm Password") },
            isError = passwordConfirmError,
            supportingText = {
                if (passwordConfirmError) {
                    Text(" Passwords do not match")// or any other error message
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            visualTransformation = if (passwordConfirmVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(onClick = { passwordConfirmVisible = !passwordConfirmVisible })
                {
                    Icon(
                        imageVector =
                            if (passwordConfirmVisible) {
                                Icons.Default.Visibility
                            } else {
                                Icons.Default.VisibilityOff
                            },
                        contentDescription = if (passwordConfirmVisible)
                            "Hide password"
                        else
                            "Show password"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) // end of outlined text field for confirm password
        var showDatePicker by remember { mutableStateOf(false) }
        val datePickerState = rememberDatePickerState()

        OutlinedTextField(
            value = birthDate,
            onValueChange = {},
            label = {Text("Birth date")},
            readOnly = true,
            isError = birthDateError,
            supportingText = {
                if (birthDateError){
                    Text("Please select a date")
                }
            },
            trailingIcon = {
                IconButton(onClick = {showDatePicker = true}) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select birth date"
                    )
                }
            },     // end of trailing icon
            modifier = Modifier.fillMaxWidth()
        ) // end of outlined text field for date of birth

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).apply {
                                timeZone = java.util.TimeZone.getTimeZone("UTC")
                            }
                            onBirthDateChanged(formatter.format(Date(millis)))
                        }
                        showDatePicker = false
                    }) { Text("Accept") }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
                }
            ) { DatePicker(state = datePickerState) }
        } // end of date picker dialog if show date picker

         val genres = listOf("Rock", "Pop", "Jazz", "Classical", "Country")
        var expanded by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = genre,
                onValueChange = { },
                readOnly = true,
                label = { Text("Favorite Music Genre") },
                isError = genreError,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon((expanded))},
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable,true)
            ) // end of outlined text field for genre
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                genres.forEach { item ->
                    DropdownMenuItem(
                        text = {Text(item)},
                        onClick = {
                            onGenreChanged(item)
                            expanded = false
                        }
                    )
                }
            }
        } // end of exposed dropdown menu box for genre
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("I accept the terms and conditions")
            val acceptedTerms = acceptedTerms
            Switch(
                checked = acceptedTerms,
                onCheckedChange = { onAcceptedTermsChanged(it) }
            )
        } // end of row for terms and conditions
        Button(
            onClick = { onCreateAccount() },
            modifier = Modifier.fillMaxWidth()
        ) {  Text ("Create Account")}
    } // end of column for register content
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel(),
    onNavigateToLogin: () -> Unit
) {

    val name by viewModel.name.collectAsStateWithLifecycle()
    val nameError by viewModel.nameError.collectAsStateWithLifecycle()
    val email by viewModel.email.collectAsStateWithLifecycle()
    val emailError by viewModel.emailError.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val passwordError by viewModel.passwordError.collectAsStateWithLifecycle()
    val passwordConfirm by viewModel.passwordConfirm.collectAsStateWithLifecycle()
    val passwordConfirmError by viewModel.passwordConfirmError.collectAsStateWithLifecycle()
    val birthDate by viewModel.birthDate.collectAsStateWithLifecycle()
    val birthDateError by viewModel.birthDateError.collectAsStateWithLifecycle()
    val genre by viewModel.genre.collectAsStateWithLifecycle()
    val genreError by viewModel.genreError.collectAsStateWithLifecycle()
    val acceptedTerms by viewModel.acceptedTerms.collectAsStateWithLifecycle()
    val acceptedTermsError by viewModel.acceptedTermsError.collectAsStateWithLifecycle()
    var showBackDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {Text("Create Account")},
                navigationIcon = {
                    IconButton(onClick = {
                        if (name.isNotBlank()|| email.isNotBlank() || password.isNotBlank()){
                            showBackDialog = true
                        }else {
                            onNavigateToLogin()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },   // end of navigation icon
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                )
            )
        } // end of top bar param

    ) { paddingValues ->
        RegisterContent(
            paddingValues = paddingValues,
            name = name,
            onNameChanged = viewModel::onNameChanged,
            nameError = nameError,
            email = email,
            onEmailChanged = viewModel::onEmailChanged,
            emailError = emailError,
            password = password,
            onPasswordChanged = viewModel::onPasswordChanged,
            passwordError = passwordError,
            passwordConfirm = passwordConfirm,
            onPasswordConfirmChanged = viewModel::onPasswordConfirmChanged,
            passwordConfirmError = passwordConfirmError,
            birthDate = birthDate,
            onBirthDateChanged = viewModel::onBirthDateChanged,
            birthDateError = birthDateError,
            genre = genre,
            onGenreChanged = viewModel::onGenreChanged,
            genreError = genreError,
            acceptedTerms = acceptedTerms,
            onAcceptedTermsChanged = viewModel::onAcceptedTermsChanged,
            acceptedTermsError = acceptedTermsError,
            onCreateAccount = {
                val isValid = viewModel.validateAndRegister()
                scope.launch{
                    if (isValid){
                    snackbarHostState.showSnackbar("Account created successfully")
                    } else{
                    snackbarHostState.showSnackbar("Please review the marked fields")}
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterContentPreview() {
    SoundInch7Theme {}
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    SoundInch7Theme {
        RegisterScreen(
            onNavigateToLogin = {}
        )
    }
}
