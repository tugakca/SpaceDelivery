package com.android.ae08bc4bf43145be1c0a32f0872b7f47.utils

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.App

@BindingAdapter("setUgs")
fun setUgs(view: TextView,value:Int){

    var ugsValue =value *10000
    App.shipInfo?.currentUGS=ugsValue.toDouble()
    val text = "UGS: $ugsValue"
    view.text = text
}

@BindingAdapter("setEus")
fun setEus(view: TextView,value:Int){
    var eusValue =value *20
    App.shipInfo?.currentEUS=eusValue.toDouble()
    val text = "EUS: $eusValue"
    view.text = text
}

@BindingAdapter("setDs")
fun setDs(view: TextView,value:Int){
    var ugsValue =value *10000
    App.shipInfo?.currentDS=ugsValue.toDouble()
    val text = "DS: $ugsValue"
    view.text = text
}

@BindingAdapter("setShipName")
fun setShipName(view: TextView,value:String){
    view.text = value
}

@BindingAdapter("setDsTime")
fun setDsTime(view: TextView,value:Int){
    var dsTime =value *10
    val text = " $dsTime s"
    view.text = text
}

@BindingAdapter("setStock")
fun setStock(view: TextView,value:Int){
    val text ="Stok:$value"
    view.text = text
}
@BindingAdapter("setCapacity")
fun setCapacity(view: TextView,value:Int){
    val text ="Kapasite:$value"
    view.text = text
}
@BindingAdapter("setNeed")
fun setNeed(view: TextView,value:Int){
    val text ="İhtiyaç:$value"
    view.text = text
}

@BindingAdapter(value= ["setDirX", "setDirY"],requireAll = true)
fun setDistance(view: TextView,dirX:Double,dirY:Double){
     val distance = DistanceUtil.calculateDistance(App.shipInfo?.dirX!!,dirX,App.shipInfo?.dirY!!,dirY)
     val text = String.format("%.2f", distance)
    view.text="Distance: $text EUS"
}