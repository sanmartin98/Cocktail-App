package com.example.tragosapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tragosapp.AppDatabase
import com.example.tragosapp.R
import com.example.tragosapp.data.DataSource
import com.example.tragosapp.data.model.Drink
import com.example.tragosapp.data.model.DrinkEntity
import com.example.tragosapp.domain.RepoImpl
import com.example.tragosapp.ui.adapters.DrinkRecyclerViewAdapter
import com.example.tragosapp.ui.viewModel.MainViewModel
import com.example.tragosapp.ui.viewModel.VMFactory
import com.example.tragosapp.vo.Resource
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment(), DrinkRecyclerViewAdapter.OnDrinkClickListener {

    private val viewModel by activityViewModels<MainViewModel>{ VMFactory(
        RepoImpl(
            DataSource(
        AppDatabase.getInstance(requireActivity().applicationContext))
        )
    ) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerview()
        setupObservers()

    }

    fun setupObservers(){
        viewModel.getFavoriteDrinks().observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Resource.Loading -> {}
                is Resource.Success -> {
                    val listDrink = result.data.map {
                        Drink(it.idDrink, it.imagen, it.nombre, it.descripcion)
                    }
                    rv_favorite_drink.adapter = DrinkRecyclerViewAdapter(requireContext(), listDrink, this)
                }
                is Resource.Failure -> {}
            }
        })
    }

    fun setupRecyclerview(){
        rv_favorite_drink.layoutManager = LinearLayoutManager(requireContext())
        rv_favorite_drink.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    override fun onDrinkClic(drink: Drink, position: Int) {
        viewModel.deleteDrinkFavorite(DrinkEntity(drink.idDrink, drink.imagen, drink.nombre, drink.descripcion))
        rv_favorite_drink.adapter?.notifyItemRemoved(position)
        rv_favorite_drink.adapter?.notifyDataSetChanged()
        Toast.makeText(requireContext(), "Se elimino de los favoritos", Toast.LENGTH_SHORT).show()
    }
}