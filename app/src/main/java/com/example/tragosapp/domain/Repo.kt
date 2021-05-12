package com.example.tragosapp.domain

import com.example.tragosapp.data.model.Drink
import com.example.tragosapp.data.model.DrinkEntity
import com.example.tragosapp.vo.Resource

interface Repo {
    suspend fun getTragoList(drinkName: String): Resource<List<Drink>>
    suspend fun getDrinkFavorite(): Resource<List<DrinkEntity>>
    suspend fun insertDrink(drink:DrinkEntity)
    suspend fun deleteDrink(drink: DrinkEntity)
}