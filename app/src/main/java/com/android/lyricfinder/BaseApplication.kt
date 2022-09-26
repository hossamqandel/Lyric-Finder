package com.android.lyricfinder

import android.app.Application
import com.android.lyricfinder.utils.SharedPrefs
import com.android.lyricfinder.utils.SharedPrefs.initSharedPreferences
import com.android.lyricfinder.utils.disableNightMode
import com.android.lyricfinder.utils.enableNightMode
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initSharedPreferences(this)

        setupAppThemeBasedOnLastOptionUserSelected()
    }

    private fun setupAppThemeBasedOnLastOptionUserSelected(){
        val isNightMode = SharedPrefs.getNightMode()
        if (isNightMode) { enableNightMode() }
        else { disableNightMode() }
    }
}