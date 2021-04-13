package dog.snow.androidrecruittest.api

import dog.snow.androidrecruittest.model.RawPhoto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoService {
    @GET("photos")
   suspend fun getPhotos(
        @Query("_limit") limit: Int
    ): Response<ArrayList<RawPhoto>>
}