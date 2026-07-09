package com.example.appconapi.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.appconapi.ViewModel.ProductViewModel

@Composable
fun ProductDetailScreen(
    productId: Int?,
    viewModel: ProductViewModel = viewModel()
) {
    val selectedProduct by viewModel.selectedProduct.collectAsState()
    val isLoadingDetail by viewModel.isLoadingDetail.collectAsState()
    val error by viewModel.error.collectAsState()

    // ✅ Cuando el ID cambia, cargar el detalle
    LaunchedEffect(productId) {
        productId?.let {
            println("🔄 Cargando detalle para ID: $it")
            viewModel.fetchProductDetail(it)
        }
    }

    // ✅ Cuando salimos de la pantalla, limpiar
    LaunchedEffect(Unit) {
        viewModel.clearSelectedProduct()
    }

    when {
        // Estado de carga
        isLoadingDetail -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Cargando detalle...")
                }
            }
        }

        // Estado de error
        error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("❌ $error")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { productId?.let { viewModel.fetchProductDetail(it) } }) {
                        Text("Reintentar")
                    }
                }
            }
        }

        // Producto no encontrado
        selectedProduct == null && !isLoadingDetail -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Producto no encontrado")
            }
        }

        // Mostrar el detalle
        selectedProduct != null -> {
            val product = selectedProduct!!

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Imagen
                AsyncImage(
                    model = product.thumbnail,
                    contentDescription = product.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Título
                Text(
                    text = product.title,
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Categoría y marca
                Text(
                    text = "${product.category} • ${product.brand ?: "Sin marca"}",
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Precio
                Text(
                    text = "$${String.format("%.2f", product.price)}",
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Descripción
                Text(
                    text = product.description,
                    fontSize = 16.sp
                )
            }
        }
    }
}