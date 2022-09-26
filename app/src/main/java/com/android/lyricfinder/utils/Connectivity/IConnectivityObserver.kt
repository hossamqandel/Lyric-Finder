package com.android.lyricfinder.utils.Connectivity

import kotlinx.coroutines.flow.Flow

interface IConnectivityObserver {

    fun observe(): Flow<Status>

    sealed interface Status {
        data class Available(val connectionStatus: String): Status
        data class UnAvailable(val connectionStatus: String): Status
        data class Losing(val connectionStatus: String): Status
        data class Lost(val connectionStatus: String): Status
    }
}