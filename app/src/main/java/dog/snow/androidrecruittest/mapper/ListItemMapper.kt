package dog.snow.androidrecruittest.mapper

import dog.snow.androidrecruittest.model.ListItem
import dog.snow.androidrecruittest.database.ListItemEntity
import javax.inject.Inject

class ListItemMapper
@Inject constructor() {

    fun transformToData(listItems: List<ListItem>): List<ListItemEntity> = listItems.map {
        transform(it)
    }

    fun transformToDomain(listItemsEntity: List<ListItemEntity>): List<ListItem> =
        listItemsEntity.map {
            transform(it)
        }

    private fun transform(listItem: ListItem): ListItemEntity = listItem.let {
        ListItemEntity(
            id = it.id,
            userId = it.userId,
            title = it.title,
            albumTitle = it.albumTitle,
            username = it.username,
            email = it.email,
            phone = it.phone,
            url = it.url,
            thumbnailUrl = it.thumbnailUrl
        )
    }

    private fun transform(listItemEntity: ListItemEntity): ListItem = listItemEntity.let {
        ListItem(
            id = it.id,
            userId = it.userId,
            title = it.title,
            albumTitle = it.albumTitle,
            username = it.username,
            email = it.email,
            phone = it.phone,
            url = it.url,
            thumbnailUrl = it.thumbnailUrl
        )
    }
}