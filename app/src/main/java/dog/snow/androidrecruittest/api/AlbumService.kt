package dog.snow.androidrecruittest.api

import dog.snow.androidrecruittest.model.RawAlbum
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumService {
    @GET("albums/{albumId}")
    suspend fun getAlbum(
        @Path("albumId") albumId: Long
    ): Response<RawAlbum>
}