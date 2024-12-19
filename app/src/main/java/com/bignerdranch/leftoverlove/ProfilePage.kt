package com.bignerdranch.leftoverlove

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun ProfilePage(navController: NavController? = null) {
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9))
    ) {
        // Back Button dan Judul "Profil"
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFB4DC7F))
                .padding(vertical = 8.dp, horizontal = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { navController?.popBackStack() }) { // Aksi tombol kembali
                    Icon(
                        imageVector = Icons.Filled.ArrowBack, // Gunakan panah default
                        contentDescription = "Back"
                    )
                }
                Spacer(modifier = Modifier.weight(1f)) // Memberi jarak fleksibel di kiri
                Text(
                    text = "Profil",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(3f) // Memberi ruang yang lebih besar untuk judul
                )
                Spacer(modifier = Modifier.weight(2f)) // Memberi jarak fleksibel di kanan
            }
        }

        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFB4DC7F))
                .padding(vertical = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.profil_icon), // Ganti dengan gambar profil Anda
                    contentDescription = "Foto Profil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color(0xFF7B886F), CircleShape)
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Nama Profil dengan Ikon
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(user?.displayName ?: "Nama Tidak Tersedia", color = Color.White, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Nomor HP dengan Ikon
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(painter = painterResource(id = R.drawable.telephone_icon), contentDescription = "Icon Phone", modifier = Modifier.size(24.dp), tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(user?.phoneNumber ?: "Nomor Tidak Tersedia", color = Color.White)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Email dengan Ikon
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(painter = painterResource(id = R.drawable.mail_icon), contentDescription = "Icon Email", modifier = Modifier.size(24.dp), tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(user?.email ?: "Email Tidak Tersedia", color = Color.White)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Poin dengan Ikon
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(painter = painterResource(id = R.drawable.favo_icon), contentDescription = "Icon Star", modifier = Modifier.size(24.dp), tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("25 poin", color = Color.White, style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Menu Options with Icons from Drawable
        MenuOption(
            text = "Edit Profil",
            icon = painterResource(id = R.drawable.edit_icon),
            onClick = { navController?.navigate("editProfile") }
        )
        MenuOption(
            text = "Aktivitasku",
            icon = painterResource(id = R.drawable.aktivitas_icon),
            onClick = { navController?.navigate("aktivitasku") }
        )
        MenuOption(
            text = "Donasiku",
            icon = painterResource(id = R.drawable.donasi_icon),
            onClick = { navController?.navigate("donasiku") }
        )
        MenuOption(
            text = "Favoritku",
            icon = painterResource(id = R.drawable.favo_icon),
            onClick = { navController?.navigate("favoritku") }
        )
        MenuOption(
            text = "Keluar",
            icon = painterResource(id = R.drawable.logout),
            onClick = {
                Firebase.auth.signOut()
                navController?.navigate("login")
            },
            textColor = Color.Red
        )
    }
}

@Composable
fun MenuOption(
    text: String,
    onClick: () -> Unit,
    icon: Painter,
    textColor: Color = Color.Black
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, color = textColor, style = MaterialTheme.typography.bodyMedium)
    }
}