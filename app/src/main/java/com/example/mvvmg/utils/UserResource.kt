package com.example.mvvmg.utils

import com.example.mvvmg.database.entity.UserEntity

sealed class UserResource {
    object Loading : UserResource()
    data class Success(val list: List<UserEntity>?) : UserResource()
    data class Error(val message: String) : UserResource()
}