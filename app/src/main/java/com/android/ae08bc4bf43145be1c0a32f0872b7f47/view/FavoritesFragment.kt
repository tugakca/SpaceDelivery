package com.android.ae08bc4bf43145be1c0a32f0872b7f47.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.R
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.adapter.FavoritesAdapter
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.databinding.FragmentFavoritesBinding
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.model.Station
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.utils.FavoriteListener
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.viewmodel.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(), FavoriteListener {
    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var favAdapter: FavoritesAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favAdapter = FavoritesAdapter(arrayListOf(), this)
        binding.favRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.favRv.adapter = favAdapter

        viewModel.getFavList()

        setObservers()

    }

    private fun setObservers() {
        viewModel.updateFav.observe(viewLifecycleOwner, {

            binding.error.visibility = View.GONE
            binding.favRv.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE

            favAdapter.updateFav(it)

            if (favAdapter.itemCount == 0) {
                binding.error.visibility = View.VISIBLE
                binding.error.text = "Liste Boş"
            }

        })
        viewModel.favStationLiveData.observe(viewLifecycleOwner, {
            binding.error.visibility = View.GONE
            binding.favRv.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
            favAdapter.clearList()
            favAdapter.addToList(it)

            if (it.size == 0) {
                binding.error.visibility = View.VISIBLE
                binding.error.text = "Liste Boş"

            }

        })
        viewModel.loading.observe(viewLifecycleOwner, {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
                binding.favRv.visibility = View.GONE
                binding.error.visibility = View.GONE
            }

        })
        viewModel.error.observe(viewLifecycleOwner, {

            binding.error.visibility = View.VISIBLE
            binding.favRv.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
            binding.error.text = it.message.toString()

        })

    }

    override fun favoriteClicked(addToFav: Boolean, station: Station) {
        viewModel.updateFav(addToFav, station)
    }


}