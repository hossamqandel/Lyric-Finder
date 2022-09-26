package com.android.lyricfinder.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPrefs {

    private var mAppContext: Context? = null
    private const val SHARED_PREFERENCES_NAME = "app theme"
    private const val NIGHT_MODE = "night mode"

    private fun mySharedPreference() {}



    fun initSharedPreferences(appContext: Context?) {
        mAppContext = appContext
    }

    private fun getSharedPreferences(): SharedPreferences {
        return mAppContext!!.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    var SharedPreferences.nightMode
        get() = getBoolean(NIGHT_MODE, false)
        set(value: Boolean) { edit().putBoolean(NIGHT_MODE, value).apply() }

    fun setNightMode (value: Boolean){

        val editor = getSharedPreferences().edit()
        editor.putBoolean(NIGHT_MODE, value).apply()

    }

    fun getNightMode (): Boolean {
        return getSharedPreferences().getBoolean(NIGHT_MODE, false)!!
    }

}