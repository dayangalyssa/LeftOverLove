package com.bignerdranch.leftoverlove

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Selamat Datang!", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 24.dp))
        Spacer(modifier = Modifier.height(16.dp))

        // Email Field
        TextField(
            value = email,
            onValueChange = { it.also { email = it } },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Kata Sandi") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Login Button
        Button(
            onClick = {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            navController.navigate("home")
                        } else {
                            Toast.makeText(navController.context, "Login gagal", Toast.LENGTH_SHORT).show()
                        }
                    }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF7B886F))
        ) {
            Text(text = "Masuk", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Divider(modifier = Modifier.weight(1f), color = Color.Gray)
            Text(text = " atau dengan ", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(horizontal = 8.dp))
            Divider(modifier = Modifier.weight(1f), color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(16.dp))
        // Google login (Optional)
        OutlinedButton(onClick = { /* Handle Google login */ }, modifier = Modifier.fillMaxWidth().height(48.dp)) {
            Image(painter = painterResource(id = R.drawable.google_icon), contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Masuk dengan Google")
        }

        Spacer(modifier = Modifier.height(16.dp))
        // Navigate to Signup
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically, // Pastikan elemen sejajar secara vertikal
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Belum punya akun?", color = Color.Gray)
            Spacer(modifier = Modifier.width(4.dp))
            TextButton(
                onClick = { navController.navigate("signup") },
                contentPadding = PaddingValues(0.dp) // Hilangkan padding tambahan pada TextButton
            ) {
                Text(text = "Daftar", color = Color(0xFF7B886F))
            }
        }
    }
}