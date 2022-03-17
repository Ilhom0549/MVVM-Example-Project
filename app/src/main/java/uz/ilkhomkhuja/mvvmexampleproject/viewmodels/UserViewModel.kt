package uz.ilkhomkhuja.mvvmexampleproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.ilkhomkhuja.mvvmexampleproject.database.entites.User
import uz.ilkhomkhuja.mvvmexampleproject.repositories.UserRepository
import uz.ilkhomkhuja.mvvmexampleproject.utils.NetworkHelper
import uz.ilkhomkhuja.mvvmexampleproject.utils.Resource

class UserViewModel(
    private val userRepository: UserRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val liveData = MutableLiveData<Resource<List<User>>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            liveData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                val remoteUsers = userRepository.getRemoteUsers()
                if (remoteUsers.isSuccessful) {
                    userRepository.addUsers(remoteUsers.body() ?: emptyList())
                    liveData.postValue(Resource.success(remoteUsers.body()))
                } else {
                    liveData.postValue(
                        Resource.error(
                            remoteUsers.errorBody()?.string() ?: "Error",
                            null
                        )
                    )
                }
            } else {
                liveData.postValue(Resource.success(userRepository.getLocalUsers()))
            }
        }
    }

    fun getUsers(): LiveData<Resource<List<User>>> = liveData

}