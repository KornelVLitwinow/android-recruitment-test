package dog.snow.androidrecruittest.ui.activity

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import dog.snow.androidrecruittest.databinding.HomeActivityBinding
import dog.snow.androidrecruittest.extension.gone
import dog.snow.androidrecruittest.extension.show
import dog.snow.androidrecruittest.repository.ConnectivityRepository
import dog.snow.androidrecruittest.viewmodel.HomeViewModel
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), ConnectivityRepository.ConnectionReceiverListener {

    @Inject
    lateinit var connectivityRepository: ConnectivityRepository

    private var _binding: HomeActivityBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeViewModel()
        registerConnectivityReceiver()
    }

    private fun observeViewModel() {
        viewModel.observeIsStillNetworkUnAvailable.observe(this) {
            if (it) {
                backToSplash()
            }
        }
    }

    private fun backToSplash() {
        finish()
        val intent = SplashActivity.openActivity(this)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    private fun showOrHideBanner(isNetworkConnection: Boolean?) {
        if (isNetworkConnection == true) {
            binding.appbar.bannerOfflineMode.banner.gone()
        } else binding.appbar.bannerOfflineMode.banner.show()
    }

    override fun onResume() {
        super.onResume()
        connectivityRepository.connectivityReceiverListener = this
    }

    override fun onPause() {
        super.onPause()
       connectivityRepository.connectivityReceiverListener = null

    }

    private fun registerConnectivityReceiver() {
        registerReceiver(
            connectivityRepository,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showOrHideBanner(isConnected)
        viewModel.checkStatusNetwork(isConnected)
    }

    companion object {
        fun openActivity(context: Context) = Intent(context, HomeActivity::class.java)
    }
}