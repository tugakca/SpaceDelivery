package com.android.ae08bc4bf43145be1c0a32f0872b7f47.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.App
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.R
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.databinding.FragmentDetailBinding
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.model.Ship
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.viewmodel.ShipViewModel
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.viewmodel.StationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*

@AndroidEntryPoint
class ShipFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: ShipViewModel by viewModels()

    private var durabilityScore = 1
    private var speedScore = 1
    private var capasityScore = 1
    private var isMax = false


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSeekBars()
        viewModel.shipInfoLiveData.observe(viewLifecycleOwner,{
            App.shipInfo=it
            findNavController().navigate(R.id.stationFragment)

        })
        binding.goButton.setOnClickListener {


            if(durabilityScore +speedScore+capasityScore!=15){
                Toast.makeText(requireContext()," 15 puan dağıtılmadı! ",Toast.LENGTH_SHORT).show()

            }else{
                val ship = Ship(shipNameEt.text.toString(),durabilityScore,capasityScore,speedScore,"Dünya",0.0,0.0,0.0,0.0,0.0,0)
                viewModel.saveShipInfo(ship)
            }


        }

    }

    private fun setSeekBars() {

        binding.durabilitySb.progress = 1
        binding.speedSb.progress = 1
        binding.capasitySb.progress = 1


        binding.durabilitySb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                val MIN = 1
                if (progress < MIN) {

                    durabilityScore = 0
                    binding.durabilitySb.progress = 1
                } else {
                    if (durabilityScore + speedScore + capasityScore >= 15 && progress > durabilityScore) {

                        binding.durabilitySb.progress = durabilityScore
                        isMax = true
                    } else {
                        if (progress + speedScore + capasityScore > 15) {
                            binding.durabilitySb.progressTintList = ContextCompat.getColorStateList(requireContext(), R.color.black)
                            binding.durabilitySb.thumbTintList = ContextCompat.getColorStateList(requireContext(), R.color.black)

                        } else {
                            binding.durabilitySb.progressTintList = ContextCompat.getColorStateList(requireContext(), R.color.teal_200)
                            binding.durabilitySb.thumbTintList = ContextCompat.getColorStateList(requireContext(), R.color.teal_200)
                            durabilityScore = progress
                        }

                        isMax = false
                    }
                }

                val text = "Durability($durabilityScore)"
                binding.durabilityTv.text = text
                if (durabilityScore <= 13)
                    binding.currentScore.text = (durabilityScore + speedScore + capasityScore).toString()


            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {


            }
        })

        binding.speedSb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                val MIN = 1
                if (progress < MIN) {
                    speedScore = 0
                    binding.speedSb.progress = 1
                } else {
                    if (durabilityScore + speedScore + capasityScore >= 15 && progress > speedScore) {

                        binding.speedSb.progress = speedScore
                        isMax = true
                    } else {

                        if (durabilityScore + progress + capasityScore > 15) {
                            binding.speedSb.progressTintList = ContextCompat.getColorStateList(requireContext(), R.color.black)
                            binding.speedSb.thumbTintList = ContextCompat.getColorStateList(requireContext(), R.color.black)

                        } else {
                            binding.speedSb.progressTintList = ContextCompat.getColorStateList(requireContext(), R.color.teal_200)
                            binding.speedSb.thumbTintList = ContextCompat.getColorStateList(requireContext(), R.color.teal_200)
                            speedScore = progress
                        }
                        isMax = false
                    }
                }
                val text = "Hız($speedScore)"
                binding.speedTv.text = text
                if (speedScore <= 13)
                    binding.currentScore.text = (durabilityScore + speedScore + capasityScore).toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

        binding.capasitySb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val MIN = 1
                if (progress < MIN) {
                    capasityScore = 1
                    binding.capasitySb.progress = 1
                } else {
                    if (durabilityScore + speedScore + capasityScore >= 15 && progress > capasityScore) {
                        binding.capasitySb.progress = capasityScore
                        isMax = true
                    } else {
                        if (durabilityScore + speedScore + progress > 15) {
                            binding.capasitySb.progressTintList = ContextCompat.getColorStateList(requireContext(), R.color.black)
                            binding.capasitySb.thumbTintList = ContextCompat.getColorStateList(requireContext(), R.color.black)

                        } else {
                            binding.capasitySb.progressTintList = ContextCompat.getColorStateList(requireContext(), R.color.teal_200)
                            binding.capasitySb.thumbTintList = ContextCompat.getColorStateList(requireContext(), R.color.teal_200)
                            capasityScore = progress
                        }
                        isMax = false

                    }
                }
                val text = "Kapasite($capasityScore)"
                binding.capasityTv.text = text
                if (capasityScore <= 13)
                    binding.currentScore.text = (durabilityScore + speedScore + capasityScore).toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

    }


}