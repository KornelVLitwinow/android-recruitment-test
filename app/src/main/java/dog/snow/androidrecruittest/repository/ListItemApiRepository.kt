package dog.snow.androidrecruittest.repository

import dog.snow.androidrecruittest.api.AlbumService
import dog.snow.androidrecruittest.api.PhotoService
import dog.snow.androidrecruittest.api.UserService
import dog.snow.androidrecruittest.basic.Const.Companion.PHOTO_LIMIT
import dog.snow.androidrecruittest.model.ListItem
import dog.snow.androidrecruittest.model.RawAlbum
import dog.snow.androidrecruittest.model.RawPhoto
import dog.snow.androidrecruittest.model.RawUser
import javax.inject.Inject

class ListItemApiRepository @Inject constructor(
    private val photoService: PhotoService,
    private val albumService: AlbumService,
    private val userService: UserService
) {

    suspend fun getDataFromApi() = createListItem(getPhotos(), getAlbums(), getUsers())

    private suspend fun getUniqueAlbumsIds() =
        getPhotos()?.map { it.albumId }?.distinct()?.toList()

    private suspend fun getAlbums(): List<RawAlbum?>? =
        getUniqueAlbumsIds()
            ?.map { albumId -> getAlbum(albumId.toLong()).body() }
            ?.toList()

    private suspend fun getUsers(): List<RawUser?>? =
        getUniqueUsersIds()
            ?.map { userId -> getUser(userId ?: 0).body() }
            ?.toList()

    private suspend fun getUniqueUsersIds(): List<Long?>? =
        getAlbums()?.map { it?.userId }?.distinct()?.toList()

    private fun createListItem(
        photos: ArrayList<RawPhoto>?,
        albums: List<RawAlbum?>?,
        users: List<RawUser?>?
    ): List<ListItem> {
        val listItems = ArrayList<ListItem>()
        if (photos != null) {
            for (photo in photos) {
                val album = albums?.find { it?.id == photo.albumId }!!
                val user = users?.find { it?.id == album.userId }!!
                val listItem = ListItem(
                    id = photo.id,
                    userId = album.userId,
                    title = photo.title,
                    albumTitle = album.title,
                    username = user.username,
                    email = user.email,
                    phone = user.phone,
                    url = photo.url,
                    thumbnailUrl = photo.thumbnailUrl
                )
                listItems.add(listItem)
            }
        }
        return listItems
    }

    private suspend fun getPhotos() = photoService.getPhotos(PHOTO_LIMIT).body()

    private suspend fun getAlbum(albumId: Long) = albumService.getAlbum(albumId)

    private suspend fun getUser(userId: Long) = userService.getUser(userId)

}