package com.example.mvvmg.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmg.repository.UserRepository
import com.example.mvvmg.utils.NetworkHelper
import java.lang.Exception

class ViewModelFactory(private val userRepository: UserRepository, private val networkHelper: NetworkHelper): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(userRepository, networkHelper) as T
        }
        throw Exception("Error")
    }
}