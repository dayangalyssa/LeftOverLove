package com.bignerdranch.leftoverlove

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bignerdranch.leftoverlove.ui.theme.LeftOverLoveTheme

@Composable
fun PesananDiantarScreen() {
    // Status pesanan dengan state
    val statusList = listOf(
        "Mohon menunggu! Pesananmu sedang diantar oleh driver." to R.drawable.motor_antar,
        "Pesanan sudah sampai. Silakan cek kembali pesananmu." to R.drawable.pesanan_sampai,
        "Pesanan selesai. Terima kasih telah memesan!" to R.drawable.pesanan_selesai
    )
    val (statusIndex, setStatusIndex) = remember { mutableStateOf(0) } // Status awal

    // Ubah status otomatis setiap 3 detik
    LaunchedEffect(Unit) {
        while (statusIndex < statusList.size - 1) {
            kotlinx.coroutines.delay(3000) // Delay 3 detik
            setStatusIndex(statusIndex + 1)
        }
    }

    // Layout utama
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Warna latar belakang abu-abu terang
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Ilustrasi Driver
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = statusList[statusIndex].second), // Gambar sesuai status
                contentDescription = "Driver Illustration",
                modifier = Modifier.size(200.dp)
            )
        }

        // Informasi Status Pesanan
        Text(
            text = statusList[statusIndex].first, // Teks sesuai status
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Card untuk Rincian Pesanan
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Rincian Pesanan Header
                Text(
                    text = "Rincian Pesanan",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // Info Driver dan Toko
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.user_icon), // Ikon User
                        contentDescription = "User Icon",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "N 4747 AD",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Ahmad Rafi",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                // Info Restoran
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.restoran_icon), // Ikon Restoran
                        contentDescription = "Restaurant Icon",
                        tint = Color(0xFFFF5722),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Nasi Goreng 88",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Jl. Sumbersari Gg 4 No 225M",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Daftar Pesanan
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Daftar Pesanan",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Item Pesanan 1
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.nasi_goreng), // Ganti dengan gambar makanan yang sesuai
                        contentDescription = "Nasi Goreng",
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "1 x Nasi Goreng Spesial",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Text(
                        text = "Rp 10.000",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Item Pesanan 2
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.nasi_goreng), // Ganti dengan gambar makanan yang sesuai
                        contentDescription = "Mie Goreng",
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "1 x Mie Goreng Spesial",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Text(
                        text = "Rp 10.000",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Ringkasan Pembayaran
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Ringkasan Pembayaran",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Detail Pembayaran
                Text("Subtotal             Rp 20.000", fontSize = 16.sp)
                Text("Biaya Pengantaran    Rp 5.000", fontSize = 16.sp)
                Text("Diskon               Rp 0", fontSize = 16.sp)

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                // Total
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Rp 20.000",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}


@Composable
fun PesananDiantarPreview() {
    PesananDiantarScreen()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LeftOverLoveTheme() {
        // Surface ensures the background color is applied in the preview
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            PesananDiantarPreview()
        }
    }
}