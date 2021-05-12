package com.example.tragosapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tragosapp.data.model.DrinkEntity
import com.example.tragosapp.domain.DrinkDAO

@Database(entities = [DrinkEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun drinkDao(): DrinkDAO

    companion object{
        private var INSTANCE: AppDatabase ?= null

        //databaseBuilder permite crear una tabla en la memoria del telefono, por lo tanto esos datos solo se van a borrar cuando sean destruidos desde la app o el celular
        //si se quiere guandar los datos solo mientras la ejecucion de la app se puede utilizar inMemoryDatabaseBuilder
        fun getInstance(context: Context): AppDatabase{
            INSTANCE = INSTANCE ?: Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "drink_table").build()
            return INSTANCE!!
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}