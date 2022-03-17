package uz.ilkhomkhuja.mvvmexampleproject.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.ilkhomkhuja.mvvmexampleproject.repositories.UserRepository
import uz.ilkhomkhuja.mvvmexampleproject.utils.NetworkHelper

class ViewModelFactory(
    private val userRepository: UserRepository,
    private val networkHelper: NetworkHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(userRepository, networkHelper) as T
        }
        throw IllegalArgumentException("Error")
    }
}