package com.example.appconapi.Data.Remote

import com.example.appconapi.Data.Model.Product
import com.example.appconapi.Data.Model.ProductsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

        @GET("products?limit=10")
        suspend fun getProducts(): ProductsResponse


        // ✅ NUEVO: Detalle de un producto por ID
        @GET("products/{id}")
        suspend fun getProductDetail(
                @Path("id") id: Int
        ): Product  // ✅ Devuelve un solo producto
}