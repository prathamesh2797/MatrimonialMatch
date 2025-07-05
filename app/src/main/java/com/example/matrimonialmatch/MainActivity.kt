package com.example.matrimonialmatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.matrimonialmatch.ui.screens.home.HomeScreen
import com.example.matrimonialmatch.ui.theme.MatrimonialMatchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            MatrimonialMatchTheme {
                //MatrimonialMatchApp()
                HomeScreen()
            }
        }
    }
}


@Preview(showBackground = true, name = "App Preview")
@Composable
fun MatrimonialMatchAppPreview() {
    MatrimonialMatchTheme {
        MatrimonialMatchApp()
    }
}