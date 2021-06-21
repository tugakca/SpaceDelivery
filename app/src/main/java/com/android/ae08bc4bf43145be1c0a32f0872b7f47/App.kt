package com.android.ae08bc4bf43145be1c0a32f0872b7f47

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.android.ae08bc4bf43145be1c0a32f0872b7f47.model.Ship
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : MultiDexApplication() {

    companion object {
        var shipInfo: Ship? = null
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }
}