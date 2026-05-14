package com.example.soundinch7.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.soundinch7.ui.UserSessionViewModel
import com.example.soundinch7.ui.theme.SoundInch7Theme

@Composable
fun ProfileScreen(
    sessionViewModel: UserSessionViewModel,
    onLogout: () -> Unit
){
    val userName by sessionViewModel.userName.collectAsStateWithLifecycle()
    val userEmail by sessionViewModel.userEmail.collectAsStateWithLifecycle()
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog){
        AlertDialog(
           onDismissRequest = {showLogoutDialog = false},
            title = {Text ("Logout")},
            text = {Text ("Are you sure yo want to Logout?")},
            confirmButton = {
                TextButton(onClick = {
                    showLogoutDialog = false
                    onLogout()
                }) {
                    Text ("Log Out", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showLogoutDialog = false
                }) {
                    Text("Cancel")
                }
            }
        )
    } // end of if showLogoutDialog

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // display user information Avatar with initials
        Box(
            modifier = Modifier
            .size(80.dp)
            .background(MaterialTheme.colorScheme.primary,
                shape = CircleShape
            ),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = userName.firstOrNull()?.uppercaseChar()?.toString()?: "?",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        } // end of box
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = userName,
            style = MaterialTheme.typography.titleLarge
        ) // end of text for userName
        Text(
            text = userEmail,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        ) // end of text for userEmail
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                showLogoutDialog = true
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text ("Logout") }
    }
}

 var previewUserSessionViewModel = UserSessionViewModel()
@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview(){
    SoundInch7Theme() {
        ProfileScreen(
            sessionViewModel = previewUserSessionViewModel,
            onLogout = {}
        )
    }
}


















