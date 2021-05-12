package com.example.tragosapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.tragosapp.AppDatabase
import com.example.tragosapp.R
import com.example.tragosapp.data.DataSource
import com.example.tragosapp.data.model.Drink
import com.example.tragosapp.data.model.DrinkEntity
import com.example.tragosapp.domain.RepoImpl
import com.example.tragosapp.ui.viewModel.MainViewModel
import com.example.tragosapp.ui.viewModel.VMFactory
import kotlinx.android.synthetic.main.fragment_tragos_detail.*

class DrinkDetailFragment : Fragment() {

    private val viewModel by activityViewModels<MainViewModel>{ VMFactory(RepoImpl(DataSource(
        AppDatabase.getInstance(requireActivity().applicationContext)))) }


    private lateinit var drink: Drink

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments().let {
            drink = it.getParcelable("drink")!!
            Log.d("detail drink", drink.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tragos_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDetailDrink()

        btn_save_or_delete_cocktail.setOnClickListener{
            viewModel.saveDrink(DrinkEntity(drink.idDrink, drink.imagen, drink.nombre, drink.descripcion))
            Toast.makeText(requireContext(), "Se guardo el trago en favoritos", Toast.LENGTH_SHORT).show()
        }
    }

    fun setupDetailDrink(){
        Glide.with(requireContext()).load(drink.imagen).centerCrop().into(img_cocktail)
        cocktail_title.text = drink.nombre
        cocktail_desc.text = drink.descripcion
    }




}