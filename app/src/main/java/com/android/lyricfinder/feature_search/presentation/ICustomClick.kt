package com.android.lyricfinder.feature_search.presentation

interface ICustomClick {

    fun <T> onItemClick(vararg data: T)
}