package com.bignerdranch.leftoverlove

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

// Bottom Navigation Bar
@Composable
fun BottomNavBar(navController: NavController, modifier: Modifier = Modifier) {
    NavigationBar(
        modifier = Modifier.height(64.dp),
        containerColor = Color(0xFFF9F9F9)// Menetapkan tinggi dari NavigationBar
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("aktivitasku") },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.iconaktivitas), // Ikon custom
                    contentDescription = "Aktivitas",
                    tint = Color(0xFF7B886F)
                )
            },
            label = {
                Text(
                    text = "Aktivitas",
                    color = Color(0xFF7B886F) // Warna teks untuk "Aktivitas"
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("home") },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.iconberanda), // Ikon custom
                    contentDescription = "beranda",
                    tint = Color(0xFF7B886F)
                )
            },
            label = {
                Text(
                    text = "Beranda",
                    color = Color(0xFF7B886F) // Warna teks untuk "Aktivitas"
                )  }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("profile") },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.iconprofil), // Ikon custom
                    contentDescription = "Profil",
                    tint = Color(0xFF7B886F)
                )
            },
            label = {
                Text(
                    text = "Profil",
                    color = Color(0xFF7B886F) // Warna teks untuk "Aktivitas"
                )  }
        )
    }
}