package com.example.tragosapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Drink(
    @SerializedName("idDrink")
    val idDrink: String = "",
    @SerializedName("strDrinkThumb")
    val imagen: String = "",
    @SerializedName("strDrink")
    val nombre: String = "",
    @SerializedName("strInstructions")
    val descripcion: String = ""
): Parcelable

data class DrinkList(
    @SerializedName("drinks")
    val drinkList: List<Drink>
)

@Entity
data class DrinkEntity(
    @PrimaryKey
    val idDrink: String,
    @ColumnInfo(name = "image_drink")
    val imagen: String = "",
    @ColumnInfo(name = "name_drink")
    val nombre: String = "",
    @ColumnInfo(name = "desc_drink")
    val descripcion: String = ""
)