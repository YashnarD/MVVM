package com.example.mvvmg.repository

import com.example.mvvmg.database.dao.UserDao
import com.example.mvvmg.database.entity.UserEntity
import com.example.mvvmg.retrofit.ApiService
import kotlinx.coroutines.flow.flow

class UserRepository(private val apiService: ApiService, private val userDao: UserDao) {

    suspend fun getGithubUsers() = flow { emit(apiService.getGithubUsers()) }

    suspend fun getDbUsers() = flow { emit(userDao.getUsers()) }

    suspend fun addUserToDb(list: List<UserEntity>) = userDao.insert(list)
}