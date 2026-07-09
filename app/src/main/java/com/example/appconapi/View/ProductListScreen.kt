package com.example.appconapi.View

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appconapi.ViewModel.ProductViewModel

@Composable
fun ProductListScreen(
    viewModel: ProductViewModel = viewModel(),
    onProductClick: (Int) -> Unit
) {
    val filteredProducts by viewModel.filteredProducts.collectAsState()
    val searchText by viewModel.searchText.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Column {
        // Barra de búsqueda
        SearchBar(
            value = searchText,
            onValueChange = viewModel::searchProducts,
            placeholder = "Buscar productos..."
        )

        // Contenido principal
        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Text(text = error ?: "Error desconocido")
                        Button(onClick = viewModel::fetchProducts) {
                            Text("Reintentar")
                        }
                    }
                }
            }

            filteredProducts.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No se encontraron productos")
                }
            }

            else -> {
                LazyColumn {
                    items(filteredProducts.size) { index ->
                        val product = filteredProducts[index]
                        ProductCard(
                            product = product,
                            onClick = { onProductClick(product.id) }
                        )
                    }
                }
            }
        }
    }
}