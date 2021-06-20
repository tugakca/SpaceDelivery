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
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.utils.DistanceUtil
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.utils.TravelClickListener


class ViewPagerAdapter(var stationList: List<Station>, val travelLister: TravelClickListener) : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

  private var pos =0
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
        val isFarAway = stationDistance > currentEUS
        val capacityProblem = stationList[position].need > currentUGS
        // if station needs volume  bigger than ship capacity or  station is far away


        if(stationList[position].isDone!=null &&stationList[position].isDone!! ){
            holder.binding.travelButton.visibility=View.GONE
            holder.binding.error.visibility=View.VISIBLE
            holder.binding.error.text="Görev Tamamlandı bro !"
            holder.binding.need.text="İhtiyaç:0"
            holder.bind(stationList[position])

        }

        if (capacityProblem || isFarAway) {
            holder.binding.travelButton.visibility = View.GONE
            holder.binding.error.visibility = View.VISIBLE
            if (capacityProblem && !isFarAway)
                holder.binding.error.text = "İstasyonun ihtiyacı kadar giysin yok! "
            else if (!capacityProblem && isFarAway)
                holder.binding.error.text = "Fazla Uzak!"
            else {
                holder.binding.error.text = "Burdan sana yâr olmaz. "
            }
        }


        holder.binding.travelButton.setOnClickListener {


            stationList[position].stock=stationList[position].stock+stationList[position].need
            stationList[position].need=0
            travelLister.onClicked(stationList[position],stationDistance)
            holder.binding.error.visibility=View.VISIBLE
            holder.binding.travelButton.visibility=View.GONE



        }
    }


    override fun getItemCount(): Int = stationList.size


     fun updateItem(station:Station){

        for(i in stationList.indices){
            if(stationList[i].name==station.name){
                notifyItemChanged(i)
            }
        }

    }


}