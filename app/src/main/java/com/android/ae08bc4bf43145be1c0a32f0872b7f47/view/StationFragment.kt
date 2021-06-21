package com.android.ae08bc4bf43145be1c0a32f0872b7f47.view

import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.App
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.R
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.adapter.ViewPagerAdapter
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.databinding.FragmentStationBinding
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.model.Ship
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.model.Station
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.utils.*
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.viewmodel.StationViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class StationFragment : Fragment(), TravelClickListener, FavoriteListener {
    private val viewModel: StationViewModel by viewModels()
    private lateinit var binding: FragmentStationBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var sharedPrefUtil: SharedPrefUtil
    private var refreshTime = 0L //nanosn
    private var timer: Timer? = Timer()
    private var currentEUS = 0.0
    private var currentUGS = 0.0
    private var currentLocation = "Dünya"
    private lateinit var stationList: List<Station>
    private var timerCD: CountDownTimer? = null
    private var goEarth = false

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_station, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPrefUtil = SharedPrefUtil(requireContext().applicationContext)
        sharedPrefUtil.saveTime(System.currentTimeMillis())
        stationList = emptyList()
        if (App.shipInfo != null) {
            if(App.shipInfo?.name.isNullOrEmpty())
                App.shipInfo?.name="#9"
            binding.model=App?.shipInfo
        }else{
            viewModel.getShipInfo()
        }
        if(App?.shipInfo?.allMissionsDone!=null && !App?.shipInfo?.allMissionsDone!!){
            refreshTime = (App.shipInfo?.durability!!.times(10000)).toLong()
            if (App.shipInfo?.damage != 0)
                setTimer()

        }
        viewModel.getStationList()
        search()
        setObservers()
//        binding.shipNameTv.setOnClickListener {
//            findNavController().navigate(R.id.detailFragment)
//        }

    }

    fun setObservers() {
        viewModel.searchLiveData.observe(viewLifecycleOwner, {
            binding.progressBar.visibility = View.GONE
            binding.currentLocation.visibility = View.GONE

            if (it.isNullOrEmpty()) {
                binding.viewpagerLay.visibility = View.GONE
                binding.currentLocation.visibility = View.GONE
                binding.searchError.visibility = View.VISIBLE
                binding.searchError.text = "Sonuç bulunamadı."
            } else {
                binding.searchError.visibility = View.GONE
                binding.viewpagerLay.visibility = View.VISIBLE
                viewPagerAdapter.setSearchList(it!!.toList())
            }

        })

        viewModel.favOperationLiveData.observe(viewLifecycleOwner, {
            viewPagerAdapter.updateItem(it)
        })
        viewModel.updateShipInfoLiveData.observe(viewLifecycleOwner, {

            App.shipInfo = it
            binding.model = it

        })
        viewModel.shipInfoLiveData.observe(viewLifecycleOwner, {

            if (it.name.isNullOrEmpty()) {
                it.name = "#9"
                App?.shipInfo?.name = "#9"
            }
            App.shipInfo = it
            binding.model = it
            refreshTime = (it.durability!!.times(10000)).toLong()

//            if (it.damage != 0)
//                setTimer()
        })
        viewModel.updateMissionLiveData.observe(viewLifecycleOwner, {
            it?.let { item ->
                viewPagerAdapter.updateItem(item)

                updateShipInfo()
            }
        })
        viewModel.stationLiveData.observe(viewLifecycleOwner, {
            binding.progressBar.visibility = View.GONE
            binding.viewpagerLay.visibility = View.VISIBLE
            binding.error.visibility = View.GONE

            if (!it.isNullOrEmpty() && App?.shipInfo!=null) {

                createViewPager(it)
                stationList = it
                it.forEach {
                    if (it.name == "Dünya") {
                        binding.currentLocation.text = it.name
                    }
                }

            }
        })
        viewModel.loading.observe(viewLifecycleOwner, {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
                binding.viewpagerLay.visibility = View.GONE
                binding.error.visibility = View.GONE
            }
        })
        viewModel.error.observe(viewLifecycleOwner, {
        })
    }

    private fun createViewPager(stationList: List<Station>) {
        val list = stationList.toMutableList()
        stationList.forEach {
            if (it.name == "Dünya") {
                list.remove(it)
            }
        }
        viewPagerAdapter = ViewPagerAdapter(emptyList(), this, this)
        viewPagerAdapter.addList(list)
        binding.viewpager.adapter = viewPagerAdapter
        if (App.shipInfo?.damage == 0) {
            viewPagerAdapter.goEarth(Constants.DAMAGE)
        }
        binding.viewpager.clipToPadding = false
        binding.viewpager.clipChildren = false
        binding.viewpager.offscreenPageLimit = 3
        binding.viewpager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL)
        binding.viewpager.setPageTransformer(object : ViewPager2.PageTransformer {
            override fun transformPage(page: View, position: Float) {
                if (position < -1) {
                    page.setAlpha(0f);
                } else if (position <= 0) {
                    page.setAlpha(1f);
                    page.setTranslationX(0f);
                    page.setScaleX(1f);
                    page.setScaleY(1f);

                } else if (position <= 1) {
                    page.setTranslationX(-position * page.getWidth());
                    page.setAlpha(1 - Math.abs(position));
                    page.setScaleX(1 - Math.abs(position));
                    page.setScaleY(1 - Math.abs(position));

                } else {
                    page.setAlpha(0F);

                }
            }

        })
        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewPagerAdapter.notifyDataSetChanged()
            }
        })


    }

    private fun updateShipInfo() {
        val damage = binding.damage.text.toString().toInt()
        if (damage != 0)
            checkAllMissionsDone()

        val ship = Ship(
                App.shipInfo?.name,
                App.shipInfo?.durability,
                App.shipInfo?.capasity,
                App.shipInfo?.speed, App.shipInfo?.currentLocation,
                App.shipInfo?.dirX,
                App.shipInfo?.dirY,
                App.shipInfo?.currentUGS, App.shipInfo?.currentEUS,
                App.shipInfo?.currentDS, damage)
        ship.shipUuid = App.shipInfo?.shipUuid
        ship.allMissionsDone = App.shipInfo?.allMissionsDone!!
        viewModel.updateShipInfo(ship)
    }

    private fun setTimer() {
        timerCD = object : CountDownTimer(refreshTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val value = millisUntilFinished / 1000
                binding.timer.text = "$value s"
            }

            override fun onFinish() {
                requireActivity().runOnUiThread {
                    if (binding.damage.text.toString() != "0")
                        binding.damage.text = (Integer.parseInt(binding.damage.text.toString()) - 10).toString()
                }

            }
        }
        timerCD?.start()
        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                if (sharedPrefUtil.getTime()!! != 0L && (System.currentTimeMillis() - sharedPrefUtil.getTime()!!) > refreshTime) {
                    sharedPrefUtil.saveTime(System.currentTimeMillis())
                    val damage = binding.damage.text.toString().toInt()
                    if (damage == 0) {
                        goEarth = true
                        timerCD?.onFinish()
                        requireActivity().runOnUiThread {
                            viewPagerAdapter.goEarth(Constants.DAMAGE)
                            binding.damage.text = "0"
                            for (i in stationList.indices) {
                                if (stationList[i].name == "Dünya") {
                                    App.shipInfo?.dirX = stationList[i].coordinateX
                                    App.shipInfo?.dirY = stationList[i].coordinateY
                                    App.shipInfo?.currentLocation = stationList[i].name
                                    App.shipInfo?.damage = 0
                                    binding.model = App.shipInfo
                                    updateShipInfo()
                                }
                            }
                        }
                    } else {
                        if (!goEarth) {
                            timerCD?.start()
                        }

                    }
                }
            }
        }
        try {

            this.timer?.scheduleAtFixedRate(timerTask, 0, 100)
        } catch (exception: IllegalStateException) {

        }
    }

    override fun onClicked(station: Station, distance: Double) {
        super.onClicked(station, distance)
        currentEUS = App.shipInfo?.currentEUS!!.minus(distance)
        currentUGS = App.shipInfo?.currentUGS!!.minus(station.need)
        currentLocation = station.name
        App.shipInfo?.currentEUS = currentEUS
        App.shipInfo?.currentUGS = currentUGS
        App.shipInfo?.currentLocation = currentLocation
        App.shipInfo?.dirX = station.coordinateX
        App.shipInfo?.dirY = station.coordinateY
        station.need = 0
        val eusText = "EUS: " + String.format("%.2f", App.shipInfo?.currentEUS)
        binding.shipEUS.text = eusText
        val ugsText = "UGS: " + App.shipInfo?.currentUGS
        binding.shipUGS.text = ugsText

        viewModel.updateMission(station)
    }

    private fun checkAllMissionsDone() {
        var missionDoneList = arrayListOf<Station>()
        var missionFailedList = arrayListOf<Station>()

        val list = stationList.toMutableList()
        stationList.forEach {
            if (it.name == "Dünya") {
                list.remove(it)
            }
        }

        for (position in stationList.indices) {
            val stationDistance = DistanceUtil.calculateDistance(App.shipInfo?.dirX!!, stationList[position].coordinateX, App.shipInfo?.dirY!!, stationList[position].coordinateY)
            stationList[position].eus = stationDistance
        }
        list.forEach {

            if (it.isDone != null && it.isDone == true) {
                missionDoneList.add(it)
            } else if (it.need > App?.shipInfo?.currentUGS!!) {
                missionFailedList.add(it)
            } else if (it.eus!! > App?.shipInfo?.currentEUS!!) {
                missionFailedList.add(it)
            }
        }

        if (missionDoneList.size + missionFailedList.size == stationList.size - 1) {
            goEarth = true
            requireActivity().runOnUiThread { timerCD?.onFinish() }
        }
        if (goEarth) {
            viewPagerAdapter.goEarth(Constants.ALL)
            for (i in stationList.indices) {
                if (stationList[i].name == "Dünya") {
                    App.shipInfo?.dirX = stationList[i].coordinateX
                    App.shipInfo?.dirY = stationList[i].coordinateY
                    App.shipInfo?.currentLocation = stationList[i].name
                    App.shipInfo?.allMissionsDone = true
                    binding.model = App.shipInfo
                }
            }
        }

    }

    override fun favoriteClicked(addToFav: Boolean, station: Station) {
        viewModel.favoriteOperation(addToFav, station)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        timerCD?.onFinish()
        timer?.cancel()
    }

    private fun search() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.viewpagerLay.visibility = View.GONE
                    binding.currentLocation.visibility = View.GONE
                    viewModel.onSearchQuery(newText)
                } else {
                    viewPagerAdapter.clearSearchList()
                    binding.searchError.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    binding.viewpagerLay.visibility = View.VISIBLE
                    binding.currentLocation.visibility = View.VISIBLE
                }
                return false
            }
        })
    }


}