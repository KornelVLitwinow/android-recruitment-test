package dog.snow.androidrecruittest.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RawPhoto(
    val id: Long,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
) : Parcelable