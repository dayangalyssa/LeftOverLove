package com.bignerdranch.leftoverlove

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.database.database

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMakanan(navController: NavController, idMakanan: String) {
    val makananDetail = remember { mutableStateOf<MakananDetail?>(null) }

    // Mengambil data dari Firebase
    LaunchedEffect(idMakanan) {
        val database = Firebase.database.reference
        database.child(idMakanan).get().addOnSuccessListener { dataSnapshot ->
            makananDetail.value = dataSnapshot.getValue(MakananDetail::class.java)
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f)) // Ruang fleksibel di kiri
                        Text(
                            text = "Detail Makanan",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 25.sp // Menambahkan ukuran font yang lebih besar
                            ),
                            modifier = Modifier.weight(5f), // Pastikan teks rata tengah
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.weight(2f)) // Ruang fleksibel di kanan
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(top = 5.dp, start = 16.dp, end = 16.dp)
                    .verticalScroll(rememberScrollState()) // Tambahkan ini
            ) {
                if(makananDetail.value != null){
                    // Gambar Makanan
                    Image(
                        painter = painterResource(id = R.drawable.nasi_goreng), // Ganti dengan gambar asli
                        contentDescription = "Food Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .width(350.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Judul Makanan
                    Row(
                        verticalAlignment = Alignment.CenterVertically // Menyelaraskan elemen secara vertikal
                    ) {
                        Column(
                            modifier = Modifier.weight(1f) // Kolom akan mengambil ruang yang cukup di baris
                        ) {
                            Text(
                                text = makananDetail.value?.namaMakanan ?: "",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text = "Ditambahkan 50 menit yang lalu",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }

                        // Menambahkan ikon di sebelah kanan
                        IconButton(
                            onClick = { /* Favorite Action */ },
                            modifier = Modifier.size(40.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite",
                                modifier = Modifier.align(Alignment.CenterVertically) // Menyelaraskan ikon secara vertikal
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = makananDetail.value?.harga ?: "N/A",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "Tersisa 5 stok",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Red,
                        )
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    Spacer(modifier = Modifier.height(5.dp))

                    // Informasi Restoran
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.iconwarung), // Ikon restoran
                            contentDescription = "Restoran",
                            modifier = Modifier.size(32.dp),
                            tint = Color.Unspecified
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Nasi Goreng 88",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Column {
                        Text(
                            text = "Waktu Pengambilan",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = makananDetail.value?.waktuPengambilan ?: "N/A",
                            style = MaterialTheme.typography.bodyLarge

                        )
                        Spacer(modifier = Modifier.height(13.dp))
                        Text(
                            text = "Alamat Pengambilan",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = makananDetail.value?.alamatPengambilan ?: "N/A",
                            style = MaterialTheme.typography.bodyLarge

                        )
                    }
                    Spacer(modifier = Modifier.height(13.dp))
                    // Informasi Makanan
                    Column{
                        Text(
                            text = "Informasi Makanan",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4)) // Warna kuning terang
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = """
                                - Waktu Masak: 13:00 WIB
                                - Aman dikonsumsi sebelum: 24:00 WIB
                                - Jenis makanan: Makanan kering
                                - Kategori: Makanan berat
                                - Kualitas makanan sepenuhnya tanggung jawab penjual
                            """.trimIndent(),
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFED9200)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Deskripsi
                    Text(
                        text = "Deskripsi",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = makananDetail.value?.deskripsi ?: "",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Rating
                    Column{
                        Text(
                            text = "Rating Restoran",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Row {
                            Text(
                                text = makananDetail.value?.rating ?: "",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(modifier = Modifier.width(4.dp))

                            Icon(
                                imageVector = Icons.Default.Star, // Ikon bintang
                                contentDescription = "Star",
                                tint = Color(0xFFFFD700),
                                modifier = Modifier.size(20.dp)
                            )
                        }

                    }
                } else {
                    // Placeholder saat data belum ada
                    Text(
                        text = "Memuat data...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
        }
    )
}