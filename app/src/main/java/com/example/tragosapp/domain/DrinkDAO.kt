package com.example.tragosapp.domain

import androidx.room.*
import com.example.tragosapp.data.model.DrinkEntity

@Dao
interface DrinkDAO {

    @Query("SELECT * FROM DrinkEntity")
    suspend fun getAllDrinks():List<DrinkEntity>

    //El onClonflict sirve para que no se ingresen datos repetidos, si ingresan un dato igual lo remplaza
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(drink:DrinkEntity)

    @Delete
    suspend fun deleteDrink(drink: DrinkEntity)

}