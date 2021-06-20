package com.android.ae08bc4bf43145be1c0a32f0872b7f47.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SharedPrefUtil {

    companion object {
        private var sharedPref: SharedPreferences? = null
        private var PREF_TIME = "preferences_time"
        private var PREF_ID = "preferences_id"

        @Volatile
        private var instance: SharedPrefUtil? = null
        private var lock = Any()
        operator fun invoke(context: Context): SharedPrefUtil = instance ?: synchronized(lock) {
            instance ?: makeCustomSharedPref(context).also {
                instance = it
            }
        }


        private fun makeCustomSharedPref(context: Context): SharedPrefUtil {
            sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPrefUtil()
        }
    }


    fun saveTime(time: Long) {
        sharedPref?.edit(commit = true) {
            putLong(PREF_TIME, time)
        }

    }

    fun saveItemId(id: Int) {
        sharedPref?.edit(commit = true) {
            putInt(PREF_ID, id)
        }

    }

    fun getID() =
        sharedPref?.getInt(PREF_ID, 0)

    fun getTime() =
        sharedPref?.getLong(PREF_TIME, 0)


}