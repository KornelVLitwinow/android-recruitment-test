package dog.snow.androidrecruittest.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dog.snow.androidrecruittest.repository.ListItemApiRepository
import dog.snow.androidrecruittest.repository.ListItemDbRepository
import dog.snow.androidrecruittest.viewmodel.SplashViewModel.ViewStatus.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val listItemRepository: ListItemApiRepository,
    private val listItemDbRepository: ListItemDbRepository
) :
    ViewModel() {
    init {
        loadData()
    }

    private val viewStateMutable = MutableLiveData<ViewStatus>(Loading)
    val observeViewState: LiveData<ViewStatus> = viewStateMutable

    private fun loadData() {
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
                viewStateMutable.postValue(Error)
            }
        }

    }

    sealed class ViewStatus {
        object Error : ViewStatus()
        object Success : ViewStatus()
        object Loading : ViewStatus()
    }

}