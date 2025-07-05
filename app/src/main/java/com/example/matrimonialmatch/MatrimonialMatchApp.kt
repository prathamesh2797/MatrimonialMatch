package com.example.matrimonialmatch

import android.app.Application
import androidx.compose.runtime.Composable
import com.example.matrimonialmatch.ui.screens.home.HomeScreen
import dagger.hilt.android.HiltAndroidApp

@Composable

fun MatrimonialMatchApp(){
    HomeScreen()
}