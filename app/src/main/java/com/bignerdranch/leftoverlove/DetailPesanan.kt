package com.bignerdranch.leftoverlove

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

data class MenuItems(
    val name: String,
    val price: Int,
    val stock: Int,
    val imageRes: Int
)

val menuItems = listOf(
    MenuItems("Nasi Goreng Spesial", 10000, 2, R.drawable.nasi_goreng),
    MenuItems("Mie Goreng Spesial", 10000, 1, R.drawable.nasi_goreng)
)

@Composable
fun DetailPesanan(navController: NavController) {
    val selectedItems = menuItems // Data statis, diambil langsung dari menuItems

    var showPromoDialog by remember { mutableStateOf(false) }
    var selectedPromo by remember { mutableStateOf<Promo?>(null) }

    val subtotal = selectedItems.sumOf { it.price * it.stock }
    val deliveryFee = 5000
    val total = subtotal + deliveryFee - (selectedPromo?.discountAmount(subtotal) ?: 0)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9))
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            // Header
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
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                        Text(
                            text = "Pesanan",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(48.dp)) // Placeholder for symmetry
                    }
                }
            }

            // Daftar Pesanan
            item {
                Text(
                    text = "Daftar Pesanan",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 16.dp) // Jarak antara judul dan daftar pesanan
                )
            }

            items(selectedItems) { item ->
                OrderItemCard(item = item)
            }

            // Promo Button
            item {
                Spacer(modifier = Modifier.height(16.dp))
                PromoButton(
                    selectedPromo = selectedPromo,
                    onPromoClick = { showPromoDialog = true }
                )
            }

            // Ringkasan Pembayaran
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Ringkasan Pembayaran",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Subtotal")
                        Text(text = "Rp $subtotal")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Biaya pengantaran")
                        Text(text = "Rp $deliveryFee")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Diskon")
                        Text(text = "-Rp ${selectedPromo?.discountAmount(subtotal) ?: 0}")
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Total",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Rp $total",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        CustomButton(
            onClick = {navController.navigate("pesananDiantar") },
            modifier = Modifier
                .padding(bottom = 16.dp)  // Padding di bawah
                .align(Alignment.BottomCenter)
        )

        // Dialog Pilih Promo
        if (showPromoDialog) {
            PromoDialog(
                onDismiss = { showPromoDialog = false },
                onPromoSelected = {
                    selectedPromo = it
                    showPromoDialog = false
                }
            )
        }
    }
}

@Composable
fun OrderItemCard(item: MenuItems) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = item.imageRes),
            contentDescription = item.name,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(item.name, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
            Text(
                "Rp ${item.price}",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
            )
        }
    }
}

@Composable
fun PromoButton(
    selectedPromo: Promo?,
    onPromoClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable(onClick = onPromoClick)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = Color(0xFFA5A5A5),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(10.dp)
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = selectedPromo?.label ?: "Pilih Promo",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(id = R.drawable.dropdown),
            contentDescription = "Dropdown"
        )
    }
}

@Composable
fun PromoDialog(
    onDismiss: () -> Unit,
    onPromoSelected: (Promo) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Pilih Promo") },
        text = {
            Column {
                Promo.values().forEach { promo ->
                    Text(
                        text = promo.label,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onPromoSelected(promo) }
                            .padding(8.dp)
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Tutup")
            }
        }
    )
}

@Composable
fun CustomButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Icon(
        painter = painterResource(id = R.drawable.buttonpesan),
        contentDescription = "Pesanan Konfirmasi",
        modifier = modifier
            .size(350.dp)
            .clickable { onClick() },
        tint = Color.Unspecified
    )
}

enum class Promo(val label: String, private val discountPercentage: Int) {
    DISCOUNT_10("Diskon 10%", 10),
    DISCOUNT_20("Diskon 20%", 20);

    fun discountAmount(subtotal: Int): Int {
        return (subtotal * discountPercentage) / 100
    }
}

@Preview(showBackground = true)
@Composable
fun DetailPesananPreview() {
    val navController = rememberNavController()
    DetailPesanan(
        navController = navController
    )
}