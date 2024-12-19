package com.bignerdranch.leftoverlove

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SplashScreen(onNavigateToSignup: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo), // Change 'logo' to your actual file name
            contentDescription = "App Logo",
            modifier = Modifier.size(100.dp),
            colorFilter = ColorFilter.tint(Color(0xFF92C947)) // Green color tint
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "LeftOverLove",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
    // Simulasi delay untuk berpindah
        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(2000) // 2 detik
            onNavigateToSignup()
        }
}