package com.example.tragosapp.domain

import com.example.tragosapp.data.model.Drink
import com.example.tragosapp.data.model.DrinkList
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
    @GET("search.php")
    //En Query se agrega el parametro de busqueda de la URL (ej:search.php?s=margarita, en este caso es "s")
    suspend fun getTragoByName(@Query("s") drinkName: String): DrinkList
}