package com.android.lyricfinder.feature_base.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.lyricfinder.feature_splash.presentation.SplashFragment
import com.android.lyricfinder.utils.Connectivity.IConnectivityObserver
import com.android.lyricfinder.utils.Connectivity.NetworkConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val networkConnectivityObserver: NetworkConnectivityObserver
) : ViewModel() {

    private val _connectivity = Channel<String>()
    val connectivity = _connectivity.receiveAsFlow()

    init {
        onNetwork()
    }

    private fun onNetwork(){
        networkConnectivityObserver.observe().onEach {
            if (!SplashFragment.isFirstVisit){
                when(it){
                    is IConnectivityObserver.Status.Available -> _connectivity.send(it.connectionStatus)
                    is IConnectivityObserver.Status.UnAvailable -> _connectivity.send(it.connectionStatus)
                    is IConnectivityObserver.Status.Losing -> _connectivity.send(it.connectionStatus)
                    is IConnectivityObserver.Status.Lost -> _connectivity.send(it.connectionStatus)
                }
            }
        }.launchIn(viewModelScope)
    }
}