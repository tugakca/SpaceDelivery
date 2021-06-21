package com.android.ae08bc4bf43145be1c0a32f0872b7f47.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.R
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.databinding.ItemFavBinding
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.model.Station
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.utils.FavoriteListener

class FavoritesAdapter(var favList: ArrayList<Station>, var favoriteListener: FavoriteListener) : RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {

    class FavoriteViewHolder(var binding: ItemFavBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Station) {
            binding.model = item
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemFavBinding>(inflater, R.layout.item_fav, parent, false)
        return FavoriteViewHolder(binding)

    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {

        holder.setIsRecyclable(false)

        holder.bind(favList[position])
        holder.binding.favIv.setOnClickListener {
            if (!favList[position].isFavorite) {
                favoriteListener.favoriteClicked(true, favList[position])
            } else {
                favoriteListener.favoriteClicked(false, favList[position])
            }

        }
    }

    override fun getItemCount(): Int {
        return favList.size

    }

    fun addToList(list: ArrayList<Station>) {
        favList.addAll(list)
        notifyDataSetChanged()
    }

    fun clearList() {
        favList.clear()
        notifyDataSetChanged()
    }

    fun updateFav(item: Station) {
        favList.remove(item)
        notifyDataSetChanged()
    }


}