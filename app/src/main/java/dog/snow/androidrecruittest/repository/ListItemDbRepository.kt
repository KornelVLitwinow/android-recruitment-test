package dog.snow.androidrecruittest.repository

import dog.snow.androidrecruittest.mapper.ListItemMapper
import dog.snow.androidrecruittest.model.ListItem
import dog.snow.androidrecruittest.database.ListItemDao
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ListItemDbRepository @Inject constructor(
    private val listItemDao: ListItemDao,
    private val listItemMapper: ListItemMapper
) {
    suspend fun insertListItemEntity(listItem: List<ListItem>) {
        listItemDao.insert(listItemMapper.transformToData(listItem))
    }

    fun getAllListItemDatabase() = listItemDao.getAll().map { listItemMapper.transformToDomain(it) }

    fun searchListItemByPhrase(text: String) =
        listItemDao.searchListItemByPhrase(text).map { listItemMapper.transformToDomain(it) }
}