package com.example.tragosapp.data

import android.media.MediaPlayer
import com.example.tragosapp.AppDatabase
import com.example.tragosapp.data.model.Drink
import com.example.tragosapp.data.model.DrinkEntity
import com.example.tragosapp.vo.Resource
import com.example.tragosapp.vo.RetrofitClient

class DataSource(private val appDatabase: AppDatabase) {

    suspend fun getDrinksByName(drinkName: String): Resource<List<Drink>>{
        return Resource.Success(
            RetrofitClient.webService.getTragoByName(drinkName).drinkList
        )
    }

    suspend fun insertDrinkIntoRoom(drinkEntity: DrinkEntity){
        appDatabase.drinkDao().insertFavorite(drinkEntity)
    }

    suspend fun getFavoritesDrink(): Resource<List<DrinkEntity>>{
        return Resource.Success(appDatabase.drinkDao().getAllDrinks())
    }

    suspend fun deleteDrinkFavorite(drinkEntity: DrinkEntity){
        appDatabase.drinkDao().deleteDrink(drinkEntity)
    }
}