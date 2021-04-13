package dog.snow.androidrecruittest.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RawAlbum(
    val id: Int,
    val userId: Long,
    val title: String
) : Parcelable
