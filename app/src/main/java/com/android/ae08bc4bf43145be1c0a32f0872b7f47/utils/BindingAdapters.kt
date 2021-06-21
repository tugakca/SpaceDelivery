package com.android.ae08bc4bf43145be1c0a32f0872b7f47.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.App
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("setUgs")
fun setUgs(view: TextView, value: Int) {

    var text = ""
    if (App.shipInfo?.currentUGS != null) {
        text = "UGS:" + App.shipInfo?.currentUGS
    } else {
        var ugsValue = value * 10000
        App.shipInfo?.currentUGS = ugsValue.toDouble()
        text = "UGS: $ugsValue"
    }

    view.text = text
}

@BindingAdapter("setEus")
fun setEus(view: TextView, value: Int) {
    var text = ""
    if (App.shipInfo?.currentEUS != null) {
        text = "EUS: " + String.format("%.2f", App.shipInfo?.currentEUS)
    } else {
        var eusValue = value * 20
        App.shipInfo?.currentEUS = eusValue.toDouble()
        text = "EUS: $eusValue"

    }
    view.text = text
}

@BindingAdapter("setDs")
fun setDs(view: TextView, value: Int) {
    var ugsValue = value * 10000
    App.shipInfo?.currentDS = ugsValue.toDouble()
    val text = "DS: $ugsValue"
    view.text = text
}

@BindingAdapter("setShipName")
fun setShipName(view: TextView, value: String?) {
    value?.let {
        view.text = it
    }

}

@BindingAdapter("setDsTime")
fun setDsTime(view: TextView, value: Int) {
    var dsTime = value * 10
    val text = " $dsTime s"
    view.text = text
}

@BindingAdapter("setStock")
fun setStock(view: TextView, value: Int) {
    val text = "Stok:$value"
    view.text = text
}

@BindingAdapter("setCapacity")
fun setCapacity(view: TextView, value: Int) {
    val text = "Kapasite:$value"
    view.text = text
}

@BindingAdapter("setNeed")
fun setNeed(view: TextView, value: Int) {
    val text = "İhtiyaç:$value"
    view.text = text
}

@BindingAdapter(value = ["setDirX", "setDirY"], requireAll = true)
fun setDistance(view: TextView, dirX: Double, dirY: Double) {
    val distance = DistanceUtil.calculateDistance(App.shipInfo?.dirX!!, dirX, App.shipInfo?.dirY!!, dirY)
    val text = String.format("%.2f", distance)
    view.text = "Uzaklık: $text EUS"
}


@BindingAdapter("setIcon")
fun setIcon(view: ImageView, isFav: Boolean) {
    view.bindImage(isFav, placeholderProgressBar(view.context))
}

fun ImageView.bindImage(isFav: Boolean, progress: CircularProgressDrawable) {
    var drawIcon: Int = R.drawable.ic_fav
    if (!isFav)
        drawIcon = R.drawable.ic_not_fav

    val options = RequestOptions()
            .placeholder(progress)
            .error(R.mipmap.ic_launcher)
    Glide.with(context)
            .setDefaultRequestOptions(options).load(drawIcon)
            .into(this)
}

fun placeholderProgressBar(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}