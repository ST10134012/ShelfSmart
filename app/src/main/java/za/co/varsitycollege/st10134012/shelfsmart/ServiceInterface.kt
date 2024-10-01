package za.co.varsitycollege.st10134012.shelfsmart

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ServiceInterface {

    @Headers("Content-Type:application/json")
    @GET("/c/b70d-8df6-47f9-97ea")
    fun getAllBooks(): Call<ApiResponse>
}