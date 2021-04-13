package dog.snow.androidrecruittest.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ListItemEntity(
    @NonNull
    @PrimaryKey
    val id: Long,
    val userId: Long,
    val title: String,
    val albumTitle: String,
    val username: String,
    val email: String,
    val phone: String,
    val url: String,
    val thumbnailUrl: String
)