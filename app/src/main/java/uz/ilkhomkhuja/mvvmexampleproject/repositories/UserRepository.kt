package uz.ilkhomkhuja.mvvmexampleproject.repositories

import uz.ilkhomkhuja.mvvmexampleproject.database.db.AppDatabase
import uz.ilkhomkhuja.mvvmexampleproject.database.entites.User
import uz.ilkhomkhuja.mvvmexampleproject.network.ApiService

class UserRepository(
    private val apiService: ApiService,
    private val appDatabase: AppDatabase
) {
    suspend fun getRemoteUsers() = apiService.getUsers()

    suspend fun getLocalUsers() = appDatabase.userDao().getAllUsers()

    suspend fun addUsers(list: List<User>) = appDatabase.userDao().addUsers(list)
}