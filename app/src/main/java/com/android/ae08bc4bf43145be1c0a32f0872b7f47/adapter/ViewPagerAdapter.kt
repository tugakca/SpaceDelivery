package com.android.ae08bc4bf43145be1c0a32f0872b7f47.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.App
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.R
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.databinding.ItemStationBinding
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.model.Station
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.utils.Constants
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.utils.DistanceUtil
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.utils.FavoriteListener
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.utils.TravelClickListener


class ViewPagerAdapter(var stationList: List<Station>, val travelLister: TravelClickListener, val favListener: FavoriteListener) : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    private var goEarth = false
    private var goEarthType = ""
    private var tempList = emptyList<Station>()


    inner class ViewHolder(var binding: ItemStationBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Station) {
            binding.model = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemStationBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_station, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.setIsRecyclable(false)
        holder.bind(stationList.get(position))
        val currentEUS = App.shipInfo?.currentEUS!!
        val currentUGS = App.shipInfo?.currentUGS!!
        val stationDistance = DistanceUtil.calculateDistance(App.shipInfo?.dirX!!, stationList[position].coordinateX, App.shipInfo?.dirY!!, stationList[position].coordinateY)
        stationList[position].eus = stationDistance
        val isFarAway = stationDistance > currentEUS
        val capacityProblem = stationList[position].need > currentUGS
        // if station needs volume  bigger than ship capacity or  station is far away

        if (stationList[position].isDone != null && stationList[position].isDone!!) {
            holder.binding.travelButton.visibility = View.GONE
            holder.binding.error.visibility = View.VISIBLE
            holder.binding.error.text = "G??rev Tamamland??!"
            holder.binding.need.text = "??htiya??:0"
            holder.bind(stationList[position])

        }

        if (capacityProblem || isFarAway) {
            holder.binding.travelButton.visibility = View.GONE
            holder.binding.error.visibility = View.VISIBLE
            if (capacityProblem && !isFarAway)
                holder.binding.error.text = "??stasyonun ihtiyac?? kadar giysin yok! "
            else if (!capacityProblem && isFarAway)
                holder.binding.error.text = "Fazla Uzak!"
            else {
                holder.binding.error.text = "??stasyon uzak ve  stok elinde yok  "
            }
        }

        if (goEarth) {
            var text = ""
            holder.binding.travelButton.visibility = View.GONE
            holder.binding.error.visibility = View.VISIBLE

            if (goEarthType == Constants.DAMAGE) {
                text = "G??rev iptal, gemi ??ok hasar ald??."
            } else if (goEarthType == Constants.EUS) {
                text = "G??rev iptal,di??er gezenlere gidecek kadar EUS kalmad??"
            } else if (goEarthType == Constants.ALL) {
                text = "T??m g??revler tamamland??.D??nyaya d??n??yorsun."
            } else {
                text = "G??rev iptal,di??er gezenlere yetecek kadar UGS yok"
            }
            holder.binding.error.text = text
        }

        holder.binding.travelButton.setOnClickListener {
            if (!goEarth) {
                stationList[position].stock = stationList[position].stock + stationList[position].need
                travelLister.onClicked(stationList[position], stationDistance)
                holder.binding.error.visibility = View.VISIBLE
                holder.binding.travelButton.visibility = View.GONE
            }
        }

        holder.binding.favIv.setOnClickListener {
            if (!App?.shipInfo?.allMissionsDone!!) {
                if (!stationList[position].isFavorite) {
                    favListener.favoriteClicked(true, stationList[position])
                } else {
                    favListener.favoriteClicked(false, stationList[position])
                }

            }
        }
    }


    override fun getItemCount(): Int = stationList.size


    fun updateItem(station: Station) {
        for (i in stationList.indices) {
            if (stationList[i].name == station.name) {
                notifyItemChanged(i)
            }
        }
    }

    fun goEarth(type: String) {
        goEarth = true
        goEarthType = type
        notifyDataSetChanged()
    }


    fun setSearchList(list: List<Station>) {
        stationList.toMutableList().clear()
        stationList = list
        notifyDataSetChanged()
    }

    fun clearSearchList() {
        stationList.toMutableList().clear()
        stationList = tempList
        notifyDataSetChanged()
    }

    fun addList(list: List<Station>) {
        stationList = list
        tempList = list
        notifyDataSetChanged()
    }
}