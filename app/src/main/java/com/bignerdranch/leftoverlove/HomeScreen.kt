package com.bignerdranch.leftoverlove

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.bignerdranch.leftoverlove.ui.theme.LeftOverLoveTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center // This centers the title
                    ) {
                        Text(
                            text = "Beranda",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Tambahkan aksi */ }) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors( // Gunakan colors
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            // val navController = rememberNavController()
            BottomNavBar(navController = navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Search Bar
            SearchBar(navController = navController)

            // Banner
            Banner(navController = navController)

            // Rekomendasi Makanan
            SectionTitle(title = "Rekomendasi Makanan")
            FoodRecommendationList(navController)

            // Restoran Terdekat
            SectionTitle(title = "Restoran Terdekat")
            NearbyRestaurantsList(navController)
        }
    }
}

// Search Bar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(navController: NavController) {
    var searchText by remember { mutableStateOf("") }
    var filteredList by remember { mutableStateOf(listOf<String>()) }
    var showDropdown by remember { mutableStateOf(false) }

    val foodList = listOf(
        "Nasi Goreng Spesial",
        "Mie Goreng Spesial",
        "Nasi Goreng Hongkong",
        "Nasi Goreng Jawa",
        "Nasi Goreng Mawut",
        "Nasi Goreng Seafood"
    )

    Column {
        TextField(
            value = searchText,
            onValueChange = { newText ->
                searchText = newText
                // Filter hanya menampilkan item yang diawali dengan input (case-insensitive)
                filteredList = if (newText.isNotEmpty()) {
                    foodList.filter { it.startsWith(newText, ignoreCase = true) }
                } else emptyList()
                showDropdown = filteredList.isNotEmpty()
            },
            placeholder = { Text(text = "Mau cari apa kawan?") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFEBEBEB),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )

        DropdownMenu(
            expanded = showDropdown,
            onDismissRequest = { showDropdown = false },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            filteredList.forEach { foodName ->
                DropdownMenuItem(
                    text = { Text(text = foodName) },
                    onClick = {
                        showDropdown = false
                        // Navigasi hanya untuk "Nasi Goreng Spesial"
                        if (foodName == "Nasi Goreng Spesial") {
                            navController.navigate("detailMakanan/M001")
                        } else if (foodName == "Mie Goreng Spesial") {
                            navController.navigate("detailMakanan/M002")
                        }
                    }
                )
            }
        }
    }
}

// Banner
@Composable
fun Banner(navController: NavController) {

    Image(
        painter = painterResource(id = R.drawable.bannerdonasi),
        contentDescription = "Gambar Banner",

        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(12.dp))
            .padding(start = 10.dp, end = 10.dp)
            .clickable {
                navController.navigate("DonasiScreen") // Navigasi ke DonasikuScreen
            }
    )
}

data class Food(val name: String, val price: String, val imageRes: Int, val id: String)

@Composable
fun FoodRecommendationList(navController: NavController) {
    val foodList = listOf(
        Food("Nasi Goreng Spesial", "Rp 10.000", R.drawable.nasi_goreng, "M001"),
        Food("Mie Goreng", "Rp 8.000", R.drawable.nasi_goreng, "M002")
    )

    LazyRow(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 0.dp)
    ) {
        items(foodList) { food ->
            FoodCard(
                name = food.name,
                price = food.price,
                imageRes = food.imageRes,
                onClick = {
                    // Navigasikan ke DetailMakanan dengan ID yang sesuai
                    navController.navigate("DetailMakanan/${food.id}")
                }
            )
        }
    }
}

// Section Title
@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun FoodCard(
    name: String,
    price: String,
    imageRes: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(180.dp)
            .padding(end = 8.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Gambar $name",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(120.dp)
                    .width(300.dp)

            )
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = price,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}


data class Restaurant(
    val name: String,
    val location: String,
    val imageRes: Int,
    val id: String
)

val restaurantList = listOf(
    Restaurant("Restoran B88", "Suhat - Malang", R.drawable.resto_nasgor, "RES01"),
    Restaurant("Resto Aji", "Dinoyo - Malang", R.drawable.resto_wadimor, "RES02"), // Dummy
    Restaurant("Warmindo", "Jaksel - Malang", R.drawable.resto_juna, "RES03") // Dummy
)

@Composable
fun NearbyRestaurantsList(navController: NavHostController) {
    LazyRow(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        items(restaurantList) { restaurant ->
            RestaurantCard(
                restaurant = restaurant,
                onClick = {
                    navController.navigate("detailRestoran/${restaurant.id}")
                }
            )
        }
    }
}

@Composable
fun RestaurantCard(restaurant: Restaurant, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(160.dp)
            .padding(end = 8.dp)
            .clickable(onClick = onClick), // Navigasi saat ditekan
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = restaurant.imageRes),
                contentDescription = "Gambar ${restaurant.name}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(100.dp)
                    .width(200.dp)
            )
            Text(
                text = restaurant.name,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = restaurant.location,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    LeftOverLoveTheme() {
        val navController = rememberNavController()
        HomeScreen(navController = navController)
    }
}