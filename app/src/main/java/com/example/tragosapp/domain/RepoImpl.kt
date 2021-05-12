package com.example.tragosapp.domain

import com.example.tragosapp.data.DataSource
import com.example.tragosapp.data.model.Drink
import com.example.tragosapp.data.model.DrinkEntity
import com.example.tragosapp.vo.Resource

class RepoImpl(private val dataSource: DataSource): Repo {
    override suspend fun getTragoList(drinkName: String): Resource<List<Drink>> {
        return dataSource.getDrinksByName(drinkName)

    }

    override suspend fun getDrinkFavorite(): Resource<List<DrinkEntity>> {
        return dataSource.getFavoritesDrink()
    }

    override suspend fun insertDrink(drink: DrinkEntity) {
        dataSource.insertDrinkIntoRoom(drink)
    }

    override suspend fun deleteDrink(drink: DrinkEntity) {
        dataSource.deleteDrinkFavorite(drink)
    }
}