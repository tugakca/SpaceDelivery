package com.android.ae08bc4bf43145be1c0a32f0872b7f47.view

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.App
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.R
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.adapter.ViewPagerAdapter
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.databinding.FragmentStationBinding
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.model.Station
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.utils.SharedPrefUtil
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.utils.TravelClickListener
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.viewmodel.StationViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class StationFragment : Fragment(),TravelClickListener {
    private val viewModel: StationViewModel by viewModels()
    private lateinit var binding: FragmentStationBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var sharedPrefUtil: SharedPrefUtil
    private var refreshTime = 0L //nanosn
      private var timer: Timer? = Timer()


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
        if(App.shipInfo!=null){
            binding.model=App.shipInfo
            refreshTime =( App.shipInfo?.durability!!.times( 10000)).toLong()
            setTimer()
        }
        viewModel.getStationList()
        setObservers()

    }
    fun setObservers(){



        viewModel.updateMissionLiveData.observe(viewLifecycleOwner,{
            it?.let {item->
                viewPagerAdapter.updateItem(item)
            }


        })
        viewModel.stationLiveData.observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) {
                createViewPager(it)

                it.forEach {
                    if(it.name=="Dünya"){
                        binding.currentLocation.text=it.name
                    }
                }

            }
        })

        viewModel.loading.observe(viewLifecycleOwner, {

        })

        viewModel.error.observe(viewLifecycleOwner, {

        })


    }

    private fun createViewPager(stationList: List<Station>) {

        val list = stationList.toMutableList()
        stationList.forEach {
            if(it.name=="Dünya"){
                list.remove(it)
            }
        }
        viewPagerAdapter = ViewPagerAdapter(list.toList(),this)
        binding.viewpager.adapter = viewPagerAdapter
        binding.viewpager.clipToPadding = false
        binding.viewpager.clipChildren = false
        binding.viewpager.offscreenPageLimit = 3
        binding.viewpager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL)
        binding.viewpager.setPageTransformer( MarginPageTransformer(1500))
        binding.viewpager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewPagerAdapter.notifyDataSetChanged()
            }
        })


    }

    private fun setTimer() {
        val timer :CountDownTimer = object: CountDownTimer(refreshTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val value  = millisUntilFinished/1000
                binding.timer.text = "$value s"
            }
            override fun onFinish() {
                binding.damage.text =(Integer.parseInt(binding.damage.text.toString())-10).toString()
            }
        }
        timer.start()
        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                if (sharedPrefUtil.getTime()!! != 0L && (System.currentTimeMillis() - sharedPrefUtil.getTime()!!) > refreshTime) {

                    sharedPrefUtil.saveTime(System.currentTimeMillis())
                    timer.start()
                }else{

                }
            }
        }
        try {

            this.timer?.scheduleAtFixedRate(timerTask, 0, 100)
        } catch (exception: IllegalStateException) {

        }
    }

    override fun onClicked(station:Station,distance:Double) {
        super.onClicked(station,distance)

        App.shipInfo?.currentEUS =App.shipInfo?.currentEUS!!.minus(distance)
        App.shipInfo?.currentUGS =App.shipInfo?.currentUGS!!.minus(station.need)
        App.shipInfo?.currentLocation=station.name
        App.shipInfo?.dirX=station.coordinateX
        App.shipInfo?.dirY=station.coordinateY
        binding.currentLocation.text=station.name

        val eusText ="EUS: " +String.format("%.2f", App.shipInfo?.currentEUS)
        binding.shipEUS.text =eusText
        val ugsText ="UGS: "  + App.shipInfo?.currentUGS
        binding.shipUGS.text =ugsText
        viewModel.updateMission(station)

    }


}