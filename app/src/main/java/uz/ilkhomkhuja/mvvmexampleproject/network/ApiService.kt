package uz.ilkhomkhuja.mvvmexampleproject.network

import retrofit2.Response
import retrofit2.http.GET
import uz.ilkhomkhuja.mvvmexampleproject.database.entites.User

interface ApiService {

    @GET("users")
    suspend fun getUsers(): Response<List<User>>
}