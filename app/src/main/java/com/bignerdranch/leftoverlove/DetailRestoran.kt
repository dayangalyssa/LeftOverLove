package com.bignerdranch.leftoverlove

import android.util.Log
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.database.database

@Composable
fun DetailRestoran(navController: NavController, idRestoran: String, onBack: () -> Unit = { navController.popBackStack() }) {
    val restoranData = remember { mutableStateOf<DataRestaurant?>(null) }
    val menuList = remember { mutableStateListOf<MenuItem>() }
    LaunchedEffect(idRestoran) {
        val database = Firebase.database.reference
        database.child(idRestoran).get().addOnSuccessListener { snapshot ->
            val restoran = snapshot.getValue(DataRestaurant::class.java)
            restoranData.value = restoran
            val menuItems = restoran?.menu?.map { entry ->
                MenuItem(
                    namaMakanan = entry.value.namaMakanan,
                    harga = entry.value.harga,
                    stock = entry.value.stock,
                    idMakanan = entry.value.idMakanan,
                    deskripsi = entry.value.deskripsi,
                    rating = entry.value.rating,
                    waktuPengambilan = entry.value.waktuPengambilan,
                )
            } ?: emptyList()

            menuList.clear()
            menuList.addAll(menuItems)
        }
    }

    val selectedItems = remember { mutableStateListOf<MenuItem>() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9))
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "Detail Restoran",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(6f)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }

            item {
                Image(
                    painter = painterResource(id = R.drawable.nasi_goreng),
                    contentDescription = "Restaurant Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp),
                    contentScale = ContentScale.Crop
                )
            }

            item {
                Card(
                    modifier = Modifier
                        .offset(y = (-32).dp)
                        .fillMaxWidth()

                        .padding(vertical = 16.dp)
                        .shadow(8.dp, shape = RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9))
                ) {
                    Column(
                        modifier = Modifier
                            .height(150.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(top = 25.dp, start = 16.dp)
                        ) {

                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = restoranData.value?.namaRestoran ?: "",
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = restoranData.value?.deskripsi ?: "",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = restoranData.value?.alamat ?: "",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Spacer(modifier = Modifier.weight(4f))
                                Text(
                                    text = restoranData.value?.rating ?: "",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )

                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Rating",
                                    tint = Color(0xFFFFD700)
                                )
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }

            items(menuList) { menuItem ->
                MenuItemCard(menuItem = menuItem, onAddToCart = { addedItem ->
                    val existingItem = selectedItems.find { it.namaMakanan == addedItem.namaMakanan }
                    if (existingItem == null) {
                        selectedItems.add(addedItem.copy(stock = 1))
                    } else {
                        val index = selectedItems.indexOf(existingItem)
                        selectedItems[index] =
                            existingItem.copy(stock = existingItem.stock + 1)
                    }
                }, onRemoveFromCart = { removedItem ->
                    val existingItem = selectedItems.find { it.namaMakanan == removedItem.namaMakanan }
                    if (existingItem != null) {
                        val index = selectedItems.indexOf(existingItem)
                        if (existingItem.stock > 1) {
                            selectedItems[index] = existingItem.copy(stock = existingItem.stock - 1)
                        } else {
                            selectedItems.removeAt(index)
                        }
                    }
                })
            }
        }

        if (selectedItems.isNotEmpty()) {
            BottomBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                selectedItems = selectedItems,
                navController = navController
            )
        }
    }
}

@Composable
fun MenuItemCard(menuItem: MenuItem, onAddToCart: (MenuItem) -> Unit, onRemoveFromCart: (MenuItem) -> Unit) {
    var isAddingToCart by remember { mutableStateOf(false) }
    var quantity by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = menuItem.namaMakanan,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Black
                    ),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "Rp ${menuItem.harga}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Text(
                    text = "Tersisa ${menuItem.stock} stok",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                if (isAddingToCart) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(
                            onClick = {
                                if (quantity > 1) {
                                    quantity -= 1
                                    val updatedItem = menuItem.copy(stock = quantity)
                                    onRemoveFromCart(updatedItem)
                                } else {
                                    quantity = 0
                                    isAddingToCart = false
                                    val updatedItem = menuItem.copy(stock = 0)
                                    onRemoveFromCart(updatedItem)
                                }
                            },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.iconmin),
                                contentDescription = "Kurangi",
                                tint = Color.Unspecified
                            )
                        }

                        Text(
                            text = quantity.toString(),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        IconButton(
                            onClick = {
                                if (quantity < menuItem.stock) {
                                    quantity += 1
                                    val updatedItem = menuItem.copy(stock = quantity)
                                    onAddToCart(updatedItem)
                                }
                            },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.iconplus),
                                contentDescription = "Tambah",
                                tint = Color.Unspecified
                            )
                        }
                    }
                } else {
                    IconButton(
                        onClick = {
                            isAddingToCart = true
                            quantity = 1
                            val updatedItem = menuItem.copy(stock = quantity)
                            onAddToCart(updatedItem)
                        },
                        modifier = Modifier.size(65.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.tambahicon),
                            contentDescription = "Tambah Icon",
                            tint = Color.Unspecified
                        )
                    }
                }
            }

            Image(
                painter = painterResource(id = R.drawable.nasi_goreng),
                contentDescription = menuItem.namaMakanan,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
    }
}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    selectedItems: List<MenuItem>,
    navController: NavController
) {
    val totalItems = selectedItems.sumOf { it.stock }
    val totalPrice = selectedItems.sumOf { it.harga * it.stock }

    Button(
        onClick = { navController.navigate("detailPesanan") },
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF7B886F),
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "$totalItems item",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFF9F9F9)
                )
                Text(
                    text = selectedItems.joinToString { it.namaMakanan },
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFF9F9F9)
                )
            }
            Text(
                text = "Rp $totalPrice",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
    Log.d("NavController", "NavController hashCode: ${navController.hashCode()}")
}

data class MenuItem(
    val namaMakanan: String = "",
    val harga: Int = 0,
    val stock: Int = 0,
    val idMakanan: String = "",
    val deskripsi: String = "",
    val rating: String = "",
    val waktuPengambilan: String = ""
)

data class DataRestaurant(
    val idRestoran: String = "",
    val namaRestoran: String = "",
    val alamat: String = "",
    val deskripsi: String = "",
    val rating: String = "",
    val menu: Map<String, MenuItem> = emptyMap()
)