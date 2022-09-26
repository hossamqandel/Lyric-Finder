package com.android.lyricfinder.utils.Connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import com.android.lyricfinder.R
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class NetworkConnectivityObserver @Inject constructor(
    private val context: Context
): IConnectivityObserver {


    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observe(): Flow<IConnectivityObserver.Status> {

        return callbackFlow {
            val callBack = object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch {
                        send(IConnectivityObserver.Status.Available(context.getString(R.string.network_available)))
                    }
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch {
                        send(IConnectivityObserver.Status.Available(context.getString(R.string.network_losing)))
                    }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch {
                        send(IConnectivityObserver.Status.Available(context.getString(R.string.network_unavailable)))
                    }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch {
                        send(IConnectivityObserver.Status.Available(context.getString(R.string.network_lost)))
                    }
                }
            }

                connectivityManager.registerDefaultNetworkCallback(callBack)
            awaitClose { connectivityManager.unregisterNetworkCallback(callBack) }
        }.distinctUntilChanged()


    }


}
