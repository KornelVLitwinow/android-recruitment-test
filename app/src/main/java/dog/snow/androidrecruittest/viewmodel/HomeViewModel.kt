package dog.snow.androidrecruittest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() :
    ViewModel() {

    private val isStillNetworkUnAvailableMutable = MutableLiveData<Boolean>()
    val observeIsStillNetworkUnAvailable: LiveData<Boolean> = isStillNetworkUnAvailableMutable

    private var timeForTenMinutes = 0L

    fun checkStatusNetwork(isConnected: Boolean) {
        viewModelScope.launch {
            checkStatusNetworkAsync(isConnected)
        }
    }

    private suspend fun checkStatusNetworkAsync(isConnected: Boolean) =
        withContext(Dispatchers.Default) {
            timeForTenMinutes = System.currentTimeMillis() + (60.times(1000))
            while (!isConnected) {
                if (System.currentTimeMillis() >= timeForTenMinutes) {
                    isStillNetworkUnAvailableMutable.postValue(true)
                }
                delay(5000)
            }
        }
}