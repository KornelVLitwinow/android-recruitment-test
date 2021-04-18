package dog.snow.androidrecruittest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dog.snow.androidrecruittest.repository.ListItemApiRepository
import dog.snow.androidrecruittest.repository.ListItemDbRepository
import dog.snow.androidrecruittest.repository.NetworkRepository
import dog.snow.androidrecruittest.viewmodel.SplashViewModel.ViewStatus.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val listItemRepository: ListItemApiRepository,
    private val listItemDbRepository: ListItemDbRepository,
    private val networkRepository: NetworkRepository
) :
    ViewModel() {

    private val viewStateMutable = MutableLiveData<ViewStatus>()
    val observeViewState: LiveData<ViewStatus> = viewStateMutable

    init {
        if (isNetworkConnection()) {
            loadData()
        } else {
            viewStateMutable.postValue(NetworkErrorConnection)
        }
    }

    private fun isNetworkConnection() = networkRepository.isInternetAvailable()

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val listFromApi = listItemRepository.getDataFromApi()
                if (!listFromApi.isNullOrEmpty()) {
                    listItemDbRepository.insertListItemEntity(listFromApi)
                    viewStateMutable.postValue(Success)
                } else {
                    viewStateMutable.postValue(Error)
                }
            } catch (e: Exception) {
                if (e is SocketTimeoutException) {
                    loadData()
                } else {
                    viewStateMutable.postValue(Error)
                }
            }
        }

    }

    sealed class ViewStatus {
        object Error : ViewStatus()
        object Success : ViewStatus()
        object NetworkErrorConnection : ViewStatus()
    }

}