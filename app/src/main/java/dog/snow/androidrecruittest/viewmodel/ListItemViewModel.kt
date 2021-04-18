package dog.snow.androidrecruittest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dog.snow.androidrecruittest.repository.ListItemDbRepository
import javax.inject.Inject

@HiltViewModel
class ListItemViewModel @Inject constructor(
    private val listItemDbRepository: ListItemDbRepository
) :
    ViewModel() {

    fun getListItemsDB()= listItemDbRepository.getAllListItemDatabase().asLiveData()

    fun searchListItemByPhrase(query: String) = listItemDbRepository.searchListItemByPhrase(query).asLiveData()
}