package dog.snow.androidrecruittest.repository

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import javax.inject.Inject

class ConnectivityRepository @Inject constructor(private val networkRepository: NetworkRepository) : BroadcastReceiver() {
    var connectivityReceiverListener: ConnectionReceiverListener? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        connectivityReceiverListener?.onNetworkConnectionChanged(networkRepository.isInternetAvailable())
    }

    interface ConnectionReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }
}