package com.android.lyricfinder.feature_base.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.lyricfinder.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}