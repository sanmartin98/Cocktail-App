package com.example.tragosapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tragosapp.AppDatabase
import com.example.tragosapp.R
import com.example.tragosapp.data.DataSource
import com.example.tragosapp.data.model.Drink
import com.example.tragosapp.domain.RepoImpl
import com.example.tragosapp.ui.adapters.DrinkRecyclerViewAdapter
import com.example.tragosapp.ui.viewModel.MainViewModel
import com.example.tragosapp.ui.viewModel.VMFactory
import com.example.tragosapp.vo.Resource
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(),
    DrinkRecyclerViewAdapter.OnDrinkClickListener {

    private val viewModel by activityViewModels<MainViewModel>{ VMFactory(RepoImpl(DataSource(
        AppDatabase.getInstance(requireActivity().applicationContext)))) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearchView()
        setupObservers()
        btn_favorites.setOnClickListener{
            findNavController().navigate(R.id.action_mainFragment_to_favoritesFragment)
        }
    }

    private fun setupObservers(){
        viewModel.fetchDrinkList.observe(viewLifecycleOwner, Observer { result ->
            run {
                when (result) {
                    is Resource.Loading -> {
                        progress_bar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        progress_bar.visibility = View.GONE
                        rv_drinks.adapter =
                            DrinkRecyclerViewAdapter(
                                requireContext(),
                                result.data,
                                this
                            )
                    }
                    is Resource.Failure -> {
                        progress_bar.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            "Ocurrio un error al traer los datos ${result.exception}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }

    private fun setupSearchView(){
        search_view.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.setDrink(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun setupRecyclerView(){
        rv_drinks.layoutManager = LinearLayoutManager(requireContext())
        rv_drinks.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    override fun onDrinkClic(drink: Drink, position: Int) {
        val bundle = Bundle()
        bundle.putParcelable("drink", drink)
        findNavController().navigate(R.id.action_mainFragment_to_tragosDetailFragment, bundle)
    }

}