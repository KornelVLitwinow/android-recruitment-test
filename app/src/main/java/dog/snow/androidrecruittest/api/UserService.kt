package dog.snow.androidrecruittest.api

import dog.snow.androidrecruittest.model.RawUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("users/{userId}")
    suspend fun getUser(
        @Path("userId") userId: Long
    ): Response<RawUser>
}