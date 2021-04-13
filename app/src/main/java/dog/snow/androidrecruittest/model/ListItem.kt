package dog.snow.androidrecruittest.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListItem(
    val id: Long,
    val userId: Long,
    val title: String,
    val albumTitle: String,
    val username: String,
    val email: String,
    val phone: String,
    val url: String,
    val thumbnailUrl: String
) : Parcelable