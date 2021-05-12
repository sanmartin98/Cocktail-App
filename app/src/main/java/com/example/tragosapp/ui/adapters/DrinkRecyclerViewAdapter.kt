package com.example.tragosapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tragosapp.R
import com.example.tragosapp.data.model.Drink
import kotlinx.android.synthetic.main.drink_card.view.*

class DrinkRecyclerViewAdapter(private val context: Context, private val drinksList: List<Drink>,
                                private val itemClickListener: OnDrinkClickListener
):RecyclerView.Adapter<DrinkRecyclerViewAdapter.DrinkViewHolder>() {

    interface OnDrinkClickListener{
        fun onDrinkClic(drink:Drink, position:Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        return DrinkViewHolder(LayoutInflater.from(context).inflate(R.layout.drink_card, parent, false))
    }

    override fun getItemCount(): Int {
        return drinksList.size
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        holder.bind(drinksList[position], position)
    }

    inner class DrinkViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(item: Drink, position: Int){
            Glide.with(context).load(item.imagen).centerCrop().into(itemView.img_drink)
            itemView.tv_title.text = item.nombre
            itemView.tv_description.text = item.descripcion
            itemView.setOnClickListener { itemClickListener.onDrinkClic(item, position) }
        }
    }
}