package com.example.appconapi.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appconapi.Data.Model.Product
import com.example.appconapi.Data.Remote.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    // Lista de productos
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    // Lista filtrada
    private val _filteredProducts = MutableStateFlow<List<Product>>(emptyList())
    val filteredProducts: StateFlow<List<Product>> = _filteredProducts

    // Texto de búsqueda
    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

    // Estados de carga y error
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // ✅ Producto seleccionado para detalle
    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct

    // ✅ Estado de carga del detalle
    private val _isLoadingDetail = MutableStateFlow(false)
    val isLoadingDetail: StateFlow<Boolean> = _isLoadingDetail

    init {
        fetchProducts()
    }

    // Cargar lista de productos
    fun fetchProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val response = RetrofitInstance.api.getProducts()
                _products.value = response.products
                _filteredProducts.value = response.products
                //log
                println("✅ Productos cargados: ${response.products.size}")
            } catch (e: Exception) {
                _error.value = "Error al cargar: ${e.message}"
                //log
                println("❌ Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Búsqueda de productos
    fun searchProducts(query: String) {
        _searchText.value = query
        _filteredProducts.value = if (query.isEmpty()) {
            _products.value
        } else {
            _products.value.filter { product ->
                product.title.contains(query, ignoreCase = true) ||
                        product.category.contains(query, ignoreCase = true) ||
                        product.brand?.contains(query, ignoreCase = true) == true
            }
        }
    }

    // ✅ NUEVO: Cargar detalle del producto desde la API
    fun fetchProductDetail(productId: Int) {
        viewModelScope.launch {
            _isLoadingDetail.value = true
            _error.value = null
            try {
                //log
                println("🔍 Cargando detalle del producto ID: $productId")
                val product = RetrofitInstance.api.getProductDetail(productId)
                _selectedProduct.value = product
                //log
            println("✅ Producto cargado: ${product.title}")
            } catch (e: Exception) {
                _error.value = "Error al cargar detalle: ${e.message}"
                //log
                println("❌ Error en detalle: ${e.message}")
            } finally {
                _isLoadingDetail.value = false
            }
        }
    }

    // ✅ Limpiar el producto seleccionado (cuando se sale del detalle)
    fun clearSelectedProduct() {
        _selectedProduct.value = null
    }
}