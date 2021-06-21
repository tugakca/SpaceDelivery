package com.android.ae08bc4bf43145be1c0a32f0872b7f47.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SharedPrefUtil {


    companion object {
        private const val STORE_NAME = "SPACE_SHARED_PREFERENCES"
        private const val KEY_FIRST_RUN = "KEY_FIRST_RUN"
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


    fun isFirstRun(context: Context): Boolean {
        val key = getString(context, KEY_FIRST_RUN)
        return if (key.isNullOrEmpty()) {
            setString(context, KEY_FIRST_RUN, "false")
            true
        } else
            false
    }

    private fun setString(context: Context?, key: String, v: String?) {
        var value = v
        if (context == null)
            return
        if (value == null)
            value = ""
        val settings = context.getSharedPreferences(
                STORE_NAME,
                Context.MODE_PRIVATE
        )
        settings.edit().putString(key, value).apply()
    }

    private fun getString(context: Context?, key: String): String? {
        if (context == null)
            return ""
        val settings = context.getSharedPreferences(
                STORE_NAME,
                Context.MODE_PRIVATE
        )
        return settings.getString(key, "")
    }

    fun getID() =
            sharedPref?.getInt(PREF_ID, 0)

    fun getTime() =
            sharedPref?.getLong(PREF_TIME, 0)


}