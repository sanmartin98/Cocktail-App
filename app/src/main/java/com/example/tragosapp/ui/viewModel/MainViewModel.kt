package com.example.tragosapp.ui.viewModel

import androidx.lifecycle.*
import com.example.tragosapp.data.model.DrinkEntity
import com.example.tragosapp.domain.Repo
import com.example.tragosapp.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(private val repo:Repo):ViewModel(){

    private val drinkData = MutableLiveData<String>()

    fun setDrink(drinkName: String){
        drinkData.value = drinkName
    }

    init {
        setDrink("tequila")
    }

    val fetchDrinkList = drinkData.distinctUntilChanged().switchMap { drinkName ->
        liveData(Dispatchers.IO){
            emit(Resource.Loading())
            try {
                emit(repo.getTragoList(drinkName))
            } catch (e: Exception){
                emit(Resource.Failure(e))
            }
        }
    }

    fun saveDrink(drinkEntity: DrinkEntity){
        //viewModelScope se utiliza para que la corutina identifique en que momento se elimina el fragment que la utiliza para acabar con todos los metodos que se estan ejecutando
        viewModelScope.launch {
            repo.insertDrink(drinkEntity)
        }
    }

    fun getFavoriteDrinks() = liveData(Dispatchers.IO){
        emit(Resource.Loading())
        try {
            emit(repo.getDrinkFavorite())
        } catch (e:Exception){
            emit(Resource.Failure(e))
        }
    }

    fun deleteDrinkFavorite(drinkEntity: DrinkEntity) {
        viewModelScope.launch {
            repo.deleteDrink(drinkEntity)
        }
    }

}