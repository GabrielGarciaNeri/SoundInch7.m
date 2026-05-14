package com.example.soundinch7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.soundinch7.ui.UserSessionViewModel
import com.example.soundinch7.ui.navigation.SoundInNavGraph
import com.example.soundinch7.ui.screens.LoginScreen
import com.example.soundinch7.ui.theme.SoundInch7Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val sessionViewModel : UserSessionViewModel = viewModel()
            SoundInch7Theme {
                Surface( // our personalized theme
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    SoundInNavGraph(navController = navController,
                        sessionViewModel = sessionViewModel
                    )
                }
            }
        }
    }
}









