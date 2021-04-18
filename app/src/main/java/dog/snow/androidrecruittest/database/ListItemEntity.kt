package dog.snow.androidrecruittest.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dog.snow.androidrecruittest.basic.Const

@Entity
data class ListItemEntity(
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = Const.ID) val id: Long,
    @ColumnInfo(name = Const.USER_ID) val userId: Long,
    @ColumnInfo(name = Const.TITLE) val title: String,
    @ColumnInfo(name = Const.ALBUM_TITLE) val albumTitle: String,
    @ColumnInfo(name = Const.USERNAME) val username: String,
    @ColumnInfo(name = Const.EMAIL) val email: String,
    @ColumnInfo(name = Const.PHONE) val phone: String,
    @ColumnInfo(name = Const.URL) val url: String,
    @ColumnInfo(name = Const.THUMBNAIL_URL) val thumbnailUrl: String
)