package com.example.appconapi.View

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.appconapi.Data.Model.Product

@Composable
fun ProductCard(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            // Imagen con Coil (es la única dependencia externa para imágenes)
            AsyncImage(
                model = product.thumbnail,
                contentDescription = product.title,
                modifier = Modifier.size(80.dp)
            )

            Column(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                // Título
                Text(
                    text = product.title,
                    fontSize = 18.sp
                )
                // Categoría
                Text(
                    text = "Categoría: ${product.category}",
                    fontSize = 14.sp
                )
                // Marca y precio
                Text(
                    text = "${product.brand ?: "Sin marca"} • $${product.price}",
                    fontSize = 14.sp
                )
            }
        }
    }
}